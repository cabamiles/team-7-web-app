package com.team7.findr.user;

public class Constants {

	//Gender
	public static final int FEMALE = 0;
	public static final int MALE = 1;
	
	//fight style
	public static final int JUDO = 0;
	public static final int BOXING = 1;
	public static final int MUAY_THAI = 2;
	public static final int WRESTLING = 3;
	
	//Preference index
	public static final int WEIGHT_INDEX = 0;
	public static final int HEIGHT_INDEX = 1;
	public static final int GENDER_INDEX = 2;
	public static final int STYLE_INDEX = 3;
	public static final int LOCATION_INDEX = 4;
	

	//Dividing buckets
	public static final int HEIGHT_DIVISIONS = 4;
	public static final int HEIGHT_MIN = 58;
	public static final int HEIGHT_MAX = 72;
	
	public static final int WEIGHT_DIVISIONS = 4;
	public static final int WEIGHT_MIN = 100;
	public static final int WEIGHT_MAX = 250;
	
	// DynamoDB constants
	public static final String USER_TABLE = "Fighters";
	public static final String USER_ID = "UserId";
	public static final String FIRST_NAME = "FirstName";
	public static final String LAST_NAME = "LastName";
	public static final String AGE = "Age";
	public static final String GENDER = "Gender";
	public static final String HEIGHT = "Height";
	public static final String WEIGHT = "Weight";
	public static final String STYLE = "FightingStyle";
	public static final String LOCATION = "Location";
	
}
