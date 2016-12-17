package com.team7.findr.util;

import com.team7.findr.user.User;
import com.team7.findr.user.Constants;

import java.util.List;


public class BucketGenerator {
	
	public static int getUserPreference(User a){
		List<Integer> preferences = a.getPreference();
		
		int location = preferences.get(Constants.LOCATION_INDEX);
		int heightPartition = getHeightPartition(preferences.get(Constants.HEIGHT_INDEX));
		int weightPartition = getWeightPartition(preferences.get(Constants.WEIGHT_INDEX));
		int gender = preferences.get(Constants.GENDER_INDEX);
		int style = preferences.get(Constants.STYLE_INDEX);
		
		int bucket = 0;
		
		bucket += (location << 10);
		bucket += (heightPartition << 7);
		bucket += (weightPartition << 4);
		bucket += (gender << 3);
		bucket += style;
		
		return bucket;
	}
	
	public static int getUserFeature(User a){
		int bucket = 0;
		
		int location = a.getLocation();
		int heightPartition = getHeightPartition(a.getHeight());
		int weightPartition = getWeightPartition(a.getWeight());
		int gender = a.getGender();
		int style = a.getFightStyle();

		bucket += (location << 10);
		bucket += (heightPartition << 7);
		bucket += (weightPartition << 4);
		bucket += (gender << 3);
		bucket += style;
		
		return bucket;
	}
	
	private static int getHeightPartition(int height){
		int partitionSize = (Constants.HEIGHT_MAX - Constants.HEIGHT_MIN) / Constants.HEIGHT_DIVISIONS;
		
		int rangeMax = Constants.HEIGHT_MIN + partitionSize;
		
		for(int i = 0; i < Constants.HEIGHT_DIVISIONS; i++){
			if(height < rangeMax) {
				return i;
			} else {
				rangeMax += partitionSize;
			}
		}
		return Constants.HEIGHT_DIVISIONS - 1;
	}
	
	private static int getWeightPartition(int weight){
		int partitionSize = (Constants.WEIGHT_MAX - Constants.WEIGHT_MIN) / Constants.WEIGHT_DIVISIONS;
		
		int rangeMax = Constants.WEIGHT_MIN + partitionSize;
		
		for(int i = 0; i < Constants.WEIGHT_DIVISIONS; i++){
			if(weight < rangeMax) {
				return i;
			} else {
				rangeMax += partitionSize;
			}
		}
		return Constants.WEIGHT_DIVISIONS - 1;
	}
	
}
