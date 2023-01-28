package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class HeadDoctor extends User {
	
	
	Connection con=conn.connDb();
	Statement st=null;
	ResultSet rs=null;
	PreparedStatement preparedStatement=null;

	
	public HeadDoctor(int id, String identityNumber, String password, String name, String type) {
		super(id, identityNumber, password, name, type);
		
	}
	
	public HeadDoctor() {
		
	}
	
	public ArrayList<User> getDoctorList() throws SQLException{
		
		ArrayList<User> list=new ArrayList<>();
		
		User obj;
		try {
			st=con.createStatement();
			rs=st.executeQuery("SELECT * FROM user WHERE type='doctor'");
			
			while(rs.next()) {
				
				obj=new User(rs.getInt("id"),rs.getString("identityNumber"),rs.getString("password"),rs.getString("name"),rs.getString("type"));
				list.add(obj);
		      }
		   }catch (SQLException e) {
			e.printStackTrace();
		
		
		   }
		   return list;
		   

}
	
public ArrayList<User> getClinicDoctorList(int clinic_id) throws SQLException{
		
		ArrayList<User> list=new ArrayList<>();
		
		User obj;
		try {
			st=con.createStatement();
			rs=st.executeQuery("SELECT u.id ,u.identityNumber,u.type,u.name, u.password FROM worker w LEFT JOIN user u  on w.user_id=u.id WHERE clinic_id="+clinic_id);
			
			while(rs.next()) {
				
				obj=new User(rs.getInt("u.id"),rs.getString("u.identityNumber"),rs.getString("u.password"),rs.getString("name"),rs.getString("type"));
				list.add(obj);
		      }
		   }catch (SQLException e) {
			e.printStackTrace();
		
		
		   }
		   return list;
		   

	}

	public boolean addDoctor(String identityNumber, String password,String name) throws SQLException {
		
		   String query="INSERT INTO user"+"(identityNumber,password,name,type) VALUES "+"(?,?,?,?)";
		   boolean key=false;
		   try {
			   
			   st=con.createStatement();
			   preparedStatement=con.prepareStatement(query);
			   preparedStatement.setString(1,identityNumber );
			   preparedStatement.setString(2,password );
			   preparedStatement.setString(3,name );
			   preparedStatement.setString(4,"doctor" );
			   preparedStatement.executeUpdate();
			   key=true;
			   
		   }catch(Exception e){
			   
			   e.printStackTrace();
		   }
		   if(key)
			   return true;
		   else
			   return false;

		 
	   }
	
	public boolean deleteDoctor(int id) throws SQLException {
		
		   String query="DELETE FROM user WHERE id=?";
		   boolean key=false;
		   try {
			   
			   st=con.createStatement();
			   preparedStatement=con.prepareStatement(query);
			   preparedStatement.setInt(1,id);
			   preparedStatement.executeUpdate();
			   key=true;
			   
		   }catch(Exception e){
			   
			   e.printStackTrace();
		   }
		   if(key)
			   return true;
		   else
			   return false;

		 
	   }
	
	public boolean updateDoctor(int id,String identityNumber,String password,String name) throws SQLException {
		
		   String query="UPDATE  user SET name=?, identityNumber=?, password=? WHERE id=?";
		   boolean key=false;
		   try {
			   
			   st=con.createStatement();
			   preparedStatement=con.prepareStatement(query);
			   preparedStatement.setString(1,name);
			   preparedStatement.setString(2,identityNumber);
			   preparedStatement.setString(3,password);
			   preparedStatement.setInt(4,id);
			   preparedStatement.executeUpdate();
			   key=true;
			   
		   }catch(Exception e){
			   
			   e.printStackTrace();
		   }
		   if(key)
			   return true;
		   else
			   return false;

		 
	   }
	
	public boolean addWorker(int user_id, int clinic_id) throws SQLException {
		
		   String query="INSERT INTO worker" + "(user_id,clinic_id) VALUES "+"(?,?)";
		   boolean key=false;
		   int count=0;
		   try {
			   
			   st=con.createStatement();
			   rs=st.executeQuery("SELECT * FROM worker WHERE clinic_id=" +clinic_id+" AND user_id=" +user_id);
			   while(rs.next()) {
				   count++;
			   }
			   if(count==0) {
				   
				   preparedStatement=con.prepareStatement(query);
				   preparedStatement.setInt(1,user_id );
				   preparedStatement.setInt(2,clinic_id );
				   preparedStatement.executeUpdate();
			   }
			  
			   key=true;
			   
		   }catch(Exception e){
			   
			   e.printStackTrace();
		   }
		   if(key)
			   return true;
		   else
			   return false;

		 
	   }
	
	

}
