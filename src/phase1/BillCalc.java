package phase1;

/*
 * created by J Kinahan
14/05/16
*/   // no need to keep this, only held it temporarily in case i wanted to refer to it

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class BillCalc extends JFrame {
	
	public BillCalc(String titleTag) {
		//setting jframe and jpanels to null to practice with absolute positioning, as per project brief
		setLayout(null);
		setTitle(titleTag);
		JPanel h = new JPanel();
		h.setLayout(null);
		h.setBounds(40, 0, 320, 70);
		//h.setBackground(Color.GREEN);//gave colours to jpanels to help visuals during build
		JLabel nametag= new JLabel("Restaurant");
		nametag.setFont(new Font("Serif", Font.BOLD, 22));
		h.add(nametag); nametag.setBounds(100, 0, 150, 40);
		add(h);
		
		JPanel waiterJpanel = new JPanel(); waiterJpanel.setLayout(null);
		waiterJpanel.setBounds(40, 85, 320, 120); //waiterJpanel.setBackground(Color.gray);
		JLabel tag1= new JLabel("Table Number: ");
		JLabel tag2= new JLabel("Waiter Name: ");
		JTextField tNum= new JTextField("              ");
		JTextField wName= new JTextField("               ");
		waiterJpanel.add(tag1); tag1.setBounds(55, 30, 100, 25);
		waiterJpanel.add(tNum); tNum.setBounds(195, 30, 100, 25);
		waiterJpanel.add(tag2); tag2.setBounds(55, 80, 100, 25);
		waiterJpanel.add(wName); wName.setBounds(195, 80, 100, 25);
		waiterJpanel.setBorder(BorderFactory.createTitledBorder("Waiter Information"));
		add(waiterJpanel); //made sure I added all components to panel before adding to JFrame
		
		JPanel menuItemsJpanel = new JPanel();
		menuItemsJpanel.setBounds(40, 240, 320, 180);
		//menuItemsJpanel.setBackground(Color.blue);
		menuItemsJpanel.setLayout(null);
		
		//creating the labels and comboboxes w/arrays before setting their bounds/ position then
		//  adding them to my panel
		JLabel tag3 = new JLabel("Beverage: "), tag4 = new JLabel("Appetizer:");
		JLabel tag5 = new JLabel("Main Course: "), tag6 = new JLabel("Dessert: ");
		String[] sips = {"Wine","Water","Beer","Tonic","Cola","Juice"};
		String[] apps = {"Crackers","Rolls","Bread","Salad","Yoghurt"};
		String[] mains = {"Pasta","Beef","Vegetarian","Chicken","Chilli"};
		String[] dess = {"Pavlova","Ice-cream","Cake","Scone","Apple Pie"};
		JComboBox bevvies = new JComboBox(sips), tastes = new JComboBox(apps);
		JComboBox bigs = new JComboBox(mains), afters = new JComboBox(dess);
				
		tag3.setBounds(20, 20, 120, 25);
		menuItemsJpanel.add(tag3);
		bevvies.setBounds(140, 20, 135, 25);
		menuItemsJpanel.add(bevvies); 
		tag4.setBounds(20, 55, 120, 25);
		menuItemsJpanel.add(tag4);
		tastes.setBounds(140, 55, 135, 25);
		menuItemsJpanel.add(tastes);
		tag5.setBounds(20, 90, 120, 25); menuItemsJpanel.add(tag5);
		bigs.setBounds(140, 90, 135, 25);menuItemsJpanel.add(bigs);
		tag6.setBounds(20, 125, 120, 25);menuItemsJpanel.add(tag6);
		afters.setBounds(140, 125, 135, 25); menuItemsJpanel.add(afters);
		menuItemsJpanel.setBorder(BorderFactory.createTitledBorder("Menu Items"));
		add(menuItemsJpanel);
		
		JPanel calcJPanel = new JPanel();
		calcJPanel.setLayout(null);
		calcJPanel.setBounds(40, 440, 320, 150);
		//calcJPanel.setBackground(Color.lightGray);
		JButton calculateBillJButton = new JButton("Calculate Bill");
		calculateBillJButton.setBounds(95, 0, 115, 30);
		calcJPanel.add(calculateBillJButton);
		
		
		JLabel tagSubT = new JLabel("Subtotal: "); tagSubT.setBounds(20, 50, 80, 25);
		JLabel tagTax = new JLabel("TAX: "); tagTax.setBounds(20, 80, 80, 25);
		JLabel tagTot= new JLabel("Total: "); tagTot.setBounds(20, 110, 80, 25);
		calcJPanel.add(tagSubT); calcJPanel.add(tagTax); calcJPanel.add(tagTot);
		JTextField subTot = new JTextField(); subTot.setBounds(120, 50, 110, 25);
		JTextField tax = new JTextField(); tax.setBounds(120, 80, 110, 25);
		JTextField total = new JTextField(); total.setBounds(120, 110, 110, 25);
		calcJPanel.add(subTot);calcJPanel.add(tax);calcJPanel.add(total);
		
		
		
		add(calcJPanel);
	}

}
