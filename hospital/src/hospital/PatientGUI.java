package hospital;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Helper.Helper;
import Helper.Item;
import Model.Clinic;
import Model.Patient;
import Model.Whour;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class PatientGUI extends JFrame {

	private JPanel w_pane;
	private static Patient patient=new Patient();
	private Clinic clinic=new Clinic();
	private JTable table_doctor;
	private DefaultTableModel doctorModel;
	private Object[] doctorData=null;
	private JTable table_whour;
	private Whour whour=new Whour();
	private DefaultTableModel whourModel;
	private Object[] whourData=null;
	private int selectDoctorID=0;
	private String selectDoctorName=null;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PatientGUI frame = new PatientGUI(patient);
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
	public PatientGUI(Patient patient) throws SQLException {
		
		
		doctorModel = new DefaultTableModel();
		Object[] colDoctor = new Object[2];
		colDoctor[0] = "ID";
		colDoctor[1] = "Name";
		doctorModel.setColumnIdentifiers(colDoctor);
		doctorData = new Object[2];
		
		

		whourModel = new DefaultTableModel();
		Object[] colWhour = new Object[2];
		colWhour[0] = "ID";
		colWhour[1] = "Date";
		whourModel.setColumnIdentifiers(colWhour);
		whourData = new Object[2];


		

		
		
		
		
		
		
		setResizable(false);
		setTitle("Hospital");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Welcome,Dear "+patient.getName());
		lblNewLabel.setFont(new Font("Yu Gothic Medium", Font.BOLD, 17));
		lblNewLabel.setBounds(10, 10, 281, 28);
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
		btnNewButton.setBounds(277, 10, 134, 28);
		w_pane.add(btnNewButton);
		
		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBounds(10, 134, 603, 292);
		w_pane.add(w_tab);
		
		JPanel w_appointment = new JPanel();
		w_appointment.setBackground(Color.WHITE);
		w_tab.addTab("Appointment System", null, w_appointment, null);
		w_appointment.setLayout(null);
		
		JScrollPane w_scrollDoctor = new JScrollPane();
		w_scrollDoctor.setBounds(10, 32, 172, 223);
		w_appointment.add(w_scrollDoctor);
		
		table_doctor = new JTable(doctorModel);
		w_scrollDoctor.setViewportView(table_doctor);
		
		JLabel lblNewLabel_1 = new JLabel("Doctor List");
		lblNewLabel_1.setBounds(10, 9, 101, 13);
		w_appointment.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Clinic Name");
		lblNewLabel_2.setBounds(214, 9, 71, 13);
		w_appointment.add(lblNewLabel_2);
		
		JComboBox select_clinic = new JComboBox();
		select_clinic.setBounds(190, 30, 142, 21);
		select_clinic.addItem("---Select Clinic---");
		for(int i=0; i<clinic.getList().size();i++) {
			
			select_clinic.addItem(new Item(clinic.getList().get(i).getId(),clinic.getList().get(i).getName()));
		}
		
		select_clinic.addActionListener(new ActionListener() {

			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				if(select_clinic.getSelectedIndex()!=0) {
					JComboBox c=(JComboBox) e.getSource();
					Item item=(Item) c.getSelectedItem();
					//System.out.println(item.getKey()+"-"+item.getValue());
					DefaultTableModel clearModel=(DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0);
					try {
						for(int i=0; i<clinic.getClinicDoctorList(item.getKey()).size();i++) {
							
							doctorData[0]=clinic.getClinicDoctorList(item.getKey()).get(i).getId();
							doctorData[1]=clinic.getClinicDoctorList(item.getKey()).get(i).getName();
							doctorModel.addRow(doctorData);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}else {
					
					DefaultTableModel clearModel=(DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0);
				}
				
			}
				
				
				
		});
			
		
		
		
		w_appointment.add(select_clinic);
		
		JLabel lblNewLabel_2_1 = new JLabel("Select Doctor");
		lblNewLabel_2_1.setBounds(200, 81, 85, 13);
		w_appointment.add(lblNewLabel_2_1);
		
		JButton btn_selDoctor = new JButton("Select");
		btn_selDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				int row=table_doctor.getSelectedRow();
				if(row>=0) {
					
					String value=table_doctor.getModel().getValueAt(row, 0).toString();
					int id=Integer.parseInt(value);
					DefaultTableModel clearModel=(DefaultTableModel) table_whour.getModel();
					clearModel.setRowCount(0);
					
					
					try {
						for(int i=0; i<whour.getWhourList(id).size();i++) {
							
							whourData[0]=whour.getWhourList(id).get(i).getId();
							whourData[1]=whour.getWhourList(id).get(i).getWdate();
							whourModel.addRow(whourData);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					table_whour.setModel(whourModel);
					selectDoctorID=id;
					selectDoctorName=table_doctor.getModel().getValueAt(row, 1).toString();
					//System.out.println(selectDoctorID+"-"+selectDoctorName);
					
					
					
				}else {
					
					Helper.showMsg("Please select doctor");
				}
			
			
			
			}
		});
		btn_selDoctor.setBounds(192, 94, 118, 21);
		w_appointment.add(btn_selDoctor);
		
		JScrollPane w_scrollWhour = new JScrollPane();
		w_scrollWhour.setBounds(342, 32, 210, 223);
		w_appointment.add(w_scrollWhour);
		
		table_whour = new JTable();
		w_scrollWhour.setViewportView(table_whour);
		
		JLabel lblNewLabel_1_1 = new JLabel("Doctor List");
		lblNewLabel_1_1.setBounds(342, 9, 101, 13);
		w_appointment.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("Appointment");
		lblNewLabel_2_1_1.setBounds(200, 147, 85, 13);
		w_appointment.add(lblNewLabel_2_1_1);
		
		JButton btn_addAppoint = new JButton("Appointment");
		btn_addAppoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			
				int selRow=table_whour.getSelectedRow();
				if(selRow>=0) {
					
					String date=table_whour.getModel().getValueAt(selRow,1).toString();
					try {
						boolean control=patient.addAppointment(selectDoctorID, patient.getId(), selectDoctorName, patient.getName(), date);
						if(control) {
							
							Helper.showMsg("success");
							patient.updateWhourStatus(selectDoctorID, date);
							updateWhourModel(selectDoctorID);
							
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}else {
					Helper.showMsg("Please enter available date");
				}
			
			
			}
		});
		btn_addAppoint.setBounds(192, 170, 118, 21);
		w_appointment.add(btn_addAppoint);
	}
	public void updateWhourModel(int doctor_id) throws SQLException {
		DefaultTableModel clearModel=(DefaultTableModel) table_whour.getModel();
		clearModel.setRowCount(0);
		for(int i=0; i<whour.getWhourList(doctor_id).size();i++) {
			
			whourData[0]=whour.getWhourList(doctor_id).get(i).getId();
			whourData[1]=whour.getWhourList(doctor_id).get(i).getWdate();
			whourModel.addRow(whourData);
		}
	}
	

}
