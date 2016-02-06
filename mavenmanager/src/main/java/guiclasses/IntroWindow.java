package guiclasses;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class IntroWindow {
	private JFrame frame;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IntroWindow window = new IntroWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void init() {
		main(null);
	}
	
	public IntroWindow() {
		frame = new JFrame("Menu");
		frame.setBounds(200, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridLayout g = new GridLayout(9,1);
		g.setHgap(5);
		g.setVgap(10);
		frame.getContentPane().setLayout(g);
		
		frame.getContentPane().add(new JLabel("                                                       Select a choice:"));
		
		JButton btnAcceptReject = new JButton("Accept or reject a request of an agent");
		frame.getContentPane().add(btnAcceptReject);
		
		JButton btnViewAgents = new JButton("View agents and their results");
		frame.getContentPane().add(btnViewAgents);
		
		JButton btnAcceptRejectPending = new JButton("Admin Pending");
		frame.getContentPane().add(btnAcceptRejectPending);
		
		JButton btnAddJobs = new JButton("Assign a job to an agent");
		frame.getContentPane().add(btnAddJobs);
		
		JButton btnDeleteJobs = new JButton("Delete a job of an agent");
		frame.getContentPane().add(btnDeleteJobs);
		
//		JButton btnViewResults = new JButton("View results of an agent");
//		frame.getContentPane().add(btnViewResults);
		
		JButton btnViewAllResults = new JButton("View results of all agents");
		frame.getContentPane().add(btnViewAllResults);
		
		JButton btnExit = new JButton("Shutdown server");
		frame.getContentPane().add(btnExit);
		
		JButton btnLogout = new JButton("Logout");
		frame.getContentPane().add(btnLogout);
		
		btnAcceptReject.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SARequests.init();
				frame.setVisible(false);
				frame.dispose(); 
			}
		});
		
		btnViewAgents.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SAStatus.init();
				frame.setVisible(false);
				frame.dispose(); 
			}
		});
		
		
		btnAddJobs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NmapJobAdd.init();
				frame.setVisible(false);
				frame.dispose(); 
			}
		});
		
		btnDeleteJobs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NmapJobDelete.init();
				frame.setVisible(false);
				frame.dispose(); 
				
			}
		});
		btnAcceptRejectPending.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AdminPending.init();
				frame.setVisible(false);
				frame.dispose(); 
				
			}
		});
		
//		btnViewResults.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				NmapJobResults.init(null);
//				frame.setVisible(false);
//				frame.dispose(); 
//			}
//		});
		
		
		btnViewAllResults.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NmapJobResults.init(null);
				frame.setVisible(false);
				frame.dispose(); 
				
			}
		});
		
		
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		btnLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				WelcomeWindow.init();
				frame.setVisible(false);
				frame.dispose(); 
			}
		});
	}

}
