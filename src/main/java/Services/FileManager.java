package Services;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.json.JSONObject;

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
			FileWriter file = new FileWriter(filePath + ".json");
			file.write(obj.toString());
			file.flush();
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public  static JSONObject getJSONObj(String filePath){
		//JSON parser object to parse read file
		//JSONParser jsonParser = new JSONParser();

		try
		{
			FileReader reader = new FileReader(filePath + ".json");
			String content = new String(Files.readAllBytes(Paths.get(filePath+".json")), StandardCharsets.UTF_8);
			JSONObject jsonObject = new JSONObject(content);
			reader.close();
			return jsonObject;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
