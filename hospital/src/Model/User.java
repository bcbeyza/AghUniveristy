package Model;

import Helper.DBConnection;

public class User {

	private int id;
	String identityNumber,password,name,type;
	
	DBConnection conn=new DBConnection();
	
	public User() {
		
	}
	
	
	public User(int id, String identityNumber, String password, String name, String type) {
		super();
		this.id = id;
		this.identityNumber = identityNumber;
		this.password = password;
		this.name = name;
		this.type = type;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getIdentityNumber() {
		return identityNumber;
	}


	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
}
