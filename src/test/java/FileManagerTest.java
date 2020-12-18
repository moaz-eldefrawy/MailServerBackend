import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class FileManagerTest {

	String currentFolder = System.getProperty("user.dir");
	String dummyFolderPath = currentFolder + File.separator + "folder";
	File dummyFolder = new File(dummyFolderPath);
	String dummyFilepath =  currentFolder + File.separator + "testText.txt";
	File dummyFile = new File(dummyFilepath);
	
	int[] arr = new int[]{1,2,3,4,5};
	
	
	@Test
	public void test() {
		writeAndRead();
		try {copyFile();}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void writeAndRead() {
		FileManager.writeToFile(arr, dummyFilepath);
		assertArrayEquals(arr, (int[])FileManager.getFile(dummyFilepath) );
	}
	
	public void copyFile() throws IOException {
		
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

	}
	
	
	

}
