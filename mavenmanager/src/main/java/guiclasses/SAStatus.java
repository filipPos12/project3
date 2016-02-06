package guiclasses;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;

import managers.SaManager;
import dbclasses.Sa;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;


public class SAStatus {

	private JFrame SAStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SAStatus window = new SAStatus();
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
	public SAStatus() {
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
		SAStatus.setTitle("Agent list");
		SAStatus.setBounds(100, 100, 756, 320);
		SAStatus.getContentPane().setLayout(null);
		
		final JList<Sa> list = new JList<Sa>();
		list.setBounds(22, 52, 341, 161);
		
		JLabel lblStatusOfAll = new JLabel("Status of all SAs");
		lblStatusOfAll.setBounds(125, 0, 135, 45);
		SAStatus.getContentPane().add(lblStatusOfAll);
		
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(75, 73, 550, 100);
		SAStatus.getContentPane().add(scrollPane);
		
		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Sa> all = SaManager.getInstance().all();
				
				Sa[] array = all.toArray(new Sa[all.size()]);
				DefaultListModel<Sa> model = new DefaultListModel<Sa>();
				for (Sa s : array) {
					model.addElement(s);
				}
				list.setModel(model);
			}
		});
		btnUpdate.setBounds(180, 225, 117, 25);
		SAStatus.getContentPane().add(btnUpdate);
		
		JButton btnResults = new JButton("Results");
		btnResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sa sa = list.getSelectedValue();
				if (sa != null) {
					NmapJobResults.init(sa);
					SAStatus.setVisible(false);
					SAStatus.dispose();
				}
			}
		});
		btnResults.setBounds(32, 225, 117, 25);
		SAStatus.getContentPane().add(btnResults);
		

		SAStatus.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				IntroWindow.init();
				SAStatus.dispose();
			}
		});

		btnUpdate.doClick();
	}
}
