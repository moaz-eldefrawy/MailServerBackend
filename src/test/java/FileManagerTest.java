import Services.*;

import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class FileManagerTest {

	String currentFolder = System.getProperty("user.dir") + File.separator +
			"src" + File.separator +
			"test" + File.separator +
			"java";

	String dummyFolderPath = currentFolder + File.separator + "testing_folder";
	File dummyFolder = new File(dummyFolderPath);
	String dummyFilepath =  dummyFolderPath + File.separator + "testText.txt";
	File dummyFile = new File(dummyFilepath);



	int[] arr = new int[]{1,2,3,4,5};

	JSONObject jsonObj1 = new JSONObject(Map.of("adel","shakal", "naguib", "sweras", "hamada", "7alabo2a"));
	JSONObject jsonObj2 = new JSONObject(Map.of("moaz","fathy", "ahmed", "bahgat", "abdallah", "yasser"));

	
	@Test
	public void copyFile() throws IOException {
		/** deprecated  **/
		/*
		dummyFolder.mkdirs();
		String path = dummyFolderPath
				+ File.separator + "asd.txt";

		FileManager.copy(dummyFilepath, path);
		assertEquals( new File(path).exists() ,true);
		
		String destPath = currentFolder + File.separator + "idk2";
		FileManager.copy(
				dummyFolderPath,
				destPath
				);
		
		assertArrayEquals(arr,
				(int [])FileManager.getFile(destPath + 
						File.separator +
						"asd.txt")
				);
		
		FileManager.deleteDir(dummyFolder);
		FileManager.deleteDir(dummyFile);
		FileManager.deleteDir(new File(destPath));
	
		assertEquals(dummyFolder.exists(), false);
		assertEquals(dummyFile.exists(), false);


		 */

	}
	
	
	

}
