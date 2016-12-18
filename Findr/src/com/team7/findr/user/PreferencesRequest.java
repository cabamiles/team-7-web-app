package com.team7.findr.user;

public class PreferencesRequest {

	private String email;
	private int gender;
	private int height;
	private int weight;
	private int location;
	private int fightingStyle;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public int getFightingStyle() {
		return fightingStyle;
	}
	public void setFightingStyle(int fightingStyle) {
		this.fightingStyle = fightingStyle;
	}
	
	
}
