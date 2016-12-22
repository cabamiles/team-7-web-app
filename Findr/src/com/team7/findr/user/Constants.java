package com.team7.findr.user;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
	public static final String FEATURES = "Features";
	public static final String PREFERENCES = "Preferences";
	public static final String CANDIDATES = "Candidates";
	public static final String LIKES = "Likes";
	public static final String MATCHES = "Matches";
	public static final String MALE_DEFAULT = "http://blogs-images.forbes.com/kurtbadenhausen/files/2015/06/fm-e1433941678273.jpg";
	public static final String FEMALE_DEFAULT = "https://peopledotcom.files.wordpress.com/2016/08/rhonda-rousey-435.jpg";
	public static final List<String> FIGHTING_STYLES = Collections.unmodifiableList(Arrays.asList("Judo", "Boxing", "Muay Thai", "Wrestling"));
	public static final String FEATURES_INDEX = "Features-index";
	public static final String PREFERENCES_INDEX = "Preferences-index";
	
	public static final String REDIS_IP = "54.85.34.18";
}
