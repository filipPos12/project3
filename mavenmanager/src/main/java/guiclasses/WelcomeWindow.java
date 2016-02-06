package guiclasses;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import managers.AdminManager;
import dbclasses.Admin;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;


public class WelcomeWindow {
	private JFrame frame;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomeWindow window = new WelcomeWindow();
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

	/**
	 * Create the application.
	 */
	public WelcomeWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		final JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(221, 132, 149, 19);
		frame.getContentPane().add(passwordField);
		
		final JTextField textField = new JTextField();
		textField.setBounds(220, 101, 150, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Remember me");
		chckbxNewCheckBox.setBounds(53, 176, 129, 23);
		frame.getContentPane().add(chckbxNewCheckBox);
		
		JButton btnLogin = new JButton("LOG IN");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//login
				List<Admin> l = AdminManager.getInstance().find(textField.getText());
				if (l.size() == 0) {
					JOptionPane.showMessageDialog(frame, "You shall not pass!");
					textField.setText("");
					passwordField.setText("");
				} else {
					IntroWindow.init();
					frame.setVisible(false);
					frame.dispose();
				}
			}
		});
		
		btnLogin.setBounds(253, 175, 117, 25);
		frame.getContentPane().add(btnLogin);
		
		JLabel lblNewLabel = new JLabel("USERNAME:");
		lblNewLabel.setBounds(68, 103, 114, 15);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("PASSWORD:");
		lblNewLabel_1.setBounds(68, 134, 123, 15);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("        WELCOME");
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 24));
		lblNewLabel_2.setBounds(87, 26, 283, 75);
		frame.getContentPane().add(lblNewLabel_2);
	}
}
