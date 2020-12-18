import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;

public class FileManager {
	public static void writeToFile(Object obj, String filePath) {
		try {
			FileOutputStream fout = new FileOutputStream(filePath);
			ObjectOutputStream Oout = new ObjectOutputStream(fout);
			Oout.writeObject(obj);
			Oout.flush();
			Oout.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Object getFile(String filePath) {
		Object obj = null;
		System.out.println(filePath);
		try {
			FileInputStream fin = new FileInputStream(filePath);
			ObjectInputStream Oin = new ObjectInputStream(fin);
			obj = Oin.readObject();
			Oin.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return obj;
	}
	
	public static void deleteDir(File dir) {
	    File[] files = dir.listFiles();
	    if(files != null) {
	        for (File file : files) {
	            deleteDir(file);
	        }
	    }
	    dir.delete();
	}
	
	
	// destpath inclusive of a new name
	// the parent folder must exist
	public static void copyFile(String originalPath, String destPath) {
		File source = new File(originalPath);
        File dest = new File(destPath);
        
        try {
			Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void copy(String sourceFolderPath, String destinationFolderPath) {
		File sourceFolder = new File(sourceFolderPath);
		File destinationFolder = new File(destinationFolderPath);
		
		if(sourceFolder.isDirectory()) {
			
			destinationFolder.mkdirs();
			String[] foldersName = sourceFolder.list();
			for(String folderName: foldersName) {
				File srcFolder = new File(sourceFolder, folderName);
				File destFolder = new File(destinationFolder, folderName);
				copy(srcFolder.getAbsolutePath(), destFolder.getAbsolutePath());
			}
		}
		else {
			copyFile(sourceFolderPath,destinationFolderPath);
		}
	}

	public static void writeToJSONFile(JSONObject obj, String filePath) {
		try {
			FileWriter file = new FileWriter(filePath);
			file.write(obj.toJSONString());
			file.flush();
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public  static JSONObject getJSONObj(String filePath){
		//JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();

		try
		{
			FileReader reader = new FileReader(filePath);

			//Read JSON file
			Object obj = jsonParser.parse(reader);
			JSONObject jsonObject = (JSONObject) obj;

			reader.close();
			return jsonObject;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
