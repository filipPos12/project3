package guiclasses;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import managers.NmapResultManager;
import managers.SaManager;
import dbclasses.NmapJobResult;
import dbclasses.Sa;

public class NmapJobResults {

	private JFrame frame;
	private Sa sa;
	/**
	 * Launch the application.
	 */
	public static void main(final Sa sa) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if (sa == null) {
						NmapJobResults window = new NmapJobResults();
						window.frame.setVisible(true);
					} else {
						NmapJobResults window = new NmapJobResults(sa);
						window.frame.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public NmapJobResults() {
		this.sa = null;
		initialize();
	}
	
	public NmapJobResults(Sa sa) {
		this.sa = sa;
		initialize();
	}
	
	public static void init(Sa sa) {
		main(sa);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		if (sa == null) {
			frame.setTitle("Result list for all agents");
		} else {
			frame.setTitle("Result list for agent: " + sa.getDevicename() + " " + sa.getHash());
		}
		frame.setBounds(100, 100, 756, 600);
		frame.getContentPane().setLayout(null);
		
		final JList<NmapJobResult> list = new JList<NmapJobResult>();
		list.setBounds(22, 52, 341, 400);
		
		JLabel lblStatusOfAll = new JLabel("Results");
		lblStatusOfAll.setBounds(125, 0, 135, 45);
		frame.getContentPane().add(lblStatusOfAll);
		
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(75, 73, 611, 359);
		frame.getContentPane().add(scrollPane);
		
		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<NmapJobResult> all = NmapResultManager.getInstance().all(sa);
				
				if (all != null) { 
					NmapJobResult[] array = all.toArray(new NmapJobResult[all.size()]);
					DefaultListModel<NmapJobResult> model = new DefaultListModel<NmapJobResult>();
					for (NmapJobResult s : array) {
						model.addElement(s);
					}
					list.setModel(model);
				}
			}
		});
		btnUpdate.setBounds(184, 463, 117, 25);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnResults = new JButton("Results");
		btnResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NmapJobResult n = list.getSelectedValue();
				if (n != null) {
					JOptionPane.showMessageDialog(null, n.getRaw_text());					
				}
			}
		});
		btnResults.setBounds(32, 463, 117, 25);
		frame.getContentPane().add(btnResults);
		

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				IntroWindow.init();
				frame.dispose();
			}
		});

		btnUpdate.doClick();
	}
}
