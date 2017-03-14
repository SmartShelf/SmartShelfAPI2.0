package com.sogeti.smartshelf.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
			@PathVariable String shelfId, @PathVariable String scaleId) {
		ProductScoringCriteria criteria = getProductScoringCriteria(username, shelfId, scaleId);
//		String criteriaHeaders = getCSVHeaders(criteria);
//		String criteriaCSV = transformProductScoringCriteriaToCSV(criteria);
		
		String body = criteria.toString();
		ResponseEntity<String> response = 
				new ResponseEntity<String>(body, HttpStatus.OK);
		
		return response;
	}
	
	private ProductScoringCriteria getProductScoringCriteria(String username,
			String shelfId, String scaleId) {
		UserDoc userDoc = dataService.findUser(username);
		List<Scale> scales = dataService.getScales(shelfId);
		ProductScoringCriteria criteria = new ProductScoringCriteria();
		
		for (Scale scale : scales) {
			if (scale.getId().equalsIgnoreCase(scaleId)) {
				String scaleProductId = scale.getProductId();
				Product product = dataService.getProduct(scaleProductId);

				dataService.populatePersentageInScale(scale);
				criteria.setFinalPercentage(scale.getPersentage());
				criteria.setStartingWeight(product.getWeight());
				criteria.setFinalWeight(scale.getWeight());
				criteria.setUseDays(scale.getUseDays());
				criteria.setProductId(scaleProductId);
				List<Member> householdMembers =
						userDoc.getUser().getHousehold().getMembers();
				Double averageAge = calculateAverageAge(householdMembers);
				criteria.setAvgAge(averageAge);
				criteria.setHouseholdSize(householdMembers.size());
				criteria.setZipCode(userDoc.getUser().getZipCode());
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
