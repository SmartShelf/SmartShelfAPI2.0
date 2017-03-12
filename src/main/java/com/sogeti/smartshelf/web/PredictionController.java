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
	
	@RequestMapping(value = "/user/{username}/product/{productId}",
			method = RequestMethod.GET)
	public ResponseEntity<String> predictProduct(@PathVariable String username,
			@PathVariable String productId) {
		UserDoc userDoc = dataService.findUser(username);
		
		String usernameFromDoc = userDoc.getUser().getUsername();
		String productIdFromDoc = "";
		Integer productWeightFromDoc = null;
		List<Shelf> shelfs = userDoc.getShelfs();
		for (Shelf shelf : shelfs) {
			boolean productIdFound = false;
			List<Scale> scales = shelf.getScales();
			for (Scale scale : scales) {
				String scaleProductId = scale.getProductId(); 
				if (scaleProductId.equalsIgnoreCase(productId)) {
					productIdFromDoc = scaleProductId;
					productWeightFromDoc = scale.getWeight();
					productIdFound = true;
					break;
				}
			}
			if (productIdFound) {
				break;
			}
		}
		
		String body;
		if (productId.length() > 0) {
			body = "username=" + usernameFromDoc +
					", productId=" + productIdFromDoc +
					", weight=" + productWeightFromDoc;
		}
		else {
			body = "product not found for user";
		}
		
		ResponseEntity<String> response = 
				new ResponseEntity<String>(body, HttpStatus.OK);
		
		return response;
	}
	
}
