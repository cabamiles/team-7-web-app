package temp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import com.team7.findr.user.Constants;
import com.team7.findr.user.User;

public class UserPopulator {

	public static void main(String[] args){
		
		Scanner in = null;
		try {
			in = new Scanner(new FileReader("/Users/helenchang/git/team-7-web-app/users.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		for (int i = 0; i < 100; i++){
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
		}
	}
	
}
