package com.team7.findr.Controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.uuid.Generators;
import com.team7.findr.user.Constants;

@Controller
public class MatchesController {
	
	@Autowired
	private AmazonDynamoDBClient dynamoClient;
	
	/*
	 * Controller to display matches for current user
	 */
	@RequestMapping("/matches")
	public ModelAndView getMatches(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("matches");
		DynamoDB dynamo = new DynamoDB(dynamoClient);
		Table table = dynamo.getTable(Constants.USER_TABLE);
		String uuid = Generators.nameBasedGenerator().generate((String)request.getSession().getAttribute("email")).toString();
		
		Item user = table.getItem(new PrimaryKey(Constants.USER_ID, uuid));
		
		List<String> matchIds = user.getList(Constants.MATCHES);
		List<String> matchNames = new ArrayList<String>();
		
		for (String matchId : matchIds) {
			Item match = table.getItem(new PrimaryKey(Constants.USER_ID, matchId));
			String fullName = "";
			fullName += match.getString(Constants.FIRST_NAME);
			fullName += " ";
			fullName += match.getString(Constants.LAST_NAME);
			matchNames.add(fullName);
		}
		mv.addObject("allMatches", matchNames);
		return mv;
	}
}
