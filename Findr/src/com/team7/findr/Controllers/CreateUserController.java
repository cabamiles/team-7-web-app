package com.team7.findr.Controllers;

import java.util.HashMap;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.fasterxml.uuid.Generators;
import com.team7.findr.user.Constants;
import com.team7.findr.user.User;
import com.team7.findr.util.BucketGenerator;


@RestController
public class CreateUserController {

	@RequestMapping(method=RequestMethod.GET,value="/sign-up")
	public ModelAndView signUpForm() {
		return new ModelAndView("sign-up");
	}
	
	
	@RequestMapping(method=RequestMethod.POST, value="/sign-up", consumes=MediaType.APPLICATION_JSON_VALUE)
	public String putUser(@RequestBody User user) {
		AmazonDynamoDBClient dynamoClient = new AmazonDynamoDBClient(new EnvironmentVariableCredentialsProvider());
		HashMap<String, AttributeValue> attributeMap = new HashMap<String, AttributeValue>();
	
		String email = user.getEmail();
		System.out.println(email);
		String uuid = Generators.nameBasedGenerator().generate(email).toString();
		System.out.println(uuid);
		
		attributeMap.put(Constants.USER_ID, new AttributeValue().withS(uuid));
		attributeMap.put(Constants.FIRST_NAME, new AttributeValue().withS(user.getFirstName()));
		attributeMap.put(Constants.LAST_NAME, new AttributeValue().withS(user.getLastName()));
		attributeMap.put(Constants.GENDER, new AttributeValue().withN(user.getGender()+""));
		attributeMap.put(Constants.AGE, new AttributeValue().withN(user.getAge()+""));
		attributeMap.put(Constants.HEIGHT, new AttributeValue().withN(user.getHeight()+""));
		attributeMap.put(Constants.WEIGHT, new AttributeValue().withN(user.getWeight()+""));
		attributeMap.put(Constants.LOCATION, new AttributeValue().withN(user.getLocation()+""));
		attributeMap.put(Constants.STYLE, new AttributeValue().withN(user.getFightStyle()+""));
		attributeMap.put(Constants.FEATURES, new AttributeValue().withN(BucketGenerator.getUserFeature(user)+""));
		attributeMap.put(Constants.PREFERENCES, new AttributeValue().withN(BucketGenerator.getUserPreference(user)+""));
		
		
		dynamoClient.putItem(new PutItemRequest(Constants.USER_TABLE, attributeMap));
		return "User " + user.getEmail() + " successfully created";
	}
}
