package guiclasses;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import managers.AdminManager;
import managers.AdminPendingManager;
import managers.SaManager;
import managers.SaPendingManager;
import managers.SaRejectedManager;
import dbclasses.Admin;
import dbclasses.Sa;

public class AdminPending {
	private JFrame adminStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminPending window = new AdminPending();
					window.adminStatus.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AdminPending() {
		initialize();
	}
	
	public static void init() {
		main(null);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		adminStatus = new JFrame();
		adminStatus.setBounds(200, 100, 600, 300);
		adminStatus.getContentPane().setLayout(null);
		
		final DefaultListModel<Admin> model = new DefaultListModel<Admin>();
		final JList<Admin> list = new JList<Admin>(model);		
		list.setBounds(22, 52, 550, 161);
		adminStatus.getContentPane().add(list);
		
		JLabel lblStatusOfAll = new JLabel("Admin Pending");
		lblStatusOfAll.setBounds(125, 0, 135, 45);
		adminStatus.getContentPane().add(lblStatusOfAll);
		
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(75, 73, 450, 100);
		adminStatus.getContentPane().add(scrollPane);
		
		JButton btnDecline = new JButton("REJECT");
		btnDecline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Admin a = list.getSelectedValue();
				if (a != null) {					
					AdminPendingManager.getInstance().remove(a.getUsername());
					reload(model);
				}
			}
		});
		btnDecline.setBounds(180, 225, 117, 25);
		adminStatus.getContentPane().add(btnDecline);
		
		JButton btnAccept = new JButton("ACCEPT");
		btnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Admin a = list.getSelectedValue();
				if (a != null) {
					AdminPendingManager.getInstance().remove(a.getUsername());
					AdminManager.getInstance().add(a);
					reload(model);
				}
			}
		});
		btnAccept.setBounds(32, 225, 117, 25);
		adminStatus.getContentPane().add(btnAccept);
	
		
		reload(model);
		
		adminStatus.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				IntroWindow.init();
				adminStatus.dispose();
			}
		});
	}
	
	void reload(DefaultListModel<Admin> model) {
		model.removeAllElements();
		for (Admin s :AdminPendingManager.getInstance().all() ) {
			model.addElement(s);
		}
	}
}



