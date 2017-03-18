package com.sogeti.smartshelf.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sogeti.smartshelf.model.DataModel;
import com.sogeti.smartshelf.model.Member;
import com.sogeti.smartshelf.model.Product;
import com.sogeti.smartshelf.model.ProductScoringCriteria;
import com.sogeti.smartshelf.model.Scale;
import com.sogeti.smartshelf.model.ScoringResults;
import com.sogeti.smartshelf.model.Shelf;
import com.sogeti.smartshelf.model.UserDoc;
import com.sogeti.smartshelf.service.DataService;

/**
*
* @author jason main
*/
@RestController
@RequestMapping("prediction")
public class PredictionController {
    
	@Autowired
	DataService dataService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<String> testPredictionService() {
		String testMessage = "test Message";
		
		return new ResponseEntity<String>(testMessage, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/models", method = RequestMethod.GET)
	public ResponseEntity<String> getDeployedModels() {
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://ibm-watson-ml.mybluemix.net/pm/v1/model";
		String accesskey = "iqMbzXCtPD/WMQikw8DJWjSVoHWH/UTNlyfhgq9LOgJXkrv6HHH+pi7UhJdoYpSwHxGxQ3pIogjgEOjN0TGDTcL0h32gVzPkwMbmHXNpi+FQYUqQmv73SQJrb1WXWeZv";
		Map<String, String> vars = new HashMap<>();
		vars.put("accessKey", accesskey);
		
		ResponseEntity<List<DataModel>> dataModelsResponse = restTemplate.exchange(
				url, HttpMethod.GET, null, 
				new ParameterizedTypeReference<List<DataModel>>(){}, vars);
		List<DataModel> dataModels = dataModelsResponse.getBody();
		
		return new ResponseEntity<String>("testing getDeployModels()", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/user/{username}/shelf/{shelfId}/scale/{scaleId}",
			method = RequestMethod.GET)
	public ResponseEntity<String> predictProduct(@PathVariable String username,
			@PathVariable String shelfId, @PathVariable String scaleId) throws JsonParseException, JsonMappingException, IOException {
		ProductScoringCriteria criteria = getProductScoringCriteria(username, shelfId, scaleId);
		String body = "";
		ScoringResults scoringResults = predictProductUse(criteria);
		
		if (evaluateOutOfStockProbability(scoringResults)) {
			body = "{showNotification: true}";
		}
		else {
			body = "{showNotification: false}";
		}
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		
		return response;
	}

	private ScoringResults predictProductUse(ProductScoringCriteria criteria) {
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		String url = "https://ibm-watson-ml.mybluemix.net/pm/v1/score/Smart_Shelf_both_v1.4";
		String accesskey = "iqMbzXCtPD/WMQikw8DJWjSVoHWH/UTNlyfhgq9LOgJXkrv6HHH+pi7UhJdoYpSwHxGxQ3pIogjgEOjN0TGDTcL0h32gVzPkwMbmHXNpi+FQYUqQmv73SQJrb1WXWeZv";
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
		        .queryParam("accesskey", accesskey);
		
		HttpEntity<?> entity = new HttpEntity<ProductScoringCriteria>(criteria, headers);
		
		HttpEntity<String> response = restTemplate.exchange(
		        builder.build().encode().toUri(), 
		        HttpMethod.POST, 
		        entity, 
		        String.class);

		String responseBody = response.getBody();
		ObjectMapper mapper = new ObjectMapper();
		ScoringResults scoringResults = new ScoringResults();
		try {
			ProductScoringCriteria[] productScoringArray = mapper.readValue(responseBody, ProductScoringCriteria[].class);
			List<ProductScoringCriteria> productScoringList = new ArrayList<ProductScoringCriteria>(Arrays.asList(productScoringArray));
			ProductScoringCriteria productScoring = productScoringList.get(0);
			List<List<Object>> scoringDataList = productScoring.getData();
			List<Object> scoringData = scoringDataList.get(0);
			String isOutOfStockStr = (String) scoringData.get(8);
			Boolean isOutOfStock = new Boolean(isOutOfStockStr);
			scoringResults.setIsOutOfStock(isOutOfStock);
			Double outOfStockProbability = (Double) scoringData.get(9);
			scoringResults.setOutOfStockProbability(outOfStockProbability);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return scoringResults;
	}
	
	private Boolean evaluateOutOfStockProbability(ScoringResults scoringResults) {
		
		if (scoringResults.getIsOutOfStock() && 
				scoringResults.getOutOfStockProbability() >= 0.8) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private ProductScoringCriteria getProductScoringCriteria(String username,
			String shelfId, String scaleId) {
		UserDoc userDoc = dataService.findUser(username);
		List<Scale> scales = dataService.getScales(shelfId);
		ProductScoringCriteria criteria = new ProductScoringCriteria();
		List<Object> scoringData = new ArrayList<>();
		for (Scale scale : scales) {
			if (scale.getId().equalsIgnoreCase(scaleId)) {
				String scaleProductId = scale.getProductId();
				String productIdForScoring = getProductIdForScoring(scaleProductId);
				Product product = dataService.getProduct(scaleProductId);
				dataService.populatePersentageInScale(scale);
				
				scoringData.add(scale.getPersentage());
				scoringData.add(scale.getWeight());
				scoringData.add(product.getWeight());
				scoringData.add(scale.getUseDays());
				scoringData.add(productIdForScoring);
				List<Member> householdMembers =
						userDoc.getUser().getHousehold().getMembers();
				Double averageAge = calculateAverageAge(householdMembers);
				scoringData.add(averageAge);
				scoringData.add(householdMembers.size());
				scoringData.add(userDoc.getUser().getZipCode());
				
				criteria.getData().add(scoringData);
			}
		}
		
		return criteria;
	}

	private String getProductIdForScoring(String scaleProductId) {
		String productIdForScoring = "";
		switch (scaleProductId) {
		case "10":
			productIdForScoring = "200";
			break;
		case "11":
			productIdForScoring = "100";
		case "12":
			productIdForScoring = "400";
		case "13":
			productIdForScoring = "500";
		case "14":
			productIdForScoring = "300";
		case "15":
			productIdForScoring = "600";
		default:
			productIdForScoring = scaleProductId;
			break;
		}
		
		return productIdForScoring;
	}

	private Double calculateAverageAge(List<Member> members) {
		int totalAge = 0;
		for (Member member : members) {
			totalAge += member.getAge();
		}
		int memberCount = members.size();
		double avgAge = totalAge / memberCount;
		
		return avgAge;
	}
	
}
