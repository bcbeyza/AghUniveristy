package hospital;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.*;
import Helper.Helper;
import Helper.Item;
import Model.Clinic;
import Model.HeadDoctor;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.Point;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class HeadDoctorGUI extends JFrame {

	static HeadDoctor headDoctor = new HeadDoctor();
	Clinic clinic = new Clinic();
	private JTextField fld_dName;
	private JTextField fld_Tcno;
	private JTextField fld_dPass;
	private JTextField fld_doctorID;
	private JTable table_doctor;

	private DefaultTableModel doctorModel = null;
	private Object[] doctorData = null;
	private JTable table_clinic;
	private JTextField fld_clinicName;
	private DefaultTableModel clinicModel = null;
	private Object[] clinicData = null;
	private JPopupMenu clinicMenu;
	private JTable table_worker;
	private static Logger logger = LogManager.getLogger(HeadDoctorGUI.class.getName());

	/**
	 * 
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HeadDoctorGUI frame = new HeadDoctorGUI(headDoctor);
					logger.info("HEAD DOCTOR GUI PAGE");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public HeadDoctorGUI(HeadDoctor headDoctor) throws SQLException {

		// Doctor Model
		doctorModel = new DefaultTableModel();
		Object[] colDoctorName = new Object[4];
		colDoctorName[0] = "ID";
		colDoctorName[1] = "Name";
		colDoctorName[2] = "Identity Numer";
		colDoctorName[3] = "Password";

		doctorModel.setColumnIdentifiers(colDoctorName);
		doctorData = new Object[4];

		for (int i = 0; i < headDoctor.getDoctorList().size(); i++) {
			doctorData[0] = headDoctor.getDoctorList().get(i).getId();
			doctorData[1] = headDoctor.getDoctorList().get(i).getName();
			doctorData[2] = headDoctor.getDoctorList().get(i).getIdentityNumber();
			doctorData[3] = headDoctor.getDoctorList().get(i).getPassword();

			doctorModel.addRow(doctorData);

		}

		// Clinic Model

		clinicModel = new DefaultTableModel();
		Object[] colClinic = new Object[2];
		colClinic[0] = "ID";
		colClinic[1] = "Clinic Name";

		clinicModel.setColumnIdentifiers(colClinic);
		clinicData = new Object[2];

		for (int i = 0; i < clinic.getList().size(); i++) {
			clinicData[0] = clinic.getList().get(i).getId();
			clinicData[1] = clinic.getList().get(i).getName();
			clinicModel.addRow(clinicData);

		}

		// WorkerModel

		DefaultTableModel workerModel = new DefaultTableModel();
		Object[] colWorker = new Object[2];
		colWorker[0] = "ID";
		colWorker[1] = "Name Surname";
		workerModel.setColumnIdentifiers(colWorker);
		Object[] workerData = new Object[2];

		setBackground(Color.WHITE);
		setTitle("Hospital");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		JPanel w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Welcome,Dear " + headDoctor.getName());
		lblNewLabel.setFont(new Font("Yu Gothic Medium", Font.BOLD, 17));
		lblNewLabel.setBounds(10, 23, 281, 28);
		w_pane.add(lblNewLabel);

		JButton btnNewButton = new JButton("Log out");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				logger.info("HEAD DOCTOR GUI PAGE LOG OUT");
				dispose();

			}
		});
		btnNewButton.setFont(new Font("Yu Gothic Medium", Font.BOLD, 17));
		btnNewButton.setBounds(452, 23, 134, 28);
		w_pane.add(btnNewButton);

		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setFont(new Font("Yu Gothic Medium", Font.BOLD, 17));
		w_tab.setBounds(51, 156, 646, 329);
		w_pane.add(w_tab);

		JPanel w_clinic = new JPanel();
		w_clinic.setBackground(Color.WHITE);
		w_tab.addTab("Clinics", null, w_clinic, null);
		w_clinic.setLayout(null);

		JScrollPane w_scrollClinic = new JScrollPane();
		w_scrollClinic.setBounds(10, 10, 247, 267);
		w_clinic.add(w_scrollClinic);

		clinicMenu = new JPopupMenu();
		JMenuItem updateMenu = new JMenuItem("Update");
		JMenuItem deleteMenu = new JMenuItem("Delete");
		clinicMenu.add(updateMenu);
		clinicMenu.add(deleteMenu);

		updateMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int selID = Integer.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(), 0).toString());
				Clinic selectClinic = clinic.getFetch(selID);
				UpdateClinicGUI updateGUI = new UpdateClinicGUI(selectClinic);
				updateGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				updateGUI.setVisible(true);
				updateGUI.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {

						try {
							updateClinicModel();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});

			}

		});

		deleteMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (Helper.confirm("sure")) {
					int selID = Integer.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(), 0).toString());
					try {
						if (clinic.deleteClinic(selID)) {

							Helper.showMsg("success");

							try {
								updateClinicModel();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});

		table_clinic = new JTable(clinicModel);
		table_clinic.setComponentPopupMenu(clinicMenu);
		table_clinic.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				Point point = e.getPoint();
				int selectedRow = table_clinic.rowAtPoint(point);
				table_clinic.setRowSelectionInterval(selectedRow, selectedRow);

			}
		});

		w_scrollClinic.setViewportView(table_clinic);

		JLabel lblNewLabel_1_2 = new JLabel("Clinic Name");
		lblNewLabel_1_2.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		lblNewLabel_1_2.setBounds(276, 5, 113, 21);
		w_clinic.add(lblNewLabel_1_2);

		fld_clinicName = new JTextField();
		fld_clinicName.setColumns(10);
		fld_clinicName.setBounds(267, 36, 136, 19);
		w_clinic.add(fld_clinicName);

		JButton btn_addClinic = new JButton("ADD");
		btn_addClinic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (fld_clinicName.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					try {
						if (clinic.addClinic(fld_clinicName.getText())) {

							Helper.showMsg("success");
							fld_clinicName.setText(null);
							updateClinicModel();
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});

		btn_addClinic.setFont(new Font("Yu Gothic", Font.BOLD, 17));
		btn_addClinic.setBounds(267, 65, 136, 31);
		w_clinic.add(btn_addClinic);

		JScrollPane w_scrollWorker = new JScrollPane();
		w_scrollWorker.setBounds(446, 10, 185, 267);
		w_clinic.add(w_scrollWorker);

		table_worker = new JTable();
		w_scrollWorker.setViewportView(table_worker);

		JComboBox select_doctor = new JComboBox();
		select_doctor.setBounds(282, 205, 136, 31);
		for (int i = 0; i < headDoctor.getDoctorList().size(); i++) {

			select_doctor.addItem(
					new Item(headDoctor.getDoctorList().get(i).getId(), headDoctor.getDoctorList().get(i).getName()));
		}
		select_doctor.addActionListener(e -> {

			JComboBox c = (JComboBox) e.getSource();
			Item item = (Item) c.getSelectedItem();
			System.out.println(item.getKey() + ":" + item.getValue());
		});

		w_clinic.add(select_doctor);

		JButton btn_addWorker = new JButton("ADD");
		btn_addWorker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int selRow = table_clinic.getSelectedRow();
				if (selRow >= 0) {
					String selClinic = table_clinic.getModel().getValueAt(selRow, 0).toString();
					int selClinicID = Integer.parseInt(selClinic);
					Item doctorItem = (Item) select_doctor.getSelectedItem();
					try {
						boolean control = headDoctor.addWorker(doctorItem.getKey(), selClinicID);
						if (control) {
							Helper.showMsg("success");
						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {

					Helper.showMsg("Please chose clinic!");
				}

			}
		});
		btn_addWorker.setFont(new Font("Yu Gothic", Font.BOLD, 17));
		btn_addWorker.setBounds(282, 246, 136, 31);
		w_clinic.add(btn_addWorker);

		JLabel lblNewLabel_1_2_1 = new JLabel("Clinic Name");
		lblNewLabel_1_2_1.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		lblNewLabel_1_2_1.setBounds(282, 124, 113, 21);
		w_clinic.add(lblNewLabel_1_2_1);

		JButton btn_workerSelect = new JButton("SELECT");
		btn_workerSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int selRow = table_clinic.getSelectedRow();
				if (selRow >= 0) {

					String selClinic = table_clinic.getModel().getValueAt(selRow, 0).toString();
					int selClinicID = Integer.parseInt(selClinic);
					DefaultTableModel clearModel = (DefaultTableModel) table_worker.getModel();
					clearModel.setRowCount(0);

					try {
						for (int i = 0; i < headDoctor.getClinicDoctorList(selClinicID).size(); i++) {

							workerData[0] = headDoctor.getClinicDoctorList(selClinicID).get(i).getId();
							workerData[1] = headDoctor.getClinicDoctorList(selClinicID).get(i).getName();
							workerModel.addRow(workerData);
						}
					} catch (SQLException e1) {

						e1.printStackTrace();
					}
					table_worker.setModel(workerModel);
				} else {

					Helper.showMsg("Please select clinic!");
				}

			}

		});
		btn_workerSelect.setFont(new Font("Yu Gothic", Font.BOLD, 17));
		btn_workerSelect.setBounds(282, 149, 136, 31);
		w_clinic.add(btn_workerSelect);

		JPanel w_doctor = new JPanel();
		w_tab.addTab("Doctor Management", null, w_doctor, null);
		w_doctor.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Name");
		lblNewLabel_1.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		lblNewLabel_1.setBounds(475, 0, 76, 21);
		w_doctor.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Identity Number");
		lblNewLabel_1_1.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		lblNewLabel_1_1.setBounds(475, 49, 136, 21);
		w_doctor.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_1_1 = new JLabel("Password");
		lblNewLabel_1_1_1.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		lblNewLabel_1_1_1.setBounds(475, 99, 136, 21);
		w_doctor.add(lblNewLabel_1_1_1);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("User ID");
		lblNewLabel_1_1_1_1.setFont(new Font("Yu Gothic UI", Font.BOLD, 15));
		lblNewLabel_1_1_1_1.setBounds(485, 188, 136, 21);
		w_doctor.add(lblNewLabel_1_1_1_1);

		fld_dName = new JTextField();
		fld_dName.setBounds(475, 20, 136, 19);
		w_doctor.add(fld_dName);
		fld_dName.setColumns(10);

		fld_Tcno = new JTextField();
		fld_Tcno.setColumns(10);
		fld_Tcno.setBounds(475, 70, 136, 19);
		w_doctor.add(fld_Tcno);

		fld_dPass = new JTextField();
		fld_dPass.setColumns(10);
		fld_dPass.setBounds(475, 118, 136, 19);
		w_doctor.add(fld_dPass);

		fld_doctorID = new JTextField();
		fld_doctorID.setColumns(10);
		fld_doctorID.setBounds(475, 211, 136, 19);
		w_doctor.add(fld_doctorID);

		JButton btn_addDoctor = new JButton("Add");
		btn_addDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (fld_dName.getText().length() == 0 || fld_dPass.getText().length() == 0
						|| fld_Tcno.getText().length() == 0) {

					Helper.showMsg("fill");
					logger.error("ERROR!");
				} else {

					try {
						boolean control = headDoctor.addDoctor(fld_Tcno.getText(), fld_dPass.getText(),
								fld_dName.getText());
						if (control) {
							Helper.showMsg("success");
							logger.info("SUCESSFULL");
							fld_dName.setText(null);
							fld_Tcno.setText(null);
							fld_dPass.setText(null);
							updateDoctorModel();
						}
					} catch (SQLException e1) {

						e1.printStackTrace();
					}
				}

			}
		});
		btn_addDoctor.setFont(new Font("Yu Gothic", Font.BOLD, 17));
		btn_addDoctor.setBounds(475, 147, 136, 31);
		w_doctor.add(btn_addDoctor);

		JButton btn_delDoctor = new JButton("Delete");
		btn_delDoctor.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (fld_doctorID.getText().length() == 0) {
					Helper.showMsg("Error! ");
				} else {

					if (Helper.confirm("sure")) {
						int selectID = Integer.parseInt(fld_doctorID.getText());
						try {
							boolean control = headDoctor.deleteDoctor(selectID);
							if (control) {
								Helper.showMsg("success");
								logger.info("success");
								fld_doctorID.setText(null);
								updateDoctorModel();
							}
						} catch (SQLException e1) {

							e1.printStackTrace();
						}
					}
				}

			}
		});
		btn_delDoctor.setFont(new Font("Yu Gothic UI", Font.BOLD, 17));
		btn_delDoctor.setBounds(475, 244, 136, 33);
		w_doctor.add(btn_delDoctor);

		JScrollPane w_scrollDoctor = new JScrollPane();
		w_scrollDoctor.setBounds(29, 20, 382, 242);
		w_doctor.add(w_scrollDoctor);

		table_doctor = new JTable(doctorModel);
		w_scrollDoctor.setViewportView(table_doctor);

		table_doctor.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				try {
					fld_doctorID.setText(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString());
				} catch (Exception ex) {

				}

			}
		});

		table_doctor.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {

				if (e.getType() == TableModelEvent.UPDATE) {
					int selectID = Integer
							.parseInt(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString());
					String selectName = table_doctor.getValueAt(table_doctor.getSelectedRow(), 1).toString();
					String selectIdentityNumber = table_doctor.getValueAt(table_doctor.getSelectedRow(), 2).toString();
					String selectPass = table_doctor.getValueAt(table_doctor.getSelectedRow(), 3).toString();

					try {
						headDoctor.updateDoctor(selectID, selectIdentityNumber, selectPass, selectName);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			}
		});

	}

	public void updateDoctorModel() throws SQLException {

		DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < headDoctor.getDoctorList().size(); i++) {
			doctorData[0] = headDoctor.getDoctorList().get(i).getId();
			doctorData[1] = headDoctor.getDoctorList().get(i).getName();
			doctorData[2] = headDoctor.getDoctorList().get(i).getIdentityNumber();
			doctorData[3] = headDoctor.getDoctorList().get(i).getPassword();

			doctorModel.addRow(doctorData);

		}

	}

	public void updateClinicModel() throws SQLException {

		DefaultTableModel clearModel = (DefaultTableModel) table_clinic.getModel();
		clearModel.setRowCount(0);

		for (int i = 0; i < clinic.getList().size(); i++) {
			clinicData[0] = clinic.getList().get(i).getId();
			clinicData[1] = clinic.getList().get(i).getName();
			clinicModel.addRow(clinicData);

		}
	}
}
