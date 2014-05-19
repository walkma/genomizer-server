package authentication;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class Authenticate {

	static private HashMap<String, String> activeUsersID = new HashMap<String,String>();

	public Authenticate(){
	//	activeUsersID=new ArrayList<String>();
	}

	static public String createUserID(String username){
		String uid =UUID.randomUUID().toString();
		return UUID.fromString(uid).toString();

	}

	static public void addUser(String username,String userID){
		activeUsersID.put(userID, username);
	}

	static public boolean userExists(String username){
		System.out.println(activeUsersID);
		return activeUsersID.containsValue(username);

	}

	static public String getID(String username) {
		Iterator<String> keys = activeUsersID.keySet().iterator();
		String key = null;
		String temp_username;
		while(keys.hasNext()) {
			key = keys.next();
			temp_username = activeUsersID.get(key);
			if(temp_username.equals(username)) {
				return key;
			}
		}
		return null;
	}

	static public void deleteUser(String id) {
		activeUsersID.remove(id);
	}

	static public boolean idExists(String id) {
		return activeUsersID.containsKey(id);
	}

	static public String getUsername(String userID){
		return activeUsersID.get(userID);
	}
}