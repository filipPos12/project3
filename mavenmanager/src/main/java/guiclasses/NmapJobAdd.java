package guiclasses;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;

import javax.swing.JTextField;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.JComboBox;

import splitter.Splitter;
import dbclasses.NmapJob;
import dbclasses.Sa;
import managers.NmapJobManager;
import managers.SaManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;


public class NmapJobAdd {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NmapJobAdd window = new NmapJobAdd();
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
	public NmapJobAdd() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Add a new job to an agent ...");
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("ADD NMAPJOB");
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 22));
		lblNewLabel_1.setBounds(96, 0, 279, 34);
		frame.getContentPane().add(lblNewLabel_1);
		
		final JComboBox<Sa> comboBox = new JComboBox<Sa>();
		comboBox.setBounds(75, 37, 550, 24);
		frame.getContentPane().add(comboBox);
		
		final JTextArea textArea = new JTextArea();
		textArea.setColumns(25);
		textArea.setRows(7);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(75, 73, 550, 100);
		frame.getContentPane().add(scrollPane);
		
		JButton btnNewButton = new JButton("ADD");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sa s = (Sa)comboBox.getSelectedItem();
				
				if (s != null) {
					String hash = s.getHash();
					String text = textArea.getText().trim();
					
					String[] lines = text.split("\n");
					for (String l : lines) {
						Splitter splitter = new Splitter();
						NmapJob j = splitter.split(l);
						
						if (j != null) {
							j.setHash(hash);
							
							NmapJobManager.getInstance().add(j);
						}	
					}
				}
			}
		});
		btnNewButton.setBounds(608, 226, 117, 25);
		frame.getContentPane().add(btnNewButton);
		frame.setBounds(100, 100, 756, 320);
		
		JLabel lblNewLabel = new JLabel("ADD NMAPJOB");
		frame.getContentPane().add(lblNewLabel, BorderLayout.NORTH);
		
		
		
		
		JButton btnUpdate = new JButton("update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Sa> all = SaManager.getInstance().all();
				Sa[] array = all.toArray(new Sa[all.size()]);
				DefaultComboBoxModel<Sa> model = new DefaultComboBoxModel<Sa>(array);
				comboBox.setModel(model);
							
			}
		});
		btnUpdate.setBounds(640, 37, 85, 25);
		frame.getContentPane().add(btnUpdate);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				IntroWindow.init();
				frame.dispose();
			}
		});
		
		btnUpdate.doClick();
	}
}
