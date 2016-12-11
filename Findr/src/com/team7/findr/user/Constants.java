package temp;

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
	public static final int HEIGHT_MIN = 48;
	public static final int HEIGHT_MAX = 76;
//	static final int HEIGHT_AVG = 66;
//	static final int HEIGHT_STDEV = 4;
	//divisions would be partitionSize = (max + min) / 4
	// > min + partitionSize, increase by [partitionSize] each partition by [division] times 
	
	
	public static final int WEIGHT_DIVISIONS = 4;
	public static final int WEIGHT_MIN = 100;
	public static final int WEIGHT_MAX = 300;
	
}
