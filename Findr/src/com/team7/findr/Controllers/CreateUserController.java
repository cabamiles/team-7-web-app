package com.team7.findr.Controllers;

import java.util.HashMap;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;	
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.fasterxml.uuid.Generators;
import com.team7.findr.user.Constants;
import com.team7.findr.user.User;
import com.team7.findr.util.BucketGenerator;


@RestController
public class CreateUserController {
	
	@RequestMapping(method=RequestMethod.POST, value="/sign-up", consumes=MediaType.APPLICATION_JSON_VALUE)
	public String putUser(@RequestBody User user) {
		AmazonDynamoDBClient dynamoClient = new AmazonDynamoDBClient(new ProfileCredentialsProvider());
		HashMap<String, AttributeValue> attributeMap = new HashMap<String, AttributeValue>();
	
		String email = user.getEmail();
		String uuid = Generators.nameBasedGenerator().generate(email).toString();
		
		attributeMap.put(Constants.USER_ID, new AttributeValue().withS(uuid));
		attributeMap.put(Constants.FIRST_NAME, new AttributeValue().withS(user.getFirstName()));
		attributeMap.put(Constants.LAST_NAME, new AttributeValue().withS(user.getLastName()));
		attributeMap.put(Constants.GENDER, new AttributeValue().withN(user.getGender()+""));
		attributeMap.put(Constants.AGE, new AttributeValue().withN(user.getAge()+""));
		attributeMap.put(Constants.HEIGHT, new AttributeValue().withN(user.getHeight()+""));
		attributeMap.put(Constants.WEIGHT, new AttributeValue().withN(user.getWeight()+""));
		attributeMap.put(Constants.LOCATION, new AttributeValue().withN(user.getLocation()+""));
		attributeMap.put(Constants.STYLE, new AttributeValue().withN(user.getFightStyle()+""));
		
		// Must populate features and preferences first
		// attributeMap.put(Constants.FEATURES, new AttributeValue().withN(BucketGenerator.getUserFeature(user)+""));
		// attributeMap.put(Constants.PREFERENCES, new AttributeValue().withN(BucketGenerator.getUserPreference(user)+""));
		PutItemRequest putItemRequest = new PutItemRequest(Constants.USER_TABLE, attributeMap);
		dynamoClient.putItem(putItemRequest);
		return "User " + user.getEmail() + " successfully created";
	}
}
