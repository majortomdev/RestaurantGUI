/*
 * created by J Kinahan
14/05/16
*/
package phase1;
import javax.swing.*;
import java.awt.*;


public class BillCalculator2temp extends JFrame{

    public BillCalculator2temp(){ // construct the JFrame with size,visibility,and null layout, and
    	//calling in and adding each of my jpanel builder methods
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
        private JLabel restaurantJLabel= new JLabel("Restaurant");
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

    public static JPanel createMenuItemsJPanel(){
        JPanel menuPanelHolder = new JPanel(null);
        menuPanelHolder.setBounds(40, 240, 320, 180);
        JLabel tag3 = new JLabel("Beverage: "), tag4 = new JLabel("Appetizer:");
        JLabel tag5 = new JLabel("Main Course: "), tag6 = new JLabel("Dessert: ");
        String[] sips = {"Wine","Water","Beer","Tonic","Cola","Juice"};
        String[] apps = {"Crackers","Rolls","Bread","Salad","Yoghurt"};
        String[] mains = {"Pasta","Beef","Vegetarian","Chicken","Chilli"};
        String[] dess = {"Pavlova","Ice-cream","Cake","Scone","Apple Pie"};
        JComboBox bevvies = new JComboBox(sips), tastes = new JComboBox(apps);
        JComboBox bigs = new JComboBox(mains), afters = new JComboBox(dess);
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
    }


    public static void main(String[] args){
        BillCalculator2temp billC = new BillCalculator2temp();
    }

}



