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
		Double outOfStockProbability = predictProductUse(criteria);
		
		if (evaluateOutOfStockProbability(outOfStockProbability)) {
			body = "{showNotification: true}";
		}
		else {
			body = "{showNotification: false}";
		}
		
//		String criteriaHeaders = getCSVHeaders(criteria);
//		String criteriaCSV = transformProductScoringCriteriaToCSV(criteria);
		
		
//		ResponseEntity<List<DataModel>> dataModelsResponse = restTemplate.exchange(
//				url, HttpMethod.GET, null, 
//				new ParameterizedTypeReference<List<DataModel>>(){}, vars);
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		
		return response;
	}

	private Double predictProductUse(ProductScoringCriteria criteria) {
//      {
//		"tablename" : "input.csv",
//		"header": ["finalPercentage","finalWeight","startingWeight","useDays","productId","avgAge","householdSize","zipCode"],
//		"data": [[20.0,7.0,35,4,600,"40.5",2,43235]]
//		}
//		StringBuilder
//		RestTemplate restTemplate = new RestTemplate();
//		String url = "https://ibm-watson-ml.mybluemix.net/pm/v1/score/Smart_Shelf_both_v1.4";
//		String accesskey = "iqMbzXCtPD/WMQikw8DJWjSVoHWH/UTNlyfhgq9LOgJXkrv6HHH+pi7UhJdoYpSwHxGxQ3pIogjgEOjN0TGDTcL0h32gVzPkwMbmHXNpi+FQYUqQmv73SQJrb1WXWeZv";
//		String copy=       "iqMbzXCtPD/WMQikw8DJWjSVoHWH/UTNlyfhgq9LOgJXkrv6HHH+pi7UhJdoYpSwHxGxQ3pIogjgEOjN0TGDTcL0h32gVzPkwMbmHXNpi+FQYUqQmv73SQJrb1WXWeZv";
//		Map<String, String> vars = new HashMap<>();
//		vars.put("accesskey", accesskey);
//		
//		ResponseEntity<String> response = restTemplate.postForEntity(url, criteria, String.class, vars);
		
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
		Double outOfStockProbability = -1.0;
		try {
			ProductScoringCriteria[] productScoringArray = mapper.readValue(responseBody, ProductScoringCriteria[].class);
			List<ProductScoringCriteria> productScoringList = new ArrayList<ProductScoringCriteria>(Arrays.asList(productScoringArray));
			ProductScoringCriteria productScoring = productScoringList.get(0);
			List<List<Object>> scoringDataList = productScoring.getData();
			List<Object> scoringData = scoringDataList.get(0);
			outOfStockProbability = (Double) scoringData.get(11);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return outOfStockProbability;
	}
	
	private Boolean evaluateOutOfStockProbability(Double outOfStockProbability) {
		if (outOfStockProbability >= 0.8) {
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
				Product product = dataService.getProduct(scaleProductId);
				dataService.populatePersentageInScale(scale);
//				[20.0,7.0,35,4,600,"40.5",2,43235]
				scoringData.add(20.0);
				scoringData.add(7.0);
				scoringData.add(35);
				scoringData.add(4);
				scoringData.add(600);
				List<Member> householdMembers =
						userDoc.getUser().getHousehold().getMembers();
				Double averageAge = calculateAverageAge(householdMembers);
				scoringData.add(40.5);
				scoringData.add(2);
				scoringData.add("43235");
				
				criteria.getData().add(scoringData);
				
//				criteria.setFinalPercentage(scale.getPersentage());
//				criteria.setStartingWeight(product.getWeight());
//				criteria.setFinalWeight(scale.getWeight());
//				criteria.setUseDays(scale.getUseDays());
//				criteria.setProductId(scaleProductId);
//				
//				criteria.setAvgAge(averageAge);
//				criteria.setHouseholdSize(householdMembers.size());
//				criteria.setZipCode(userDoc.getUser().getZipCode());
			}
		}
		
		return criteria;
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
