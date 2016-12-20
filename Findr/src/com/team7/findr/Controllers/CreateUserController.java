package com.team7.findr.Controllers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


@RestController
public class CreateUserController {
	
	@Autowired
	private AmazonDynamoDBClient dynamoClient;
	
	@RequestMapping(method=RequestMethod.POST, value="/sign-up", consumes=MediaType.APPLICATION_JSON_VALUE)
	public void putUser(@RequestBody User user, HttpServletRequest request) {
		//AmazonDynamoDBClient dynamoClient = new AmazonDynamoDBClient(new ProfileCredentialsProvider());
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
		attributeMap.put(Constants.FEATURES, new AttributeValue().withN(BucketGenerator.getUserFeature(user)+""));
		// attributeMap.put(Constants.PREFERENCES, new AttributeValue().withN(BucketGenerator.getUserPreference(user)+""));
		PutItemRequest putItemRequest = new PutItemRequest(Constants.USER_TABLE, attributeMap);
		dynamoClient.putItem(putItemRequest);
		
		request.getSession().setAttribute("user", user);
		request.getSession().setAttribute("email", user.getEmail());
	}
	
	@RequestMapping("/preferences")
	public ModelAndView redirectPreferences() {
		System.out.println("redirect called");
		return new ModelAndView("preferences");
	}
}
