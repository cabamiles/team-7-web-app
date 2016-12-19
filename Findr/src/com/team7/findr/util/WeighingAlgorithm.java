package com.team7.findr.util;

import com.team7.findr.user.Constants;
import com.team7.findr.util.ZipCodeDistance;
import com.team7.findr.user.User;
import java.lang.Math;

public class WeighingAlgorithm {

	public final int CUT_OFF_SCORE = 350; //completely arbitrary right now
	
	public boolean isCompatible(User a, User b){
		if(isCompatibleDirectional(a, b) && isCompatibleDirectional(b, a)) {
			return true;
		}
		return false;
	}
	
	private boolean isCompatibleDirectional(User a, User b) {
		int score = 0;
		
		score += getGenderScore(a, b);
		score += getWeightScore(a, b);
		score += getHeightScore(a, b);
		score += getFightStyleScore(a, b);
		score += getLocationScore(a, b);
	
		if(score > CUT_OFF_SCORE) {
			return true;
		}
		return false;
	}
	
	private int getGenderScore(User a, User b) {
		if(a.getGender() == b.getGender()){
			return 100;
		}
		return 0;
	}
	private int getWeightScore(User a, User b) {
		int maxDiff = (Constants.WEIGHT_MAX - Constants.WEIGHT_MIN) / Constants.WEIGHT_DIVISIONS;
		int diff = Math.abs(a.getWeight() - b.getWeight());
		
		float score = (float)(maxDiff - diff) / (float) maxDiff * 100;
		
		return (int)score;
	}
	private int getHeightScore(User a, User b) {
		int maxDiff = (Constants.HEIGHT_MAX - Constants.HEIGHT_MIN) / Constants.HEIGHT_DIVISIONS;
		int diff = Math.abs(a.getHeight() - b.getHeight());
		
		float score = (float)(maxDiff - diff) / (float) maxDiff * 100;
		
		return (int)score;
	}
	
	private int getFightStyleScore(User a, User b) {
		if(a.getFightStyle() == b.getFightStyle()){
			return 100;
		}
		return 0;
	}
	private int getLocationScore(User a, User b) {
		float distance = ZipCodeDistance.get(a.getLocation(), b.getLocation());
		
		int distanceRounded = (int) distance;
		
		return Integer.max(100 - (distanceRounded * 10), 0);
	}
	
	//Do we still want to compare age?
//	private int getAgeScore(User a, User b) {
//		return 0;
//	}
	
}
