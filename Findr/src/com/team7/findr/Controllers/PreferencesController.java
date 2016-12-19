package com.team7.findr.Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.fasterxml.uuid.Generators;
import com.team7.findr.user.Constants;
import com.team7.findr.user.PreferencesRequest;
import com.team7.findr.util.BucketGenerator;

@RestController
public class PreferencesController {

	@Autowired
	private AmazonDynamoDBClient dynamoClient;
	
	@RequestMapping(method=RequestMethod.POST, value="/prefs", consumes=MediaType.APPLICATION_JSON_VALUE)
	public void putPreferences(@RequestBody PreferencesRequest prefRequest, HttpServletRequest request) {
		DynamoDB dynamo = new DynamoDB(dynamoClient);
		Table table = dynamo.getTable(Constants.USER_TABLE);
		
		HashMap<String, String> attributeNames = new HashMap<String, String>();
		attributeNames.put("#P", Constants.PREFERENCES);
		
		HashMap<String, Object> attributeValues = new HashMap<String, Object>();
		
		System.out.println((String)request.getSession().getAttribute("email"));
		String uuid = Generators.nameBasedGenerator().generate((String)request.getSession().getAttribute("email")).toString();
		
		List<Integer> preferencesList = new ArrayList<Integer>();
		
		// TODO probably should change hard coded 4 later
		for (int i=0; i<4; i++)
			preferencesList.add(0);
		
		preferencesList.add(Constants.STYLE_INDEX, prefRequest.getFightingStyle());
		preferencesList.add(Constants.GENDER_INDEX, prefRequest.getGender());
		preferencesList.add(Constants.HEIGHT_INDEX, prefRequest.getHeight());
		preferencesList.add(Constants.WEIGHT_INDEX, prefRequest.getWeight());
		preferencesList.add(Constants.LOCATION_INDEX, prefRequest.getLocation());
		
		int prefInt = BucketGenerator.getUserPreference(preferencesList);
		attributeValues.put(":val", prefInt);
		
		UpdateItemOutcome outcome = table.updateItem(
				new PrimaryKey(Constants.USER_ID, uuid),
				"set #P = :val",
				attributeNames,
				attributeValues
				);
	}
}