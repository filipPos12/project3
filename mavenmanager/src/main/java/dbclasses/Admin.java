package dbclasses;

public class Admin {
	private String username;
	private String password;
	private boolean alive;
	
	public Admin(String username, String password, boolean alive) {
		super();
		this.username = username;
		this.password = password;
		this.alive = alive;
	}

	public Admin(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAlive() {
		return true;
	}

	public void setAlive(boolean alive) {
		this.alive = true;
	}

	public boolean isNotAlive() {
		// TODO Auto-generated method stub
		return false;
	}
	public void setNotAlive(boolean alive) {
		this.alive = false;
	}

	@Override
	public String toString() {
		return "Admin [username=" + username + "]";
	}
		

}
	