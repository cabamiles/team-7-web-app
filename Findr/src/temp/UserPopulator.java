package temp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

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

/**
 * This class is simply for demo purposes. Should not be used in prod
 *
 */
public class UserPopulator {

	public static void main(String[] args){
		
		long millisecondsInThirdSecond = 333;
		
		AmazonDynamoDBClient dynamoClient = new AmazonDynamoDBClient(new ProfileCredentialsProvider());
		
		List<String> allUsers = new ArrayList<String>();
		
		Scanner in = null;
		try {
			in = new Scanner(new FileReader("/home/miles/team-7-web-app/Findr/src/users.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		for (int i = 0; i < 100; i++){
			LocalTime start = LocalTime.now();
			String line = in.nextLine();
			String[] split = line.split(",");
			User user= new User(split[0], split[1], split[2]);
			if(split[3].equalsIgnoreCase("female")){
				user.setGender(Constants.FEMALE);
			} else{
				user.setGender(Constants.MALE);
			}
			
			user.setWeight(Integer.parseInt(split[4]));
			user.setHeight(Integer.parseInt(split[5]));
			user.setFightStyle(Integer.parseInt(split[6]));
			user.setAge(Integer.parseInt(split[7]));
			user.setLocation(Integer.parseInt(split[8]));
			
			//use next user info as preferences
			String prefLine = in.nextLine();
			split = prefLine.split(",");
			
			if(split[3].equalsIgnoreCase("female")){
				user.setPreference(Constants.GENDER_INDEX, Constants.FEMALE);
			} else{
				user.setPreference(Constants.GENDER_INDEX, Constants.MALE);
			}
			user.setPreference(Constants.WEIGHT_INDEX, Integer.parseInt(split[4]));
			user.setPreference(Constants.HEIGHT_INDEX, Integer.parseInt(split[5]));
			user.setPreference(Constants.STYLE_INDEX, Integer.parseInt(split[6]));
			user.setPreference(Constants.LOCATION_INDEX, Integer.parseInt(split[7]));
			
			HashMap<String, AttributeValue> attributeMap = new HashMap<String, AttributeValue>();
			System.out.println(user.getEmail());
			String uuid = Generators.nameBasedGenerator().generate(user.getEmail()).toString();
			allUsers.add(uuid);
			
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
			attributeMap.put(Constants.PREFERENCES , new AttributeValue().withN(BucketGenerator.getUserPreference(user.getPreference())+""));
			
			List<AttributeValue> collection = new ArrayList<AttributeValue>();
			attributeMap.put(Constants.LIKES, new AttributeValue().withL(collection));
			attributeMap.put(Constants.MATCHES, new AttributeValue().withL(collection));
			
			PutItemRequest putItemRequest = new PutItemRequest(Constants.USER_TABLE, attributeMap);
			dynamoClient.putItem(putItemRequest);
			// to prevent throttling limit dynamoDB puts to 2 a second
			LocalTime now = LocalTime.now();
			while (Duration.between(start, now).toMillis() < millisecondsInThirdSecond) {
				now = LocalTime.now();
			}
		}
		
		// make every user every other user's candidate
		JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
		try (Jedis jedis = pool.getResource()) {
			for (String userId : allUsers) {
			
				for (String otherUser : allUsers) {
					if (!otherUser.equals(userId)) {
						jedis.lpush(userId, otherUser);
					}
				}
			}
		}
		pool.destroy();
		System.out.println("done");
	}
	
}
