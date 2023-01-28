package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Doctor extends User {

	Connection con=conn.connDb();
	Statement st=null;
	ResultSet rs=null;
	PreparedStatement preparedStatement=null;
	
	
	public Doctor() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Doctor(int id, String identityNumber, String password, String name, String type) {
		super(id, identityNumber, password, name, type);
		// TODO Auto-generated constructor stub
	}
	
	public boolean addWhour(int doctor_id,String doctor_name,String wdate) throws SQLException {
		
		int key=0;
		int count=0;
		
		String query="INSERT INTO whour"+ "(doctor_id,doctor_name,wdate) VALUES" +"(?,?,?)";
		
		try {
			st=con.createStatement();
			rs=st.executeQuery("SELECT * FROM whour WHERE status='a' AND doctor_id = "+ doctor_id +" AND wdate='"+wdate+"'");
			
			while(rs.next()) {
				
				count++;
				break;
				
			}
			
			if(count==0) {
				preparedStatement=con.prepareStatement(query);
				preparedStatement.setInt(1, doctor_id);
				preparedStatement.setString(2, doctor_name);
				preparedStatement.setString(3, wdate);
				preparedStatement.executeUpdate();
			}
			
			key=1;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(key==1)
			return true;
		else
		    return false;
	}
	
public ArrayList<Whour> getWhourList(int doctor_id) throws SQLException{
		
		ArrayList<Whour> list=new ArrayList<>();
		
		Whour obj;
		try {
			st=con.createStatement();
			rs=st.executeQuery("SELECT * FROM whour WHERE status='a' AND doctor_id=" + doctor_id);
			
			while(rs.next()) {
				
				obj=new Whour();
				obj.setId(rs.getInt("id"));
				obj.setDoctor_id(rs.getInt("doctor_id"));
				obj.setDoctor_name(rs.getString("doctor_name"));
				obj.setStatus(rs.getString("status"));
				obj.setWdate(rs.getString("wdate"));
				list.add(obj);
		      }
		   }catch (SQLException e) {
			e.printStackTrace();
		
		
		   }
		   return list;
		   

	}

}
