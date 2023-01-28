package hospital;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import Helper.Helper;
import Model.Patient;
import Model.User;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class RegisterGUI extends JFrame {

	private JPanel w_pane;
	private JTextField fld_name;
	private JTextField fld_tcno;
	private JPasswordField fld_pass;
	private Patient patient=new Patient();
	private static Logger logger = LogManager.getLogger(RegisterGUI.class.getName());

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RegisterGUI() {
		setResizable(false);
		setTitle("Hospital");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 330);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Name");
		lblNewLabel_1.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		lblNewLabel_1.setBounds(10, 23, 76, 21);
		w_pane.add(lblNewLabel_1);
		
		fld_name = new JTextField();
		fld_name.setColumns(10);
		fld_name.setBounds(10, 50, 136, 19);
		w_pane.add(fld_name);
		
		JLabel lblNewLabel_1_1 = new JLabel("Identity Number");
		lblNewLabel_1_1.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		lblNewLabel_1_1.setBounds(10, 88, 136, 21);
		w_pane.add(lblNewLabel_1_1);
		
		fld_tcno = new JTextField();
		fld_tcno.setColumns(10);
		fld_tcno.setBounds(10, 109, 136, 19);
		w_pane.add(fld_tcno);
		
		JLabel lblNewLabel_1_2 = new JLabel("Password");
		lblNewLabel_1_2.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		lblNewLabel_1_2.setBounds(10, 148, 76, 21);
		w_pane.add(lblNewLabel_1_2);
		
		fld_pass = new JPasswordField();
		fld_pass.setBounds(10, 179, 167, 26);
		w_pane.add(fld_pass);
		
		JButton btn_register = new JButton("Register");
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			if(fld_tcno.getText().length()==0 || fld_pass.getText().length()==0 || fld_name.getText().length()==0) {
				
				Helper.showMsg("fill");
				logger.error("Please fill up the informations");
				
			}else {
				try {
					boolean control=patient.register(fld_tcno.getText(),fld_pass.getText(), fld_name.getText());
					if(control) {
						Helper.showMsg("success");
						logger.info("SUCCESSFULL REGISTER");
						LoginGUI login=new LoginGUI();
						login.setVisible(true);
						dispose();
						
					}else {
						
						Helper.showMsg("error");
						logger.error("ERROR!");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			 
			
			
			}
		});
		btn_register.setBounds(10, 215, 136, 32);
		w_pane.add(btn_register);
		
		JButton btn_basckto = new JButton("Return Back");
		btn_basckto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				LoginGUI login=new LoginGUI();
				login.setVisible(true);
				logger.info("RETURN BACK");
				dispose();
			}
		});
		btn_basckto.setBounds(10, 257, 136, 32);
		w_pane.add(btn_basckto);
	}
}
