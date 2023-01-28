package hospital;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

import Helper.DBConnection;
import Helper.Helper;
import Helper.Helper.*;
import Model.Doctor;
import Model.HeadDoctor;
import Model.Patient;

public class LoginGUI extends JFrame {

	private JPanel w_pane;
	private JTextField fld_hastaTc;
	private JTextField fld_doctorTc;
	private JPasswordField fld_doctorPass;
	private DBConnection conn = new DBConnection();

	private JPasswordField fld_patientPass;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//BasicConfigurator.configure();
					LoginGUI frame = new LoginGUI();
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
	public LoginGUI() {
		setResizable(false);
		setTitle("Hospital");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		w_pane = new JPanel();
		w_pane.setBackground(new Color(255, 255, 255));
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lbl_logo = new JLabel(new ImageIcon(getClass().getResource("medicine.png")));
		lbl_logo.setBounds(127, 10, 200, 90);
		w_pane.add(lbl_logo);

		JLabel lblNewLabel = new JLabel("Hospital Management System");
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 17));
		lblNewLabel.setBounds(93, 99, 320, 30);
		w_pane.add(lblNewLabel);

		JTabbedPane w_tabpane = new JTabbedPane(JTabbedPane.TOP);
		w_tabpane.setBounds(10, 161, 466, 192);
		w_pane.add(w_tabpane);

		JPanel w_hastaLogin = new JPanel();
		w_tabpane.addTab("Login For Patient", null, w_hastaLogin, null);
		w_hastaLogin.setBackground(Color.WHITE);
		w_hastaLogin.setLayout(null);

		JLabel lblTcNumaranz = new JLabel("Identity Number:");
		lblTcNumaranz.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 17));
		lblTcNumaranz.setBounds(10, 10, 131, 30);
		w_hastaLogin.add(lblTcNumaranz);

		JLabel lblSifre = new JLabel("Password");
		lblSifre.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 17));
		lblSifre.setBounds(10, 37, 121, 30);
		w_hastaLogin.add(lblSifre);

		fld_hastaTc = new JTextField();
		fld_hastaTc.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 18));
		fld_hastaTc.setBounds(168, 15, 96, 19);
		w_hastaLogin.add(fld_hastaTc);
		fld_hastaTc.setColumns(10);

		JButton btn_register = new JButton("Register");
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				RegisterGUI rGUI = new RegisterGUI();
				rGUI.setVisible(true);
				dispose();

			}
		});
		btn_register.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 17));
		btn_register.setBounds(10, 100, 153, 44);
		w_hastaLogin.add(btn_register);

		JButton btn_hastaLogin = new JButton("Login");
		btn_hastaLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (fld_hastaTc.getText().length() == 0 || fld_patientPass.getText().length() == 0) {

				
					Helper.showMsg("fill");
					
				} else {
					boolean key=true;
					try {

						Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM user");

						while (rs.next()) {
							if (fld_hastaTc.getText().equals(rs.getString("identityNumber"))
									&& fld_patientPass.getText().equals(rs.getString("password"))) {
								if (rs.getString("type").equals("patient")) {
									Patient patient = new Patient();
									patient.setId(rs.getInt("id"));
									patient.setPassword("password");
									patient.setIdentityNumber(rs.getString("identityNumber"));
									patient.setName(rs.getString("name"));
									patient.setType(rs.getString("type"));
									// System.out.println(hd.getName());
									PatientGUI pGUI = new PatientGUI(patient);
									pGUI.setVisible(true);
									dispose();
									key = false;

								}

							}
						}

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (key) {
						Helper.showMsg("Error! Please register the system!");
					}
				}

			}
		});
		btn_hastaLogin.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 17));
		btn_hastaLogin.setBounds(263, 100, 140, 44);
		w_hastaLogin.add(btn_hastaLogin);

		fld_patientPass = new JPasswordField();
		fld_patientPass.setBounds(168, 46, 96, 19);
		w_hastaLogin.add(fld_patientPass);

		JPanel w_doctorLogin = new JPanel();
		w_doctorLogin.setBackground(Color.WHITE);
		w_tabpane.addTab("Login For Doctor", null, w_doctorLogin, null);
		w_doctorLogin.setLayout(null);

		JLabel lblTcNumaranz_1 = new JLabel("Identity Number:");
		lblTcNumaranz_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 17));
		lblTcNumaranz_1.setBounds(10, 10, 145, 30);
		w_doctorLogin.add(lblTcNumaranz_1);

		fld_doctorTc = new JTextField();
		fld_doctorTc.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 18));
		fld_doctorTc.setColumns(10);
		fld_doctorTc.setBounds(177, 15, 191, 19);
		w_doctorLogin.add(fld_doctorTc);

		JLabel lblSifre_1 = new JLabel("Password:");
		lblSifre_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 17));
		lblSifre_1.setBounds(10, 50, 121, 30);
		w_doctorLogin.add(lblSifre_1);

		JButton btn_doctorLogin = new JButton("Login");
		btn_doctorLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (fld_doctorTc.getText().length() == 0 || fld_doctorPass.getText().length() == 0) {

					
					
					Helper.showMsg("fill");
					
				} else {

					try {

						Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM user");

						while (rs.next()) {
							if (fld_doctorTc.getText().equals(rs.getString("identityNumber"))
									&& fld_doctorPass.getText().equals(rs.getString("password"))) {
								if (rs.getString("type").equals("headDoctor")) {
									HeadDoctor hd = new HeadDoctor();
									hd.setId(rs.getInt("id"));
									hd.setPassword("password");
									hd.setIdentityNumber(rs.getString("identityNumber"));
									hd.setName(rs.getString("name"));
									hd.setType(rs.getString("type"));
									// System.out.println(hd.getName());
									HeadDoctorGUI hdGUI = new HeadDoctorGUI(hd);
									hdGUI.setVisible(true);
									dispose();
								}
								if (rs.getString("type").equals("doctor")) {
									Doctor doctor = new Doctor();
									doctor.setId(rs.getInt("id"));
									doctor.setPassword("password");
									doctor.setIdentityNumber(rs.getString("identityNumber"));
									doctor.setName(rs.getString("name"));
									doctor.setType(rs.getString("type"));
									DoctorGUI dGUI = new DoctorGUI(doctor);
									dGUI.setVisible(true);
									dispose();

								}

							}
						}

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}
		});
		btn_doctorLogin.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 17));
		btn_doctorLogin.setBounds(151, 99, 201, 44);
		w_doctorLogin.add(btn_doctorLogin);

		fld_doctorPass = new JPasswordField();
		fld_doctorPass.setBounds(177, 59, 191, 19);
		w_doctorLogin.add(fld_doctorPass);
	}
}
