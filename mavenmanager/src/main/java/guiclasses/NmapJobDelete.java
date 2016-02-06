package guiclasses;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JButton;

import managers.NmapJobManager;
import managers.NmapResultManager;
import managers.SaManager;
import splitter.Splitter;
import dbclasses.NmapJob;
import dbclasses.NmapJobResult;
import dbclasses.Sa;


public class NmapJobDelete {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NmapJobDelete window = new NmapJobDelete();
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
	public NmapJobDelete() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Delete a periodic job of an agent ...");
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("ADD NMAPJOB");
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 22));
		lblNewLabel_1.setBounds(96, 0, 279, 34);
		frame.getContentPane().add(lblNewLabel_1);
		
		final JComboBox<NmapJob> comboBox = new JComboBox<NmapJob>();
		comboBox.setBounds(75, 37, 550, 24);
		frame.getContentPane().add(comboBox);
		
		final JButton btnUpdate = new JButton("update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<NmapJob> all = NmapJobManager.getInstance().periodic();
				NmapJob[] array = all.toArray(new NmapJob[all.size()]);
				DefaultComboBoxModel<NmapJob> model = new DefaultComboBoxModel<NmapJob>(array);
				comboBox.setModel(model);
			}
		});
		
		JButton btnNewButton = new JButton("DELETE");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NmapJob j = (NmapJob) comboBox.getSelectedItem();
				if (j!=null) {
					NmapJobManager.getInstance().cancel(j.getId());
					btnUpdate.doClick();
				}
			}
		});
		btnNewButton.setBounds(608, 226, 117, 25);
		frame.getContentPane().add(btnNewButton);
		frame.setBounds(100, 100, 756, 320);
		
		JLabel lblNewLabel = new JLabel("ADD NMAPJOB");
		frame.getContentPane().add(lblNewLabel, BorderLayout.NORTH);
		
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
