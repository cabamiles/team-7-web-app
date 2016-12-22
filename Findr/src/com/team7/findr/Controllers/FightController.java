package com.team7.findr.Controllers;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
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
		
		JedisPool pool = new JedisPool(new JedisPoolConfig(), Constants.REDIS_IP);
		String uuid = Generators.nameBasedGenerator().generate((String)request.getSession().getAttribute("email")).toString();
		// get necessary user information from redis (query by user id for the candidates list)
		String candidateId = null;
		try (Jedis jedis = pool.getResource()){
			candidateId = jedis.lpop(uuid);
		}
		pool.destroy();
		
		if (candidateId == null) {
			// TODO: should redired when there is nothing in redis
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
			model.addObject("candidateStyle", Constants.FIGHTING_STYLES.get(Integer.parseInt(item.getString(Constants.STYLE))));
			int gender = Integer.parseInt(item.getString(Constants.GENDER));
			if (gender==0) {
				model.addObject("candidatePhoto", Constants.MALE_DEFAULT);
			} else {
				model.addObject("candidatePhoto", Constants.FEMALE_DEFAULT);
			}
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
		
		JedisPool pool = new JedisPool(new JedisPoolConfig(), Constants.REDIS_IP);
		
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
	
	@RequestMapping(value="/check-match", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public void checkMatch(@RequestBody String candidateId, HttpServletRequest request){
		DynamoDB dynamo = new DynamoDB(dynamoClient);
		Table table = dynamo.getTable(Constants.USER_TABLE);
		String uuid = Generators.nameBasedGenerator().generate((String)request.getSession().getAttribute("email")).toString();
		
		System.out.println(candidateId);
		// check if it's a match
		PrimaryKey primaryKey = new PrimaryKey(Constants.USER_ID, candidateId);
		PrimaryKey userKey = new PrimaryKey(Constants.USER_ID, uuid);
		Item item = table.getItem(primaryKey);
		Item userItem = table.getItem(userKey);
		List<String> likesList = item.getList(Constants.LIKES);
		System.out.println(likesList.size());
		for (String s : likesList) {
			System.out.println(s);
		}
		if (item.getList(Constants.LIKES)!= null && likesList.contains(uuid)) {
			// add user to candidates matches and vice versa
			List<String> userMatches = userItem.getList(Constants.MATCHES);
			List<String> candMatches = item.getList(Constants.MATCHES);
			boolean usrHasMatches = userMatches != null;
			boolean candHasMatches = candMatches != null;
			int usrNumMatches;
			int candNumMatches;

			if (usrHasMatches) {
				usrNumMatches = userMatches.size();
			} else {
				usrNumMatches = 0;
			}

			if (candHasMatches) {
				candNumMatches = candMatches.size();
			} else {
				candNumMatches = 0;
			}
			setMatch(table, uuid, candidateId, usrNumMatches, candNumMatches);
		}

		// add candidate to user's likes
		List<String> userLikes = userItem.getList(Constants.LIKES);
		boolean hasLikes = userLikes != null;
		int numLikes;
		if (hasLikes) {
			numLikes = userLikes.size();
		} else {
			numLikes = 0;
		}

		setLikes(table, uuid, candidateId, numLikes);
	}
	/*
	 * Controller for processing right clicks
	 */
	@RequestMapping(value="/fight-right", method = RequestMethod.GET)
	public @ResponseBody HashMap<String, String> fightRight(ModelMap model, HttpServletRequest request) {
		DynamoDB dynamo = new DynamoDB(dynamoClient);
		Table table = dynamo.getTable(Constants.USER_TABLE);
		String uuid = Generators.nameBasedGenerator().generate((String)request.getSession().getAttribute("email")).toString();
		
		JedisPool pool = new JedisPool(new JedisPoolConfig(), Constants.REDIS_IP);

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
		PrimaryKey userKey = new PrimaryKey(Constants.USER_ID, uuid);
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
	
	private void setMatch(Table table, String userId, String candidateId, int usrMatches, int candMatches) {
		HashMap<String, String> attributeNames = new HashMap<String, String>();
		attributeNames.put("#M", Constants.MATCHES);
		HashMap<String, Object> attributeValuesCan = new HashMap<String, Object>();
		HashMap<String, Object> attributeValuesUsr = new HashMap<String, Object>();
		attributeValuesCan.put(":can", candidateId);
		attributeValuesUsr.put(":usr", userId);
		
		table.updateItem(
				new PrimaryKey(Constants.USER_ID, userId),
				"set #M[" + usrMatches + "] = :can",
				attributeNames,
				attributeValuesCan
				);
		
		table.updateItem(new PrimaryKey(Constants.USER_ID, candidateId),
				"set #M[" + candMatches + "] = :usr",
				attributeNames,
				attributeValuesUsr
				);
	}
	
	private void setLikes(Table table, String userId, String candidateId, int usrLikes) {
		HashMap<String, String> attributeNames = new HashMap<String, String>();
		attributeNames.put("#L", Constants.LIKES);
		HashMap<String, Object> attributeValues = new HashMap<String, Object>();
		attributeValues.put(":can", candidateId);

		table.updateItem(
				new PrimaryKey(Constants.USER_ID, userId),
				"set #L[" + usrLikes + "] = :can",
				attributeNames,
				attributeValues
				);
		
	}
}
