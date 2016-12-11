package com.team7.findr.Controllers;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.fasterxml.uuid.Generators;
import com.team7.findr.user.Constants;
import com.team7.findr.user.User;
@Controller
public class CreateUserController {

	@RequestMapping("/put-user")
	@ResponseBody
	public void putUser(@ModelAttribute("user") User user) {
		AmazonDynamoDBClient dynamoClient = new AmazonDynamoDBClient(new EnvironmentVariableCredentialsProvider());
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
		
		dynamoClient.putItem(new PutItemRequest(Constants.USER_TABLE, attributeMap));
		
	}
}
