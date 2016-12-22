package com.team7.findr.EMR;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
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
import com.team7.findr.user.Constants;
import com.team7.findr.user.User;
import com.team7.findr.util.WeighingAlgorithm;

import redis.clients.jedis.Jedis;


public class TopTen extends Configured implements Tool {
	
	public static class FighterMapper extends Mapper<
	LongWritable, // User ID.
	Text, // User bucket for Preferences. 
	Text, // User name.
	Text // List of Strings of User Names.
	> {
		private AmazonDynamoDBClient dynamoClient = new AmazonDynamoDBClient(new ProfileCredentialsProvider());
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			DynamoDB dynamoDB = new DynamoDB(dynamoClient);
			StringTokenizer st = new StringTokenizer(value.toString());
			String userName = st.nextToken();
			// Feature bucket.
			String featBucketStr = st.nextToken();
			BigDecimal featBucketNumber = new BigDecimal(featBucketStr);
			
			// Preference bucket.
			String prefBucketStr = st.nextToken();
			BigDecimal prefBucketNumber = new BigDecimal(prefBucketStr);
			
			
			Table table = dynamoDB.getTable("Fighters");
			Index featIndex = table.getIndex("Features-index");
			Index prefIndex = table.getIndex("Preferences-index");
		
			QuerySpec featuresSpec = new QuerySpec()
					.withKeyConditionExpression("#f = :F")
					.withNameMap(new NameMap()
						.with("#f", Constants.FEATURES))
						.withValueMap(new ValueMap().withNumber(":F", featBucketNumber));
			
			ItemCollection<QueryOutcome> featureItems = featIndex.query(featuresSpec);
			
			QuerySpec preferenceSpec = new QuerySpec()
					.withKeyConditionExpression("#f = :F")
					.withNameMap(new NameMap()
							.with("#f", Constants.PREFERENCES))
					.withValueMap(new ValueMap().withNumber(":F", prefBucketNumber));

			ItemCollection<QueryOutcome> prefItems = prefIndex.query(preferenceSpec);
			
			Iterator<Item> prefIterator = prefItems.iterator();
			Iterator<Item> featIterator = featureItems.iterator();

			// Query DynamoDB for the Users in the bucketName.
			while (prefIterator.hasNext()) {
				Item currentRow = prefIterator.next();
				context.write(new Text(userName), new Text(currentRow.get(Constants.USER_ID).toString()));
			}
			
			while (featIterator.hasNext()) {
				Item current = featIterator.next();
				context.write(new Text(userName), new Text(current.get(Constants.USER_ID).toString()));
			}
		}
	}
	
	public static class FighterReducer extends Reducer<Text, Text, Text, ArrayWritable> {
		private AmazonDynamoDBClient dynamoClient = new AmazonDynamoDBClient(new ProfileCredentialsProvider());
		// private Jedis redisClient = new Jedis("ec2-198-51-100-1.compute-1.amazonaws.com");
		private Jedis redisClient = new Jedis("54.85.34.18", 6379);
		private ArrayWritable result;
		private final int NUM_TO_RETURN = 10;

		public void reduce(Text matcherId, Iterable<Text> userIds, Context context) throws IOException, InterruptedException {
			String[] arrayResults = new String[NUM_TO_RETURN];
			ArrayList<User> matchedUsers = new ArrayList<>();
			WeighingAlgorithm wa = new WeighingAlgorithm();

			// Query DynamoDB for the matcher information.
			DynamoDB dynamoDB = new DynamoDB(dynamoClient);
			Table userTable = dynamoDB.getTable(Constants.USER_TABLE);
			PrimaryKey matcherPK = new PrimaryKey(Constants.USER_ID, matcherId.toString());
			Item matcherItem = userTable.getItem(matcherPK);
			User matcherUser = new User(matcherItem);
			
			// Find the ten most compatible matches.
			int currLowestMatch = 501; 
			int indexLowestMatch = 0;
			for(Text userId : userIds) {
				redisClient.append(userId.toString(), "hello");
				// Query DynamoDB for the user information.
				PrimaryKey matcheePK = new PrimaryKey(Constants.USER_ID, userId.toString());
				Item matcheeItem = userTable.getItem(matcheePK);
				// Generate a new User from the DynamoDB information.
				User matcheeUser = new User(matcheeItem);
				// Set the User Object's Id.
				matcheeUser.setUserID(userId.toString());
				int matchScore = wa.getCompatibilityScore(matcherUser, matcheeUser);
				// Check match scores.
				if (matchedUsers.size() < 10) {
					matchedUsers.add(matcheeUser);
					if (matchScore < currLowestMatch) {
						currLowestMatch = matchScore;
						indexLowestMatch = matchedUsers.size();
					}
				} else if (matchScore > currLowestMatch) {
					matchedUsers.remove(indexLowestMatch);
					matchedUsers.add(matcheeUser);
					int[] updateInfo = getLowestMatchInfo(matcherUser, matchedUsers);
					// Update the new Lowest Match Score and Index.
					indexLowestMatch = updateInfo[0];
					currLowestMatch = updateInfo[1];
				}
			}
			// Write output results to Redis.
			int i = 0;
			for(User matchedUser : matchedUsers) {
				arrayResults[i] = matchedUser.getUserID();
				redisClient.lpush(matcherId.toString(), matchedUser.getUserID());
				i++;
			}
			
			// Write output to outputFile.
			result = new ArrayWritable(arrayResults);
			context.write(matcherId, result);
		}
		
		private int[] getLowestMatchInfo(User matcher, ArrayList<User> users) {
			int currLowestMatch = 501;
			int lowestIndex = 0;
			WeighingAlgorithm wa = new WeighingAlgorithm();
			int[] matchIndexAndLowestScore = new int[2];
		
			for(User user : users) {
				int matchScore = wa.getCompatibilityScore(matcher, user);
				if (matchScore < currLowestMatch) {
					currLowestMatch = matchScore;
					lowestIndex = users.indexOf(user);
				}
			}
			matchIndexAndLowestScore[0] = lowestIndex;
			matchIndexAndLowestScore[1] = currLowestMatch;
			return matchIndexAndLowestScore;
		}
	}
	
	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(new Configuration(), "Top Ten Matches");
		
		job.setMapperClass(FighterMapper.class);
		job.setCombinerClass(FighterReducer.class);
		job.setReducerClass(FighterReducer.class);
		
		// Set output.
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(ArrayWritable.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		return job.waitForCompletion(true) ? 0 : 1;
	}
	
	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new TopTen(), args);
		System.exit(res);
	}
}
