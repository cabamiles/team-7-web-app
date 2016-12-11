package com.team7.findr.Controllers;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.team7.findr.user.Constants;
import com.team7.findr.user.User;
@Controller
public class CreateUserController {

	@RequestMapping("/put-user")
	@ResponseBody
	public void putUser(@ModelAttribute("user") User user) {
		AmazonDynamoDBClient dynamoClient = new AmazonDynamoDBClient(new EnvironmentVariableCredentialsProvider());
		HashMap<String, AttributeValue> attributeMap = new HashMap<String, AttributeValue>();
		
		String email = user.get
		
		dynamoClient.putItem(new PutItemRequest(Constants.USER_TABLE, ));
		
	}
}
