package com.team7.findr.Controllers;


import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.uuid.Generators;
import com.team7.findr.user.Constants;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@RestController
public class FightController {
	
	@Autowired
	private AmazonDynamoDBClient dynamoClient;
	
	/*
	 * landing controller for fights
	 */
	@RequestMapping(value="/fight", method = RequestMethod.GET)
	public ModelAndView fight(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("fight");
		DynamoDB dynamo = new DynamoDB(dynamoClient);
		Table table = dynamo.getTable(Constants.USER_TABLE);
		
		JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
		String uuid = Generators.nameBasedGenerator().generate((String)request.getSession().getAttribute("email")).toString();
		
		// get necessary user information from redis (query by user id for the candidates list)
		String candidateId = null;
		try (Jedis jedis = pool.getResource()){
			candidateId = jedis.lpop(uuid);
		}
		pool.destroy();
		
		if (candidateId == null) {
			// redirect to "no new users"
		}
		Item item;
		PrimaryKey primaryKey = new PrimaryKey(Constants.USER_ID, candidateId);
		
		try {
			// query dynamodb for this user's information
			item = table.getItem(primaryKey);
			model.addObject("candidateName", item.getString(Constants.FIRST_NAME));
			model.addObject("candidateAge", item.getString(Constants.AGE));
			model.addObject("candidateHeight", item.getString(Constants.HEIGHT));
			model.addObject("candidateWeight", item.getString(Constants.WEIGHT));
			model.addObject("candidateStyle", item.getString(Constants.STYLE));
		} catch (Exception e) {
			System.err.println("Could not get item for candidate " + candidateId);
			System.err.println(e.getMessage());
		}
		// add candidate attributes to the view that is then displayed
		model.addObject("candidateId", candidateId);
		return model;
	}
	/*
	 * Controller for processing left clicks
	 */
	@RequestMapping(value="/fight-left", method = RequestMethod.GET)
	public @ResponseBody HashMap<String, String> fightLeft(ModelMap model, HttpServletRequest request) {
		DynamoDB dynamo = new DynamoDB(dynamoClient);
		Table table = dynamo.getTable(Constants.USER_TABLE);
		String uuid = Generators.nameBasedGenerator().generate((String)request.getSession().getAttribute("email")).toString();
		
		JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
		
		// get necessary user information from redis (query by user id for the candidates list)
		String candidateId = null;
		try (Jedis jedis = pool.getResource()){
			candidateId = jedis.lpop(uuid);
		}
		pool.destroy();
		
		if (candidateId == null) {
			// redirect to "no new users"
		}
		HashMap<String, String> candidate = new HashMap<String, String>();
		
		Item item;
		PrimaryKey primaryKey = new PrimaryKey(Constants.USER_ID, candidateId);
		
		try {
			// query dynamodb for this candidate's information
			item = table.getItem(primaryKey);
			candidate.put(Constants.FIRST_NAME, item.getString(Constants.FIRST_NAME));
			candidate.put(Constants.AGE, item.getString(Constants.AGE));
			candidate.put(Constants.HEIGHT, item.getString(Constants.HEIGHT));
			candidate.put(Constants.WEIGHT, item.getString(Constants.WEIGHT));
			candidate.put(Constants.STYLE, item.getString(Constants.STYLE));
			candidate.put(Constants.GENDER, item.getString(Constants.GENDER));
		} catch (Exception e) {
			System.err.println("Could not get item for candidate " + candidateId);
			System.err.println(e.getMessage());
		}
		model.addAttribute("candidateId", candidateId);
		
		// serialize 
		return candidate;
	}
	
	/*
	 * Controller for processing right clicks
	 */
	@RequestMapping(value="/fight-right", method = RequestMethod.GET)
	public @ResponseBody HashMap<String, String> fightRight(ModelMap model, HttpServletRequest request) {
		DynamoDB dynamo = new DynamoDB(dynamoClient);
		Table table = dynamo.getTable(Constants.USER_TABLE);
		String uuid = Generators.nameBasedGenerator().generate((String)request.getSession().getAttribute("email")).toString();
		
		JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
		
		// get necessary user information from redis (query by user id for the candidates list)
		String candidateId = null;
		try (Jedis jedis = pool.getResource()){
			candidateId = jedis.lpop(uuid);
		}
		pool.destroy();
		
		if (candidateId == null) {
			// redirect to "no new users"
		}
		HashMap<String, String> candidate = new HashMap<String, String>();
		
		Item item;
		PrimaryKey primaryKey = new PrimaryKey(Constants.USER_ID, candidateId);
		
		try {
			// query dynamodb for this candidates's information
			item = table.getItem(primaryKey);
			candidate.put(Constants.FIRST_NAME, item.getString(Constants.FIRST_NAME));
			candidate.put(Constants.AGE, item.getString(Constants.AGE));
			candidate.put(Constants.HEIGHT, item.getString(Constants.HEIGHT));
			candidate.put(Constants.WEIGHT, item.getString(Constants.WEIGHT));
			candidate.put(Constants.STYLE, item.getString(Constants.STYLE));
			candidate.put(Constants.GENDER, item.getString(Constants.GENDER));
		} catch (Exception e) {
			System.err.println("Could not get item " + candidateId);
			System.err.println(e.getMessage());
		}
		
		// add candidate attributes to the view that is then displayed
		model.addAttribute("candidateId", candidateId);
		return candidate;
	}
	
	// need another controler for this processing
//	// check if the candidate is in the user's Likes list
//	List<Object> likes = item.getList(Constants.LIKES);
//	HashSet<String> likesSet = new HashSet<String>();
//	for (Object like : likes) {
//		likesSet.add((String)like);
//	}
//	if (likesSet.contains(uuid)) {
//		// display match
	//}
}
