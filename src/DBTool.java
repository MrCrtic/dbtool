import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Choice;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.swing.JCheckBox;


public class DBTool {

	private JFrame frmDbtool;
	private JTextField textField;
	JFileChooser chooser;
	String choosertitle = "Select Folder";
	String backupLocation;
	String dbName="backup";
	private final Action action = new SwingAction();
	private JTextField hostName;
	private JTextField userName;
	private JTextField password;
	private JTextField databaseName;
	public String executeCmd;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DBTool window = new DBTool();
					window.frmDbtool.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DBTool() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDbtool = new JFrame();
		frmDbtool.setTitle("DBTool");
		frmDbtool.setBounds(100, 100, 450, 300);
		frmDbtool.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDbtool.getContentPane().setLayout(null);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				 chooser = new JFileChooser(); 
				    chooser.setCurrentDirectory(new java.io.File("."));
				    chooser.setDialogTitle(choosertitle);
				    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				   
				    chooser.setAcceptAllFileFilterUsed(false);
				    //    
				    if (chooser.showOpenDialog(frmDbtool) == JFileChooser.APPROVE_OPTION) { 
				      System.out.println("getCurrentDirectory(): " 
				         +  chooser.getCurrentDirectory());
				      System.out.println("getSelectedFile() : " 
				         +  chooser.getSelectedFile());
				      
				      String dbName = databaseName.getText().toString();
				      backupLocation = chooser.getSelectedFile()+"/"+dbName+".sql";
				      textField.setText(backupLocation);
				      }
				    else {
				      System.out.println("No Selection ");
				      }
			}
		});
	
		btnBrowse.setBounds(272, 163, 98, 25);
		frmDbtool.getContentPane().add(btnBrowse);
		
		textField = new JTextField();
		textField.setBounds(22, 163, 238, 25);
		frmDbtool.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblSaveBackupTo = new JLabel("Save backup to:");
		lblSaveBackupTo.setBounds(22, 136, 124, 15);
		frmDbtool.getContentPane().add(lblSaveBackupTo);
		
		JButton btnNewButton = new JButton("Create Database backup");
		btnNewButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
			if(hostName.getText().length()==0 || userName.getText().length()==0 || databaseName.getText().length()==0){
				JOptionPane.showMessageDialog(frmDbtool, "Please input Host, Username and Database fields!");
			}else{
				try {
					 String dbName = databaseName.getText().toString();
					 String dbUser = userName.getText().toString();
					 String dbPassword = password.getText().toString();
					 String dbHost = hostName.getText().toString();
					 if(dbPassword.length()==0){
						 executeCmd = "/opt/lampp/bin/mysqldump -h"+dbHost+" -u" + dbUser + " " +" --add-drop-database " + dbName + " -r" + backupLocation;
					 }else{
						 executeCmd = "/opt/lampp/bin/mysqldump -h"+dbHost+" -u" + dbUser + " -p"+ dbPassword +" --add-drop-database " + dbName + " -r" + backupLocation;
					 }
			       
			        Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
			        int processComplete = runtimeProcess.waitFor();

			      
			        if (processComplete == 0) {
			           
			            JOptionPane.showMessageDialog(frmDbtool, "Backup Complete!");
			            
			            
			        } else {
			        	JOptionPane.showMessageDialog(frmDbtool, "Backup Failed!");
			        }

			    } catch (IOException | InterruptedException ex) {
			        System.out.println( "Error at Backuprestore" + ex.getMessage());
			    }
				}
			}
			
		});
		
		btnNewButton.setBounds(22, 191, 238, 25);
		frmDbtool.getContentPane().add(btnNewButton);
		
		JLabel lblHost = new JLabel("Host:");
		lblHost.setBounds(22, 12, 124, 25);
		frmDbtool.getContentPane().add(lblHost);
		
		hostName = new JTextField();
		hostName.setBounds(22, 39, 114, 19);
		frmDbtool.getContentPane().add(hostName);
		hostName.setColumns(10);
		
		JLabel lblUser = new JLabel("User:");
		lblUser.setBounds(173, 12, 124, 25);
		frmDbtool.getContentPane().add(lblUser);
		
		userName = new JTextField();
		userName.setColumns(10);
		userName.setBounds(170, 39, 114, 19);
		frmDbtool.getContentPane().add(userName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(314, 12, 124, 25);
		frmDbtool.getContentPane().add(lblPassword);
		
		password = new JTextField();
		password.setColumns(10);
		password.setBounds(314, 39, 114, 19);
		frmDbtool.getContentPane().add(password);
		
		JLabel lblDatabaseName = new JLabel("Database Name:");
		lblDatabaseName.setBounds(22, 70, 124, 25);
		frmDbtool.getContentPane().add(lblDatabaseName);
		
		databaseName = new JTextField();
		databaseName.setColumns(10);
		databaseName.setBounds(22, 97, 148, 19);
		frmDbtool.getContentPane().add(databaseName);
		
		JCheckBox defaultCB = new JCheckBox("Default Data");
		defaultCB.setBounds(236, 90, 124, 33);
		frmDbtool.getContentPane().add(defaultCB);
		defaultCB.addItemListener(new ItemListener() {
		    @Override
		    public void itemStateChanged(ItemEvent e) {
		        if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
		           hostName.setText("localhost");
		           userName.setText("root");
		        } else {//checkbox has been deselected
		            hostName.setText("");
		            userName.setText("");
		        };
		    }
		});
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
