/*
 * 		created by Joseph Kinahan
		02/07/16

		email :   kinahanj25@gmail.com  	phone: 0873637329
*/
package phase4;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class BillCalculatorpart4 extends JFrame implements ActionListener {

	private ArrayList<String> billItems = new ArrayList<String>();
	private JLabel tag1, tag2, tag3, tag4, tag5, tag6;

	private String[] sips = {}, apps = {}, mains = {}, dess = {};
	//private String DB_URL = "jdbc:mysql://localhost/restaurant1?autoReconnect=true&useSSL=false";
	private String DB_URL = "jdbc:mysql://localhost:3307/restaurant";
	private static String USER = "root";
	private static String PASS = "usbw";

	@SuppressWarnings("rawtypes")
	private JComboBox tNum, bevvies, tastes, bigs, afters;
	private JButton calculateBillJButton, saveTableJButton, payBillJButton;
	private JTextField subTot, tax, Total, wName;
	int tNumCount = 0;
	private double subT, tx,subtotal, total;
	public static final double TAX_RATE = 0.05;

	Connection myConnection;

	Statement myStatement = null;

	//PreparedStatement pstate = null;

	ResultSet myResultSet = null;

	public BillCalculatorpart4(String JDBC_DRIVER, String DB_URL) throws SQLException {

		Connection myConnection = null;
		Statement myStatement = null;
		//PreparedStatement pstate = null;
		ResultSet myResultSet;

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
		this.dessertJComboBoxItemStateChanged();
		this.beverageJComboBoxItemStateChanged();
		this.appetizerJComboBoxItemStateChanged();
		this.mainCourseJComboBoxItemStateChanged();
		this.calculateBillJbuttonActionPerformed();
		this.saveTableJButtonActionPerformed();
		this.payBillJButtonActionPerformed();
		this.setLayout(null);
		this.setVisible(true);
		this.setSize(400, 650);
		this.setResizable(false);
		this.loadTableNumbers();
		this.tableNumberJComboBoxItemStateChanged();
	}
//below I created the reset method which sets selected components to disabled
	public void resetFrame() {
		billItems = new ArrayList<String>();
		bevvies.setEnabled(false);
		tastes.setEnabled(false);
		afters.setEnabled(false);
		bigs.setEnabled(false);
		wName.setText("");
		saveTableJButton.setEnabled(false);
		calculateBillJButton.setEnabled(false);
		payBillJButton.setEnabled(false);
		subTot.setText("");
		tax.setText("");
		Total.setText("");
	}
	// iv made this method engage to reenable the panels' components after paybill has been pressed
	//it will be called on the tablenumberjcomboboxStateChanged method
	public void engage(){
		bevvies.setEnabled(true);
		tastes.setEnabled(true);
		afters.setEnabled(true);
		bigs.setEnabled(true);
		saveTableJButton.setEnabled(true);
		calculateBillJButton.setEnabled(true);
		payBillJButton.setEnabled(true);
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
		tNum = new JComboBox();
		wName = new JTextField("               ");

		tag1.setBounds(55, 30, 100, 25);
		waiterPanelHolder.add(tag1);
		tNum.setBounds(225, 30, 70, 25);
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

		try {
			loadCategory("Beverage", bevvies);
			loadCategory("Appetizer", tastes);
			loadCategory("Main Course", bigs);
			loadCategory("Dessert", afters);
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
	// below i repeat the process of loading from the database directly into a
	// jcombo when i call the following method
	@SuppressWarnings("unchecked")
	public void loadTableNumbers() throws SQLException {
		String sql = "SELECT tableNumber FROM restauranttables";
		myConnection = DriverManager.getConnection(DB_URL, USER, PASS);
		myStatement = myConnection.createStatement();
		myResultSet = myStatement.executeQuery(sql);

		while (myResultSet.next()) {

			int tNumber = myResultSet.getInt("tableNumber");
			tNum.addItem(tNumber);
			tNumCount++;
		}
		myResultSet.close();
		myConnection.close();

	}
	public JPanel createCalculateBillJPanel() {
		JPanel billPanelHolder = new JPanel(null);
		billPanelHolder.setLayout(null);
		billPanelHolder.setBounds(40, 440, 320, 150);
		saveTableJButton = new JButton("Save Table");
		saveTableJButton.setBounds(200, 35, 115, 30);
		;
		calculateBillJButton = new JButton("Calculate Bill");
		calculateBillJButton.setBounds(200, 70, 115, 30);
		payBillJButton = new JButton("Pay Bill");
		payBillJButton.setBounds(200, 105, 115, 30);
		billPanelHolder.add(saveTableJButton);
		billPanelHolder.add(calculateBillJButton);
		billPanelHolder.add(payBillJButton);
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
		subTot.setBounds(80, 50, 100, 25);
		tax = new JTextField();
		tax.setBounds(80, 80, 100, 25);
		Total = new JTextField();
		Total.setBounds(80, 110, 100, 25);
		billPanelHolder.add(subTot);
		billPanelHolder.add(tax);
		billPanelHolder.add(Total);
		return billPanelHolder;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void loadCategory(String category, JComboBox jcb) throws SQLException {

		String sql = "SELECT * FROM menu where category = '" + category + "'";
		myConnection = DriverManager.getConnection(DB_URL, USER, PASS);
		myStatement = myConnection.createStatement();
		myResultSet = myStatement.executeQuery(sql);

		while (myResultSet.next()) {

			String name1 = myResultSet.getString("name");

			jcb.addItem(name1);
		}
	}	
	public void tableNumberJComboBoxItemStateChanged() throws SQLException {
		tNum.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					String number = event.getItem().toString();
					String sql = "SELECT * FROM restauranttables WHERE tableNumber = " + number + "";
					try {
						myConnection = DriverManager.getConnection(DB_URL, USER, PASS);
						myStatement = myConnection.createStatement();
						myResultSet = myStatement.executeQuery(sql);
						if(myResultSet.next()==true)
							wName.setText(myResultSet.getString("waiterName"));
							subtotal=myResultSet.getDouble("subtotal");
							displayTotal(subtotal);
							engage();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
				}
			}
		});
	};
	public void displayTotal(double total){
		DecimalFormat dollars = new DecimalFormat("$0.00");
		subTot.setText(dollars.format(subtotal));
		double tx = Math.round(((total*TAX_RATE)*100)/100);//last minute attempt in vain to roundoff this output
		//double tx2= Math.round(tx*100/100);
		tax.setText(dollars.format(tx));
		Total.setText(dollars.format(total+tx));
	}
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
	public void saveTableJButtonActionPerformed() {
		saveTableJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Saving Table Info...");
				subtotal = subT;
				try {
					updateTable();//calling my method which updates the database
					resetFrame();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	public void payBillJButtonActionPerformed(){
		payBillJButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				subtotal = 0;
				try {
					updateTable();
					resetFrame();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	public void updateTable() throws SQLException{
		try{
		myConnection = DriverManager.getConnection(DB_URL, USER, PASS);
		myStatement = myConnection.createStatement();
		myStatement.executeUpdate("UPDATE restauranttables SET subTotal= "+subtotal +" WHERE tableNumber = "
				+(int)tNum.getSelectedItem());// update query to DB linking through tableNumber
		
		} catch(SQLException exception){
			exception.printStackTrace();
		}
	}
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
	private double calculateSubTotal() throws SQLException {
		for (int i = 0; i < billItems.size(); i++) {
			myResultSet = myStatement.executeQuery("SELECT price FROM Menu WHERE name = '" + billItems.get(i)+" ' ");
			while (myResultSet.next()) {
				subT += myResultSet.getDouble("price");
			}
		}
		subTot.setText("$"+subT);//useful $ addition as concatenation has effect of casting double to string
		tx = subT * TAX_RATE;
		tax.setText("$"+tx);
		total = subT + tx;
		Total.setText("$"+total);
		billItems = new ArrayList<String>();
		return subT;
	}
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
	public static void main(String[] args) throws SQLException {
		
		BillCalculatorpart4 billC = new BillCalculatorpart4("com.mysql.jdbc.Driver",
				"jdbc:mysql://localhost/restaurant?autoReconnect=true&useSSL=false");
		billC.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        billC.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
            	billC.frameWindowClosing(we);
            	System.exit(0);
            };
        });
		
       
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
