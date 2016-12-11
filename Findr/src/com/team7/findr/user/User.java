package temp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
	private String firstName;
	private String lastName;
	private int age;
	private int gender;
	private int weight;
	private int height;
	private int fightStyle;
	private int location;
	
	private List<User> previousMatches = new ArrayList<User>();
	
	private List<Integer> preference = new ArrayList<Integer>();

	public User(String firstName, String lastName){
		this.firstName = firstName;
		this.lastName = lastName;
		
		//default preferences
		preference.add(Constants.WEIGHT_INDEX, 1);
		preference.add(Constants.HEIGHT_INDEX, 1);
		preference.add(Constants.GENDER_INDEX, 1);
		preference.add(Constants.STYLE_INDEX, 1);
		preference.add(Constants.LOCATION_INDEX, 1);
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getFightStyle() {
		return fightStyle;
	}

	public void setFightStyle(int fightStyle) {
		this.fightStyle = fightStyle;
	}

	public int getLocation() {
		//if location zip is in x city, return num
		
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public List<User> getPreviousMatches() {
		return previousMatches;
	}

	public void setPreviousMatches(List<User> previousMatches) {
		this.previousMatches = previousMatches;
	}

	public List<Integer> getPreference() {
		return preference;
	}

	public void setPreference(List<Integer> preference) {
		this.preference = preference;
	}
	

}
