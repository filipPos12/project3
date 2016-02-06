package guiclasses;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;

import dbclasses.Sa;
import managers.SaManager;
import managers.SaPendingManager;
import managers.SaRejectedManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class SARequests {

	JFrame SAStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SARequests window = new SARequests();
					window.SAStatus.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SARequests() {
		initialize();
	}
	
	public static void init() {
		main(null);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		SAStatus = new JFrame();
		SAStatus.setBounds(200, 100, 600, 300);
		SAStatus.getContentPane().setLayout(null);
		
		final DefaultListModel<Sa> model = new DefaultListModel<Sa>();
		final JList<Sa> list = new JList<Sa>(model);		
		list.setBounds(22, 52, 550, 161);
		SAStatus.getContentPane().add(list);
		
		JLabel lblStatusOfAll = new JLabel("Status of all SAs");
		lblStatusOfAll.setBounds(125, 0, 135, 45);
		SAStatus.getContentPane().add(lblStatusOfAll);
		
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(75, 73, 450, 100);
		SAStatus.getContentPane().add(scrollPane);
		
		JButton btnDecline = new JButton("DECLINE");
		btnDecline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sa sa = list.getSelectedValue();
				if (sa != null) {
					SaPendingManager.getInstance().remove(sa.getHash());
					SaRejectedManager.getInstance().add(sa);
					reload(model);
				}
			}
		});
		btnDecline.setBounds(180, 225, 117, 25);
		SAStatus.getContentPane().add(btnDecline);
		
		JButton btnAccept = new JButton("ACCEPT");
		btnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sa sa = list.getSelectedValue();
				if (sa != null) {
					SaPendingManager.getInstance().remove(sa.getHash());
					SaManager.getInstance().add(sa);
					reload(model);
				}
			}
		});
		btnAccept.setBounds(32, 225, 117, 25);
		SAStatus.getContentPane().add(btnAccept);
		
		
		JButton btnUpdate = new JButton("Update list");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reload(model);		
			}
		});
		btnUpdate.setBounds(330, 225, 117, 25);
		SAStatus.getContentPane().add(btnUpdate);
		
		reload(model);
		
		SAStatus.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				IntroWindow.init();
				SAStatus.dispose();
			}
		});
	}
	
	void reload(DefaultListModel<Sa> model) {
		model.removeAllElements();
		for (Sa s :SaPendingManager.getInstance().all() ) {
			model.addElement(s);
		}
	}
}
