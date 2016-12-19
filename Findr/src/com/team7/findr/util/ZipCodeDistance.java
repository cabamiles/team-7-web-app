package com.team7.findr.util;

import java.io.*;
import java.net.*;
public class ZipCodeDistance {
	
	
	
	
	public static float get(int zip1, int zip2){
		String apiKey = "TGl1pW2iztySjyL07d567vBsIoo0QtPKaaLA6NSFhqyBYjC5xHcEdO7VcvW3ZLN9";
		String urlSt = 
				String.format("http://www.zipcodeapi.com/rest/%s/distance.csv/%d/%d/mi", apiKey, zip1, zip2);
		
		StringBuilder result = new StringBuilder();
		try {
			URL url = new URL(urlSt);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		      conn.setRequestMethod("GET");
		      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		      String line;
		      while ((line = rd.readLine()) != null) {
		         result.append(line);
		         result.append(", ");
		      }
		      rd.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] entries = result.toString().split(", ");

		return Float.parseFloat(entries[1]);
	}
}
	