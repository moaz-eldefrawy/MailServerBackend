package Services;

import java.io.*;
import java.util.regex.Pattern;


public class Authentication {

	protected String SystemUsersInfoPath;
	private Contact contact;
	boolean matchPass = false;
	private Contact currentUser;
	private String email;
	private String password;
	private static Authentication instance = new Authentication();

	//for sign up
	private Authentication() {
	}

	public static Authentication getInstance(){
		return instance;
	}

	public boolean signUp(String email, String password) {
		if (userExists(email) || !isValidEmailFormat(email))
			return false;

		/// TODO: HashPassword Using Spring Scrypt
		StorageManager.storeUser(email, password);
		return true;
	}

	/**

		return null on failure and user object on success
	 */
	public User signIn(String email, String password) {
		if (!userExists(email) || !isValidEmailFormat(email))
			return null;

		User user = StorageManager.retrieveUser(email);
		/// TODO: Check if password matches after hashing
		if(user.password.equals(password))
			return user;

		return null;
	}

	public Boolean userExists(String email) {
		String userFilePath = App.usersFolderPath + File.separator +
				email + ".json";

		File userFolder = new File(userFilePath);
		return userFolder.exists();
	}

	public boolean isValidEmailFormat(String email) {
		String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		boolean match = pattern.matcher(email).matches();
		return match;
	}


}

	 
     
