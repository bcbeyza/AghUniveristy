package Helper;

import javax.swing.JOptionPane;

public class Helper {

	
	public static void showMsg(String str) {
		
		String msg;
		
		switch(str) {
		
		case "fill":
			msg="Please fill up all informations.";
			break;
			
		case "success":
			msg="Successful !";
			break;
			
	    default:
	    	msg=str;
		}
		
		JOptionPane.showMessageDialog(null, msg,"Message",JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static boolean confirm(String str) {
		
		String msg;
		switch(str) {
		
		case "sure":
			msg="Are you sure to do this operation?";
			break;
		case "error":
			msg="Error!";
			break;
		default:
			msg=str;
			break;
		}
		
		int res=JOptionPane.showConfirmDialog(null, msg,"Dikkat !",JOptionPane.YES_NO_OPTION);
		
		if(res==0) {
			return true;
		}else {
			return false;
		}
		
	}
}
