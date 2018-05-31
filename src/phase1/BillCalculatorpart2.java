package phase1;

import java.awt.Font;

import java.awt.event.ItemEvent;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Statement;

import java.util.ArrayList;

import java.util.List;



import javax.swing.BorderFactory;

import javax.swing.JButton;

import javax.swing.JComboBox;

import javax.swing.JFrame;

import javax.swing.JLabel;

import javax.swing.JPanel;

import javax.swing.JTextField;


public class BillCalculatorpart2 extends JFrame{


JLabel tag3, tag4, tag5, tag6;

String[] sips = {} , apps  = {}, mains = {}, dess = {};

JComboBox bevvies, tastes, bigs, afters;


private ArrayList billItems = new ArrayList();


Connection myConnection=null;

Statement myStatement = null;

PreparedStatement pstate = null;

ResultSet myResultSet;



    public BillCalculatorpart2(String JDBC_DRIVER, String DB_URL) throws SQLException{ // construct the JFrame with size,visibility,and null layout, and

    //calling in and adding each of my jpanel builder methods

      String USER = "root";

      String PASS = "root";

     

     

    

// below is the section where i make a connection with the database on my localhost and initiliase the statement

// and the resultset to begin to interact with the DB     

    	try{

    	   

        Class.forName(JDBC_DRIVER);



        System.out.println("Connecting to database...");

        try {

myConnection= DriverManager.getConnection(DB_URL,USER,PASS);

myStatement = myConnection.createStatement();


} catch (SQLException e) {

e.printStackTrace();

}



    	}catch(ClassNotFoundException se){

        //Handle errors for JDBC

        se.printStackTrace();

    	}

     

    

        setTitle("Restaurant Bill Calculator");

        this.add(createLabelJPanel());

        this.add(createWaiterJPanel());

        this.add(createMenuItemsJPanel());

        this.add(createCalculateBillJPanel());

        this.setLayout(null);

        this.setVisible(true);

        this.setSize(400, 650);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setResizable(false);

    

    

    

    }

    //for each panel, i created a method which builds and returns the panel with relevant 

    //components added

    public static JPanel createLabelJPanel(){

        JPanel labelPanelHolder = new JPanel(null);    //here i pass in null layout to 

        //fit with the JPanel constructor that has layout manager as its only arg

        labelPanelHolder.setBounds(40, 0, 320, 50);

        JLabel labelR= new JLabel("Restaurant");

        labelR.setFont(new Font("Serif", Font.BOLD, 22));

        labelR.setBounds(100, 0, 150, 40);

        labelPanelHolder.add(labelR);

        return labelPanelHolder;

    }



    public static JPanel createWaiterJPanel(){

        JPanel waiterPanelHolder = new JPanel(null);

        waiterPanelHolder.setBounds(40, 85, 320, 120);

        JLabel tag1= new JLabel("Table Number: ");

        JLabel tag2= new JLabel("Waiter Name: ");

        JTextField tNum= new JTextField("              ");

        JTextField wName= new JTextField("               ");

         

        tag1.setBounds(55, 30, 100, 25);

        waiterPanelHolder.add(tag1);

        tNum.setBounds(195, 30, 100, 25);

        waiterPanelHolder.add(tNum); 

        tag2.setBounds(55, 80, 100, 25);

        waiterPanelHolder.add(tag2); 

        wName.setBounds(195, 80, 100, 25);

        waiterPanelHolder.add(wName);

        waiterPanelHolder.setBorder(BorderFactory.createTitledBorder("Waiter Information"));

      //when the panel is complete, i return it as the focus of the method

        return waiterPanelHolder;

    }



    public JPanel createMenuItemsJPanel() throws SQLException{

        JPanel menuPanelHolder = new JPanel(null);

        menuPanelHolder.setBounds(40, 240, 320, 180);

        tag3 = new JLabel("Beverage: ");

        tag4 = new JLabel("Appetizer:");

        tag5 = new JLabel("Main Course: ");

        tag6 = new JLabel("Dessert: ");
        

        
        loadCategory("beverages", bevvies);


        tastes = new JComboBox(apps);

        bigs = new JComboBox(mains);

        afters = new JComboBox(dess);

        //create the labels, jcomboboxes w/ arrays, and set their bounds before adding them

        // to the panel

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

    

    

    

        

  /*  bevvies.addItemListener(new ItemListener() {

    @Override

    public void ItemStateChanged(ItemEvent event){

    if(event.getStateChange() == ItemEvent.SELECTED){

    ArrayList billItems += event.getItem().toString();

    }

    

    }*/

    }

    

    public void loadCategory(String category, JComboBox bevvies)throws SQLException {

    String sql = "SELECT * FROM menu where category = '" + category + "'";

    myResultSet = myStatement.executeQuery(sql);

    ArrayList<String> list = new ArrayList<String>();

while(myResultSet.next()){

String name = myResultSet.getString("name");

list.add(name);

}

sips = list.toArray(sips);

bevvies = new JComboBox(sips);


  }

    

    

    public static JPanel createCalculateBillJPanel(){

        JPanel billPanelHolder = new JPanel(null);

        billPanelHolder.setLayout(null);

        billPanelHolder.setBounds(40, 440, 320, 150);

        JButton calculateBillJButton = new JButton("Calculate Bill");

        calculateBillJButton.setBounds(95, 0, 115, 30);

        billPanelHolder.add(calculateBillJButton);

        JLabel tagSubT = new JLabel("Subtotal: ");

        tagSubT.setBounds(20, 50, 80, 25);

        JLabel tagTax = new JLabel("TAX: ");

        tagTax.setBounds(20, 80, 80, 25);

        JLabel tagTot= new JLabel("Total: ");

        tagTot.setBounds(20, 110, 80, 25);

        billPanelHolder.add(tagSubT);

        billPanelHolder.add(tagTax);

        billPanelHolder.add(tagTot);

        JTextField subTot = new JTextField(); subTot.setBounds(120, 50, 110, 25);

        JTextField tax = new JTextField(); tax.setBounds(120, 80, 110, 25);

        JTextField total = new JTextField(); total.setBounds(120, 110, 110, 25);

        billPanelHolder.add(subTot);

        billPanelHolder.add(tax);

        billPanelHolder.add(total);

        return billPanelHolder;

        

      /*  calculateBillJButton.addActionListener(new ActionListener(){

@Override

public void actionPerformed(ActionEvent e){

calculateTotal();

 

        

  });

        */ 

    }

    public static void main(String[] args){

        try {
			BillCalculatorpart2 billC = new BillCalculatorpart2("com.mysql.jdbc.Driver","jdbc:mysql://localhost/restaurant1?autoReconnect=true&useSSL=false");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

}