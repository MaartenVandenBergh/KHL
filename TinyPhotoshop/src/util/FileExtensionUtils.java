package util;

import java.io.File;

public class FileExtensionUtils {

	public static String getExtensionFromFile(File f){
		String output = f.getName();
		try{
			output = output.substring(output.indexOf(".")+1);
		}
		catch(StringIndexOutOfBoundsException e){
			throw new IllegalArgumentException("No extension");
		}
		
		return output;
	}
	public static String getNameFromFile(File f){
		String output = f.getName();
		output = output.substring(0, output.indexOf(".")-1);
		
		return output;
	}
	
}
