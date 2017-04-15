package fi.jamk.chatapp;


public interface UserDAO {
	//Register method
	public boolean registerUser(User newUser);
	//Log in method
	public boolean loginUser(User user);
}
