package hospital;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Model.Doctor;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import com.toedter.calendar.JDateChooser;

import Helper.Helper;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;

public class DoctorGUI extends JFrame {

	private JPanel w_pane;
	private static Doctor doctor=new Doctor();
	private JTable table_whour;
	private DefaultTableModel whourModel;
	private Object[] whourData=null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorGUI frame = new DoctorGUI(doctor);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public DoctorGUI(Doctor doctor) throws SQLException {
		
		whourModel=new DefaultTableModel();
		Object[]colWhour=new Object[2];
		colWhour[0]="ID";
		colWhour[1]="Date";
		whourModel.setColumnIdentifiers(colWhour);
		whourData=new Object[2];
		for(int i=0; i<doctor.getWhourList(doctor.getId()).size();i++) {
			whourData[0]=doctor.getWhourList(doctor.getId()).get(i).getId();
			whourData[1]=doctor.getWhourList(doctor.getId()).get(i).getWdate();
			whourModel.addRow(whourData);
		}
		
		
		setResizable(false);
		setTitle("Hospital");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Welcome,Dear "+doctor.getName());
		lblNewLabel.setFont(new Font("Yu Gothic Medium", Font.BOLD, 17));
		lblNewLabel.setBounds(10, 23, 281, 28);
		w_pane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Log out");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				LoginGUI login=new LoginGUI();
				login.setVisible(true);
				dispose();
			
			
			}
		});
		btnNewButton.setFont(new Font("Yu Gothic Medium", Font.BOLD, 17));
		btnNewButton.setBounds(515, 24, 134, 28);
		w_pane.add(btnNewButton);
		
		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBounds(21, 112, 570, 301);
		w_pane.add(w_tab);
		
		JPanel w_whour = new JPanel();
		w_tab.addTab("Working Hours", null, w_whour, null);
		w_whour.setLayout(null);
		
		JDateChooser select_date = new JDateChooser();
		select_date.setBounds(10, 28, 91, 30);
		w_whour.add(select_date);
		
		JComboBox select_time = new JComboBox();
		select_time.setModel(new DefaultComboBoxModel(new String[] {"10:00", "10:30", "11:00", "11:30", "10:00", "13:30", "14:00", "14:30", "15:00", "15:30", ""}));
		select_time.setBounds(126, 28, 85, 30);
		w_whour.add(select_time);
		
		
		
		JButton btn_addWhour = new JButton("Add");
		btn_addWhour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				String date="";
				try {
					 date=sdf.format(select_date.getDate());
				}catch(Exception e2) {
					
				}
				
				
				if(date.length()==0) {
					Helper.showMsg("Please select date");
				}
				else {
					
					String time=" "+ select_time.getSelectedItem().toString()+":00";
					String selectDate=date+time;
					try {
						boolean control=doctor.addWhour(doctor.getId(), doctor.getName(), selectDate);
						if(control) {
							Helper.showMsg("success");
							updateWhourModel(doctor);
						}else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
				
				
				
			}
		});
		btn_addWhour.setFont(new Font("Yu Gothic Medium", Font.BOLD, 17));
		btn_addWhour.setBounds(243, 30, 134, 28);
		w_whour.add(btn_addWhour);
		
		JScrollPane w_scrollWhour = new JScrollPane();
		w_scrollWhour.setBounds(29, 90, 458, 174);
		w_whour.add(w_scrollWhour);
		
		table_whour = new JTable(whourModel);
		w_scrollWhour.setViewportView(table_whour);
	}
	
	public void updateWhourModel(Doctor doctor) throws SQLException {

		DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
		clearModel.setRowCount(0);
		
		for(int i=0; i<doctor.getWhourList(doctor.getId()).size();i++) {
			whourData[0]=doctor.getWhourList(doctor.getId()).get(i).getId();
			whourData[1]=doctor.getWhourList(doctor.getId()).get(i).getWdate();
			whourModel.addRow(whourData);
		}
		

	}
}
