package com.team7.findr.Controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.uuid.Generators;
import com.team7.findr.user.Constants;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import org.springframework.ui.ModelMap;

@Controller
@RequestMapping("/matches")
public class MatchesController {
	public ModelAndView fight(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("matches");
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
}
