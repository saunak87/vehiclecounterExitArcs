package com.arcs.parkingcounter.es.esy.playdotv.gpiolib;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;

public class RunPythonCode {

	public static String getOutput() {
		return output;
	}

	public static void setOutput(String output) {
		RunPythonCode.output = output;
	}

	private  static String output = "";

	static boolean fromFile(File file) {

		boolean outputValue = false;

		try {

			ProcessBuilder pb = new ProcessBuilder("python", file.toString());
			pb.redirectOutput(Redirect.PIPE);
			Process p = pb.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
//			String output = builder.toString();
			output = builder.toString();
//			System.out.println("Python output: " + output);
//			if(output.equals("0")){
//				System.out.println("Python Output: " + output);
//			}
			
			if(isNumeric(output)){
				if(Integer.parseInt(output) == 1){
					outputValue = true;
				}else{
					outputValue = false;

				}
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return outputValue;

	}
	
	static boolean isNumeric(String str){
		try{
			@SuppressWarnings("unused")
			double d = Double.parseDouble(str);
		}catch(NumberFormatException nfe){
			return false;
		}
		return true;
	}

}
