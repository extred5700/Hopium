package Main.boundary.StaffUI;

import Main.boundary.StaffLoginPage;
import Main.controller.RestaurantStaff.StaffDeleteController;
import Main.controller.RestaurantStaff.StaffEditController;
import Main.controller.RestaurantStaff.StaffSearchController;
import Main.controller.RestaurantStaff.StaffViewController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class RestaurantStaffPageUI extends JFrame {
    /* Variable declaration */
    private final JFrame staffUIFrame = new JFrame("Restaurant Staff Homepage");

    /* Buttons for top of the GUI*/
    private final JButton buttonLogout = new JButton("Logout");
    private final JButton buttonEdit = new JButton("Edit");
    private final JButton buttonSearch = new JButton("Search");
    private final JButton buttonView = new JButton("View");
    private final JButton buttonDelete = new JButton("Delete");

    /* 1. EDIT Function */
    private final JPanel panelEditOrder = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 25));
    private JTable tableEditOrder;
    // Labels
    private final JLabel labelEditOrderID = new JLabel("Order ID: ");
    private final JLabel labelEditFoodName = new JLabel("Food Name: ");
    private final JLabel labelEditQuantity = new JLabel("Quantity: ");
    // Text Fields
    private final JTextField fieldEditOrderID = new JTextField(20);
    private final JTextField fieldEditFoodName = new JTextField(20);
    private final JTextField fieldEditQuantity = new JTextField(20);
    // Buttons
    private final JButton buttonEditOrder = new JButton("Edit Order");
    private final JButton buttonEditFulfillment = new JButton("Fulfill Order");

    /* 2. SEARCH function */
    private final JPanel panelSearchOrder = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 25));
    private JTable tableSearchOrder;
    // Label
    private final JLabel labelSearchTableNumber = new JLabel("Table Number: ");
    // Text Fields
    private final JTextField fieldSearchTableNumber = new JTextField(20);
    // Buttons
    private final JButton buttonSearchOrder = new JButton("Search Order by Table Number");

    /* 3. VIEW function */
    private final JPanel panelViewOrder = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 25));
    private JTable tableViewOrder;

    /* 4. DELETE function */
    private final JPanel panelDeleteOrder = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 25));
    private JTable tableDeleteOrder;
    // Button
    private final JButton buttonDeleteOrder = new JButton("Delete Order");

    public RestaurantStaffPageUI(String setDisplayPage){
        staffUIFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        staffUIFrame.getContentPane().setLayout(new FlowLayout());
        staffUIFrame.setSize(520, 705);
        staffUIFrame.setResizable(false);
        staffUIFrame.setLocationRelativeTo(null); // Window will display in the middle of the screen
        staffUIFrame.getContentPane().setBackground(Color.WHITE);

        // Add buttons & functions for the top of the GUI
        displayStaffUserButton();

        /* Button Function for Restaurant Staff */
        // 1. EDIT function
        displayEditPanel();
        panelEditOrder.setVisible(false);

        // 2. SEARCH function
        displaySearchPanel();
        panelSearchOrder.setVisible(false);

        // 3. VIEW function
        displayViewPanel();
        panelViewOrder.setVisible(false);

        // 4. DELETE function
        displayDeletePanel();
        panelDeleteOrder.setVisible(false);

        switch (setDisplayPage){
            case "Default":
                break;
            case "Edit":
                panelEditOrder.setVisible(true);
                break;
            case "Search":
                panelSearchOrder.setVisible(true);
            case "View":
                panelViewOrder.setVisible(true);
                break;
            case "Delete":
                panelDeleteOrder.setVisible(true);
                break;
        }
        staffUIFrame.setVisible(true);
    }

    /* Universal GUI Functions
     * displayAdminUserButtons() - Method to display the buttons on top of the screen
     * ActionListener topButtonsListener - Button Listener for the buttons on top of the screen
     * displayTitledBorder(JPanel panel) - Construction and display of the Titled Border
     */
    public void displayStaffUserButton(){
        JButton [] myArray = {buttonLogout, buttonEdit, buttonSearch, buttonView, buttonDelete};
        for (JButton jButton : myArray) {
            jButton.setPreferredSize(new Dimension(95, 30));
            jButton.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 15));
            jButton.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
            jButton.setBackground(Color.WHITE);
            jButton.addActionListener(topButtonsListener);
            staffUIFrame.add(jButton);
        }
    }

    // Top buttons listener
    ActionListener topButtonsListener = e ->{
        JButton buttonPressed = (JButton)e.getSource();
        String action = buttonPressed.getText();
        switch (action) {
            case "Logout" -> {
                staffUIFrame.dispose();
                staffUIFrame.setVisible(false);
                new StaffLoginPage();
            }
            case "Edit" -> {
                staffUIFrame.dispose();
                new RestaurantStaffPageUI("Edit");
            }
            case "Search" -> {
                staffUIFrame.dispose();
                new RestaurantStaffPageUI("Search");
            }
            case "View" -> {
                staffUIFrame.dispose();
                new RestaurantStaffPageUI("View");
            }
            case "Delete" -> {
                staffUIFrame.dispose();
                new RestaurantStaffPageUI("Delete");
            }
        }
    };

    // Method to construct and display of the Titled Border
    public void displayTitledBorder(JPanel panel, String nameOfPanel){
        TitledBorder titledBorder = new TitledBorder(nameOfPanel);
        titledBorder.setBorder(new LineBorder(Color.BLACK));
        titledBorder.setTitleFont(new Font("Arial", Font.BOLD + Font.ITALIC, 15));
        panel.setBorder(titledBorder);
    }


    /* 1. CREATE function
    * 1a) void displayEditPanel() - Display JPanel for Restaurant Staff to edit orders
    * 1b) Component editTableConstruction() - Construction of the JTable, Mouse Click Listener to display all transactions (JTable type returned as a JScrollPane type)
    * 1c) boolean checkOrderFulfillment() - Check if Customer Order is fulfilled based on the Edit Table
    * 1d) void editPanelButton_OnClick(String nameOfButton) - ALL edit JPanel buttons
    */
    // 1a) Method to display JPanel for Restaurant Staff to edit orders
    public void displayEditPanel(){
        displayTitledBorder(panelEditOrder, "Edit/Fulfill Customer Order"); // Display titled border

        // Table Construction called in method, converted to a JScrollPane
        JScrollPane editOrderScrollPane = (JScrollPane) editTableConstruction();

        // Label
        labelEditOrderID.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 19));
        labelEditFoodName.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 19));
        labelEditQuantity.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 19));

        // Text Fields
        fieldEditOrderID.setPreferredSize(new Dimension(60, 30));
        fieldEditOrderID.setEditable(false);
        fieldEditFoodName.setPreferredSize(new Dimension(60, 30));
        fieldEditFoodName.setEditable(false);
        fieldEditQuantity.setPreferredSize(new Dimension(60, 30));

        // Buttons
        JButton [] editButtonInPanel = {buttonEditOrder, buttonEditFulfillment};
        for (JButton jButton : editButtonInPanel){
            jButton.setPreferredSize(new Dimension(250, 30));
            jButton.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 15));
            jButton.setBorder(BorderFactory.createLineBorder(Color.RED,1));
            jButton.setBackground(Color.WHITE);
            jButton.setEnabled(false);
        }
        buttonEditOrder.setActionCommand("Edit Order");
        buttonEditFulfillment.setActionCommand("Fulfill Order");

        // EDIT Button Click Listener
        buttonEditOrder.addActionListener(e -> editButton_OnClick(e.getActionCommand()));

        // Fulfill Button Click Listener
        buttonEditFulfillment.addActionListener(e -> editButton_OnClick(e.getActionCommand()));

        // Add components to the JPanel
        panelEditOrder.setPreferredSize(new Dimension(500, 550));
        panelEditOrder.setBackground(Color.WHITE);
        panelEditOrder.add(editOrderScrollPane);
        panelEditOrder.add(labelEditOrderID);
        panelEditOrder.add(fieldEditOrderID);
        panelEditOrder.add(labelEditFoodName);
        panelEditOrder.add(fieldEditFoodName);
        panelEditOrder.add(labelEditQuantity);
        panelEditOrder.add(fieldEditQuantity);
        panelEditOrder.add(buttonEditOrder);
        panelEditOrder.add(buttonEditFulfillment);
        staffUIFrame.add(panelEditOrder);
        panelEditOrder.setVisible(true);
    }

    // 1b) Method to construction of the JTable, Mouse Click Listener to display all transactions (JTable type returned as a JScrollPane type)
    public Component editTableConstruction(){
        StaffEditController staffEditController = new StaffEditController();
        String [][] data = staffEditController.displayOrders();
        // Display data in a table format
        String [] columnTableNames = {"Order ID", "Food Name", "Quantity", "Price", "Fulfilled"};
        tableEditOrder = new JTable(data, columnTableNames);
        JScrollPane editScrollPane = new JScrollPane(tableEditOrder);
        editScrollPane.setPreferredSize(new Dimension(485, 200)); // width then height

        // Table Mouse Click Listener
        tableEditOrder.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int getRow = tableEditOrder.getSelectedRow();
                // Display details on the text fields
                fieldEditOrderID.setText(tableEditOrder.getModel().getValueAt(getRow, 0).toString());
                fieldEditFoodName.setText(tableEditOrder.getModel().getValueAt(getRow, 1).toString());
                fieldEditQuantity.setText(tableEditOrder.getModel().getValueAt(getRow, 2).toString());
                buttonEditOrder.setEnabled(true);
                buttonEditFulfillment.setEnabled(true);
            }
        });

        // Reset Text Fields and Buttons
        JTextField [] editTextFields = {fieldEditOrderID, fieldEditFoodName, fieldEditQuantity};
        for (JTextField jTextField : editTextFields){
            jTextField.setText("");
        }
        JButton [] editButtons = {buttonEditOrder, buttonEditFulfillment};
        for (JButton jButton : editButtons){
            jButton.setEnabled(false);
        }

        //Get the components in the panel
        Component[] componentList = panelEditOrder.getComponents();
        //Loop through the components
        for(Component c : componentList){
            //Find the components you want to remove
            if(c instanceof JScrollPane){
                //Remove it
                panelEditOrder.remove(c);
            }
        }
        panelEditOrder.add(editScrollPane, 0);
        panelEditOrder.revalidate();
        panelEditOrder.repaint();

        return editScrollPane;
    }

    // 1c) Check if Customer Order is fulfilled based on the Edit Table
    public boolean checkOrderFulfillment(){
        boolean isCustomerOrderFulfilled = false;
        String getCurrentFulfillmentStatus = tableEditOrder.getModel().getValueAt(tableEditOrder.getSelectedRow(), 4).toString();
        if (getCurrentFulfillmentStatus.equals("Y")) {
            isCustomerOrderFulfilled = true;
        }
        return isCustomerOrderFulfilled;
    }

    // 1d) ALL edit JPanel buttons
    public void editButton_OnClick(String nameOfButton){
        StaffEditController staffEditController = new StaffEditController();
        switch (nameOfButton){
            case "Edit Order":
                // If Customer Order fulfillment status == "Y"
                if (checkOrderFulfillment()){
                    JOptionPane.showMessageDialog(null, "Customer Order has already been fulfilled.", "Error!", JOptionPane.WARNING_MESSAGE);
                }
                // If Customer Order fulfillment status == "N"
                else{
                    int orderIDSelected = Integer.parseInt(tableEditOrder.getModel().getValueAt(tableEditOrder.getSelectedRow(), 0).toString());
                    // Check if the data keyed in Quantity Text Field is a numeric data type
                    //if ((fieldEditQuantity.getText() != null) && (fieldEditQuantity.getText().matches("[1-9.]+"))){
                    if ((fieldEditQuantity.getText() != null) && (fieldEditQuantity.getText().matches("\\b([1-9]|[1-9][0-9]|100)\\b"))){ // Update: Max Quantity for a dish is 100 (Range is 1 - 100)
                        int oldQuantity = Integer.parseInt(tableEditOrder.getModel().getValueAt(tableEditOrder.getSelectedRow(), 2).toString());
                        // Check if quantity text field is edited to a new value
                        int newQuantity = Integer.parseInt(fieldEditQuantity.getText());
                        /* If the new quantity keyed into the text field is (MUST satisfy the following conditions):
                         * a) A numeric data type
                         * b) New quantity is a different value from the old quantity value
                         */
                        if ((newQuantity != oldQuantity)){
                            staffEditController = new StaffEditController();
                            if (staffEditController.editCustomerOrder(orderIDSelected, newQuantity)){
                                JOptionPane.showMessageDialog(null, "Customer Order has been successful updated.", "Customer Order Update", JOptionPane.INFORMATION_MESSAGE);
                            }
                            else{
                                JOptionPane.showMessageDialog(null, "Customer Order has not been successful updated.", "Customer Order Update", JOptionPane.ERROR_MESSAGE);
                            }
                            // Refresh Table, text fields and disable buttons
                            editTableConstruction();
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Quantity text field has not been edited.", "Error!", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Please enter a valid number or a number that is more than 0 in the quantity text field.", "Error!", JOptionPane.WARNING_MESSAGE);
                    }
                }
                break;
            case "Fulfill Order":
                int orderIDSelected = Integer.parseInt(tableEditOrder.getModel().getValueAt(tableEditOrder.getSelectedRow(), 0).toString());
                // If Customer Order fulfillment status == "Y"
                if (checkOrderFulfillment()){
                    JOptionPane.showMessageDialog(null, "Customer Order has already been fulfilled.", "Error!", JOptionPane.WARNING_MESSAGE);
                }
                else{
                    if (staffEditController.fulfillOrder(orderIDSelected)){
                        JOptionPane.showMessageDialog(null, "Customer order fulfillment is successful.", "Customer Order Fulfillment", JOptionPane.INFORMATION_MESSAGE);
                        editTableConstruction();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Customer order fulfilled has failed.", "Error!", JOptionPane.WARNING_MESSAGE);
                    }
                }
                break;
        }
    }


    /* 2. SEARCH function
     * 2a) void displaySearchPanel() - Display JPanel for Restaurant Staff to Search Orders based on Table Number
     * 2b) Component searchTableConstruction(String [][] data) - Construction of the JTable (JTable type returned as a JScrollPane type)
     * 2c) void searchButton_OnClick(String [][] data) - ALL edit JPanel buttons
     */
    // 2a) Display JPanel for Restaurant Staff to Search Orders based on Table Number
    public void displaySearchPanel(){
        displayTitledBorder(panelSearchOrder, "Search Customer Orders by Table Number"); // Display titled border

        // Table Construction called in method, converted to a JScrollPane
        String [][] orders = {{"", "", "", ""}};
        JScrollPane searchScrollPane1 = (JScrollPane) searchTableConstruction(orders);

        // Label
        labelSearchTableNumber.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 17));

        // Text Field
        fieldSearchTableNumber.setPreferredSize(new Dimension(60, 30));
        // Ensure user only can type in numbers/integers
        fieldSearchTableNumber.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
            }
        });

        // Button
        buttonSearchOrder.setPreferredSize(new Dimension(250, 30));
        buttonSearchOrder.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 15));
        buttonSearchOrder.setBorder(BorderFactory.createLineBorder(Color.RED,1));
        buttonSearchOrder.setBackground(Color.WHITE);
        // SEARCH Button Click Listener
        buttonSearchOrder.addActionListener(e -> searchButton_OnClick());

        // Add components to the JPanel
        panelSearchOrder.setPreferredSize(new Dimension(500, 390));
        panelSearchOrder.setBackground(Color.WHITE);
        panelSearchOrder.add(searchScrollPane1);
        panelSearchOrder.add(labelSearchTableNumber);
        panelSearchOrder.add(fieldSearchTableNumber);
        panelSearchOrder.add(buttonSearchOrder);
        staffUIFrame.add(panelSearchOrder);
        panelSearchOrder.setVisible(true);
    }

    // 2b) Method to construction of the JTable, display all Customer orders (JTable type returned as a JScrollPane type)
    public Component searchTableConstruction(String [][] data){
        // Display data in a table format
        String [] columnTableNames = {"Order ID", "Food Name", "Quantity", "Price"};
        // Table
        tableSearchOrder = new JTable(data, columnTableNames);
        JScrollPane searchScrollPane2 = new JScrollPane(tableSearchOrder);
        searchScrollPane2.setPreferredSize(new Dimension(470, 200)); // width then height

        //Get the components in the panel
        Component[] componentList = panelSearchOrder.getComponents();
        //Loop through the components
        for(Component c : componentList){
            //Find the components you want to remove
            if(c instanceof JScrollPane){
                //Remove it
                panelSearchOrder.remove(c);
            }
        }
        panelSearchOrder.add(searchScrollPane2, 0);
        panelSearchOrder.revalidate();
        panelSearchOrder.repaint();

        return searchScrollPane2;
    }

    // 2c) ALL edit JPanel buttons
    public void searchButton_OnClick(){
        if (fieldSearchTableNumber.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Please enter a valid Table Number", "Error!", JOptionPane.WARNING_MESSAGE);
        }
        else{
            // NO need to check whether the user keys in an integer (already done in displaySearchPanel(), text field initialization)
            int table_num = Integer.parseInt(fieldSearchTableNumber.getText());
            StaffSearchController staffSearchController = new StaffSearchController();
            String [][] data = staffSearchController.searchBy(table_num);
            // Refresh Table
            searchTableConstruction(data);
        }
    }


    /* 3. VIEW function
    * 3a) void displayViewPanel() - Display JPanel for Restaurant Staff to View All Orders
    * 3b) Component viewTableConstruction() - Construction of the JTable (JTable type returned as a JScrollPane type)
    * */
    // 3a) - Display JPanel for Restaurant Staff to Search Orders based on Table Number
    public void displayViewPanel(){
        displayTitledBorder(panelViewOrder, "View all Orders");

        // Table Construction called in method, converted to a JScrollPane
        JScrollPane viewScrollPane1 = (JScrollPane) viewTableConstruction();

        // Add components to the JPanel
        panelViewOrder.setPreferredSize(new Dimension(500, 550));
        panelViewOrder.setBackground(Color.WHITE);
        panelViewOrder.add(viewScrollPane1);
        staffUIFrame.add(panelViewOrder);
        panelViewOrder.setVisible(true);
    }

    // 3b) Construction of the JTable (JTable type returned as a JScrollPane type)
    public Component viewTableConstruction(){
        StaffViewController staffViewController = new StaffViewController();
        String [][] data = staffViewController.displayOrders();
        // Display data in a table format
        String [] columnTableNames = {"Order ID", "Food Name", "Quantity", "Price", "Fulfilled"};
        tableViewOrder = new JTable(data, columnTableNames);
        JScrollPane viewScrollPane = new JScrollPane(tableViewOrder);
        viewScrollPane.setPreferredSize(new Dimension(485, 470)); // width then height

        //Get the components in the panel
        Component[] componentList = panelViewOrder.getComponents();
        //Loop through the components
        for(Component c : componentList){
            //Find the components you want to remove
            if(c instanceof JScrollPane){
                //Remove it
                panelViewOrder.remove(c);
            }
        }
        panelViewOrder.add(viewScrollPane, 0);
        panelViewOrder.revalidate();
        panelViewOrder.repaint();

        return viewScrollPane;
    }


    /* 4. DELETE function
    * 4a) void displayDeletePanel() - Display JPanel for Restaurant Staff to Delete orders
    * 4b) Component deleteTableConstruction() - Construction of the JTable, Mouse Click Listener to select an order ID (JTable type returned as a JScrollPane type)
    * */
    // 4a) Display JPanel for Restaurant Staff to Delete orders
    public void displayDeletePanel(){
        displayTitledBorder(panelDeleteOrder, "Delete Orders"); // Display titled border

        // Table Construction called in method, converted to a JScrollPane
        JScrollPane deleteOrderScrollPane = (JScrollPane) deleteTableConstruction();

        // Button
        buttonDeleteOrder.setPreferredSize(new Dimension(250, 30));
        buttonDeleteOrder.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 15));
        buttonDeleteOrder.setBorder(BorderFactory.createLineBorder(Color.RED,1));
        buttonDeleteOrder.setBackground(Color.WHITE);
        // DELETE ORDER Button Click Listener
        buttonDeleteOrder.addActionListener(e -> deleteOrderButton_Onclick());

        // Add components to the JPanel
        panelDeleteOrder.setPreferredSize(new Dimension(500, 340));
        panelDeleteOrder.setBackground(Color.WHITE);
        panelDeleteOrder.add(deleteOrderScrollPane);
        panelDeleteOrder.add(buttonDeleteOrder);
        staffUIFrame.add(panelDeleteOrder);
        panelDeleteOrder.setVisible(true);
    }

    // 4b) Method to construction of the JTable, Mouse Click Listener to select an order ID (JTable type returned as a JScrollPane type)
    public Component deleteTableConstruction(){
        StaffDeleteController staffDeleteController = new StaffDeleteController();
        String [][] data = staffDeleteController.displayAllOrders();
        // Display data in a table format
        String [] columnTableNames = {"Order ID", "Food Name", "Quantity", "Price", "Fulfilled"};
        tableDeleteOrder = new JTable(data, columnTableNames);
        JScrollPane deleteScrollPane = new JScrollPane(tableDeleteOrder);
        deleteScrollPane.setPreferredSize(new Dimension(485, 200)); // width then height

        //Get the components in the panel
        Component[] componentList = panelDeleteOrder.getComponents();
        //Loop through the components
        for(Component c : componentList){
            //Find the components you want to remove
            if(c instanceof JScrollPane){
                //Remove it
                panelDeleteOrder.remove(c);
            }
        }
        panelDeleteOrder.add(deleteScrollPane, 0);
        panelDeleteOrder.revalidate();
        panelDeleteOrder.repaint();

        return deleteScrollPane;
    }

    // 4c) Delete button function to allow the user to delete an order by passing the selected Order ID into the controller
    public void deleteOrderButton_Onclick(){
        int getRow = tableDeleteOrder.getSelectedRow();
        if (getRow != -1){
            int selectedOrderID = Integer.parseInt((String)tableDeleteOrder.getModel().getValueAt(getRow, 0));
            StaffDeleteController staffDeleteController = new StaffDeleteController();
            // If deletion of Customer Order is successful
            if (staffDeleteController.deleteByOrderID(selectedOrderID)){
                JOptionPane.showMessageDialog(null, "Customer Order has been successfully deleted!", "Delete Order", JOptionPane.INFORMATION_MESSAGE);
                deleteTableConstruction(); // Refresh table
            }
            // If deletion of Customer Order is unsuccessful
            else{
                JOptionPane.showMessageDialog(null, "Customer Order deletion failed.", "Suspend User", JOptionPane.ERROR_MESSAGE);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Please select an order to delete from the table.", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

}
