/*
 * created by Joseph Kinahan ....
 * 
 * 	email -	kinahanj25@gmail.com   phone: 087 3637329
05/06/16
*/
package phase2;

import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class BillCalculatorpart2 extends JFrame implements ActionListener {

	private ArrayList<String> billItems = new ArrayList<String>();
	private JLabel tag1, tag2, tag3, tag4, tag5, tag6;

	private String[] sips = {}, apps = {}, mains = {}, dess = {};
	private String DB_URL = "jdbc:mysql://localhost/restaurant1?autoReconnect=true&useSSL=false";
	private String USER = "root";
	private String PASS = "root";

	@SuppressWarnings("rawtypes")
	private JComboBox bevvies, tastes, bigs, afters;
	private JButton calculateBillJButton;
	private JTextField tNum, wName, subTot, tax, Total;
	private double subT, tx, total;
	public static final double TAX_RATE = 0.05;

	Connection myConnection;
	Statement myStatement = null;
	PreparedStatement pstate = null;
	ResultSet myResultSet = null;

	public BillCalculatorpart2(String JDBC_DRIVER, String DB_URL) {

		Connection myConnection = null;
		Statement myStatement = null;
		PreparedStatement pstate = null;
		ResultSet myResultSet;

		// below is the section where i make a connection with the database on
		// my localhost and initilise the statement
		// and the resultset to begin to interact with the DB
		try {

			Class.forName(JDBC_DRIVER);

			System.out.println("Connecting to database...");
			try {
				myConnection = DriverManager.getConnection(DB_URL, USER, PASS);
				myStatement = myConnection.createStatement();
				myResultSet = myStatement.executeQuery("SELECT * FROM menu");

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} catch (ClassNotFoundException se) {
			se.printStackTrace();
		}

		setTitle("Restaurant Bill Calculator");
		this.add(createLabelJPanel());
		this.add(createWaiterJPanel());
		this.add(createMenuItemsJPanel());
		this.add(createCalculateBillJPanel());
		// below I call my methods that will implement the action listeners I
		// have set up for the Jcombos and
		// for the cclculateBill button.....
		this.dessertJComboBoxItemStateChanged();
		this.beverageJComboBoxItemStateChanged();
		this.appetizerJComboBoxItemStateChanged();
		this.mainCourseJComboBoxItemStateChanged();
		this.calculateBillJbuttonActionPerformed();
		this.setLayout(null);
		this.setVisible(true);
		this.setSize(400, 650);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); turned off to use the custom close method
		this.setResizable(false);
		
		//this.frameWindowClosing(this);

	}
	
	

	public JPanel createLabelJPanel() {
		JPanel labelPanelHolder = new JPanel(null);
		labelPanelHolder.setBounds(40, 0, 320, 50);
		JLabel labelR = new JLabel("Restaurant");
		labelR.setFont(new Font("Serif", Font.BOLD, 22));
		labelR.setBounds(100, 0, 150, 40);
		labelPanelHolder.add(labelR);
		return labelPanelHolder;
	}

	public JPanel createWaiterJPanel() {
		JPanel waiterPanelHolder = new JPanel(null);
		waiterPanelHolder.setBounds(40, 85, 320, 120);
		tag1 = new JLabel("Table Number: ");
		tag2 = new JLabel("Waiter Name: ");
		tNum = new JTextField("              ");
		wName = new JTextField("               ");

		tag1.setBounds(55, 30, 100, 25);
		waiterPanelHolder.add(tag1);
		tNum.setBounds(195, 30, 100, 25);
		waiterPanelHolder.add(tNum);
		tag2.setBounds(55, 80, 100, 25);
		waiterPanelHolder.add(tag2);
		wName.setBounds(195, 80, 100, 25);
		waiterPanelHolder.add(wName);
		waiterPanelHolder.setBorder(BorderFactory.createTitledBorder("Waiter Information"));
		return waiterPanelHolder;
	}

	public JPanel createMenuItemsJPanel() {
		JPanel menuPanelHolder = new JPanel(null);
		menuPanelHolder.setBounds(40, 240, 320, 180);
		tag3 = new JLabel("Beverage: ");
		tag4 = new JLabel("Appetizer:");
		tag5 = new JLabel("Main Course: ");
		tag6 = new JLabel("Dessert: ");
		bevvies = new JComboBox(sips);
		tastes = new JComboBox(apps);
		bigs = new JComboBox(mains);
		afters = new JComboBox(dess);
		// here i use my loadCategory method(coded below the next JPanel) to
		// populate the 4 jcombos directly from
		// the database specifying the category from the DB table and the JCombo
		// to populate as parameters....
		try {
			loadCategory("Beverage", bevvies);
			loadCategory("Appetizer", tastes);
			loadCategory("Main Course", bigs);
			loadCategory("Dessert", afters);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		bevvies.addActionListener(this);
		tag3.setBounds(20, 20, 120, 25);
		menuPanelHolder.add(tag3);
		bevvies.setBounds(140, 20, 135, 25);
		menuPanelHolder.add(bevvies);
		tag4.setBounds(20, 55, 120, 25);
		menuPanelHolder.add(tag4);
		tastes.setBounds(140, 55, 135, 25);
		menuPanelHolder.add(tastes);
		tag5.setBounds(20, 90, 120, 25);
		menuPanelHolder.add(tag5);
		bigs.setBounds(140, 90, 135, 25);
		menuPanelHolder.add(bigs);
		tag6.setBounds(20, 125, 120, 25);
		menuPanelHolder.add(tag6);
		afters.setBounds(140, 125, 135, 25);
		menuPanelHolder.add(afters);
		menuPanelHolder.setBorder(BorderFactory.createTitledBorder("Menu Items"));
		return menuPanelHolder;

	}

	public JPanel createCalculateBillJPanel() {
		JPanel billPanelHolder = new JPanel(null);
		billPanelHolder.setLayout(null);
		billPanelHolder.setBounds(40, 440, 320, 150);
		calculateBillJButton = new JButton("Calculate Bill");
		calculateBillJButton.setBounds(95, 0, 115, 30);
		billPanelHolder.add(calculateBillJButton);
		JLabel tagSubT = new JLabel("Subtotal: ");
		tagSubT.setBounds(20, 50, 80, 25);
		JLabel tagTax = new JLabel("TAX: ");
		tagTax.setBounds(20, 80, 80, 25);
		JLabel tagTot = new JLabel("Total: ");
		tagTot.setBounds(20, 110, 80, 25);
		billPanelHolder.add(tagSubT);
		billPanelHolder.add(tagTax);
		billPanelHolder.add(tagTot);
		subTot = new JTextField();
		subTot.setBounds(120, 50, 110, 25);
		tax = new JTextField();
		tax.setBounds(120, 80, 110, 25);
		Total = new JTextField();
		Total.setBounds(120, 110, 110, 25);
		billPanelHolder.add(subTot);
		billPanelHolder.add(tax);
		billPanelHolder.add(Total);
		return billPanelHolder;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void loadCategory(String category, JComboBox jcb) throws SQLException {
		// this method takes a string and a jcombo as its parameters and it
		// connects to the database and populates the jcombo
		// with the passed category taken from the database....
		String sql = "SELECT * FROM menu where category = '" + category + "'";
		myConnection = DriverManager.getConnection(DB_URL, USER, PASS);
		myStatement = myConnection.createStatement();
		myResultSet = myStatement.executeQuery(sql);

		while (myResultSet.next()) {
			
			String name1 = myResultSet.getString("name");
			jcb.addItem(name1);
		}

		myResultSet.close();

	}

	// below, I've coded 4 methods that add action listeners to my 4 jcomboboxes, and take the chosen
	// selection and add it to my billItems global ArrayList variable.....
	public void beverageJComboBoxItemStateChanged() {
		bevvies.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					String drink = event.getItem().toString();
					billItems.add(drink);
				}
			}
		});
	}

	public void appetizerJComboBoxItemStateChanged() {
		tastes.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					String entree = event.getItem().toString();
					billItems.add(entree);
				}
			}
		});
	}

	public void mainCourseJComboBoxItemStateChanged() {
		bigs.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					String dinner = event.getItem().toString();
					billItems.add(dinner);
				}
			}
		});
	}

	public void dessertJComboBoxItemStateChanged() {
		afters.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					String sweet = event.getItem().toString();
					billItems.add(sweet);
				}
			}
		});
	}

	// the action listener on the below button listens for/creates the event that calls the
	// calculateSubTotal method
	public void calculateBillJbuttonActionPerformed() {
		calculateBillJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					calculateSubTotal();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
//my calculate method loops through the billItems arraylist and retrieves the price for each through a mysql query
//and adds them to the subTotal variable	
	private double calculateSubTotal() throws SQLException {
		for (int i = 0; i < billItems.size(); i++) {
			myResultSet = myStatement.executeQuery("SELECT price FROM Menu WHERE name = '" + billItems.get(i) + "'");
			while (myResultSet.next()) {
				subT += myResultSet.getDouble("price");
			}
		}
		subTot.setText("$" +subT);// an expedient  way of converting to string
										// to display a double in textfield
		tx = subT * TAX_RATE;
		tax.setText("$"+tx);
		total = subT + tx;
		Total.setText("$"+total);
		return subT;
	}
//below i coded the window closing method to close the DB connections independent of the default close, 
//i used the main to set the JFrame to do nothing on close but to listen for the window event which calls
// my method to close connections	
	public void frameWindowClosing(WindowEvent event){
		try{
			myStatement.close();
			myConnection.close();
		}
		catch(SQLException sqlException){
			sqlException.printStackTrace();
		}
		finally{
			System.out.println("Database connection closed.");
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		
		BillCalculatorpart2 billC = new BillCalculatorpart2("com.mysql.jdbc.Driver",
				"jdbc:mysql://localhost/restaurant1?autoReconnect=true&useSSL=false");
		billC.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        billC.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
            	billC.frameWindowClosing(we);
            	System.exit(0);
            }
        } );
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
