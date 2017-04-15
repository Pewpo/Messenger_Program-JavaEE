package fi.jamk.chatapp;

import java.util.List;

public interface UserDAO {
	//Register method
	public boolean registerUser(User newUser);
	//Log in method
	public boolean loginUser(User user);
	//Get all users
	public List<User> getAllUsers(String loggedAs);
	
}
