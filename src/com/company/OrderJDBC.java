package com.company;




import java.sql.*;
import java.io.*;
import java.lang.Integer;
import java.lang.Double;


public class OrderJDBC
{
    private String url = "jdbc:mysql://localhost/Banca";
    private	String uid = "Pop";
    private	String pw = "password";
    private BufferedReader reader;
    private Connection con;


    public static void main(String[] args)
    {
        OrderJDBC app = new OrderJDBC();

        app.init();
        app.run();
    }

    private void init()
    {
        // Initialize your application
        // Register the MySQL driver and make a connection
        try {	// Load driver class
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (java.lang.ClassNotFoundException e) {
            System.err.println("ClassNotFoundException: " +e);
        }

        con = null;
        try {
            con = DriverManager.getConnection(url, uid, pw);
        }
        catch (SQLException ex) {
            System.err.println("SQLException: " + ex);
            System.exit(1);
        }

        // Set up console reader
        reader = new BufferedReader(new InputStreamReader(System.in));
    }
    private void run()
    {	// Continually read command from user in a loop and then do the requested query
        String choice, sqlSt;
        choice="1";
        while (!choice.equals("X"))
        {
            printMenu();
            choice = getLine();

            if (choice.equals("1"))				// List all customers
            {	sqlSt = "SELECT * FROM Utilizator;";
                doQuery(sqlSt);
            }
            else if (choice.equals("2"))		// List all orders for a customer
            {	String cid;
                System.out.print("Enter utilizator id: ");
                cid = getLine();
               // System.out.println("Enter password:");
                //cid =
                sqlSt = "SELECT * FROM Orders WHERE customerId = '"+cid+"';";
                doQuery(sqlSt);
            }
            else if (choice.equals("3"))		// List all lineitems for an order
            {	String onum;
                System.out.print("Enter order id: ");
                onum = getLine();
                sqlSt = "SELECT * FROM OrderedProduct WHERE orderId = '"+onum+"';";
                doQuery(sqlSt);
            }
            else if (choice.equals("A"))		// Add customer
            {	String cid, cname;
                System.out.print("Enter customer id: ");
                cid = getLine();
                System.out.print("Enter customer name: ");
                cname = getLine();
                cname = convertSQLString(cname);
                sqlSt = "INSERT INTO Customer (customerId, customerName) VALUES ('"+cid+"','"+cname+"');";
                doUpdate(sqlSt);
            }
            else if (choice.equals("F"))		// Fix order total
            {	String onum;
                System.out.print("Enter order id: ");
                onum = getLine();
                updateOrderTotal(onum);
            }

            else if (choice.equals("D"))		// Delete customer
            {	String cid;
                System.out.print("Enter customer id to delete: ");
                cid = getLine();
                sqlSt = "DELETE FROM Customer WHERE customerId = '"+cid+"';";
                doUpdate(sqlSt);
                sqlSt = "DELETE FROM OrderedProduct WHERE orderId IN (SELECT orderId FROM Orders WHERE customerId = '"+cid+"');";
                doUpdate(sqlSt);
                sqlSt = "DELETE FROM Orders WHERE customerId = '"+cid+"';";
                doUpdate(sqlSt);
            }
            else if (choice.equals("U"))		// Update customer
            {	// Retrive student record and prompt for each field to update
                updateCustomer();
            }
            else if (choice.equals("N"))		// Enter a new order
            {	enterOrder();
            }
            else if (choice.equals("X"))
                System.out.println("Exiting!");
            else
                System.out.println("Invalid input!");
            System.out.println("Press any key to continue...\n");
            try{
                reader.readLine();
            }catch(IOException e){
                System.out.println(e);
                System.exit(1);
            }
        }
    }

    private String getLine()
    {	 String inputLine = "";
        try{
            inputLine = reader.readLine();
        }catch(IOException e){
            System.out.println(e);
            System.exit(1);
        }//try
        return inputLine;
    }

    private String convertSQLString(String st)
    {	// Main function is to replace "'" in string with "''"
        return st.replaceAll("'","''");
    }

    private void printMenu()
    {	System.out.println("\n\nSelect one of these options: ");
        System.out.println("  1 - List all customers");
        System.out.println("  2 - List all orders for a customer");
        System.out.println("  3 - List all lineitems for an order");
        System.out.println("  F - Fix total for an order");
        System.out.println("  A - Add a customer");
        System.out.println("  D - Delete a customer");
        System.out.println("  U - Update a customer");
        System.out.println("  N - Enter new order");
        System.out.println("  X - Exit application");
        System.out.print("Your choice: ");
    }

    private void doUpdate(String updateStr)
    {
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(updateStr);
            System.out.println("Operation success!");
        }
        catch (SQLException e)
        {	System.out.println("Operation failed: "+e);
        }
    }


    private void doQuery(String queryStr)
    {
        try {
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery(queryStr);
            ResultSetMetaData rsmd = rst.getMetaData();

            // Calculate column sizes (cut off large columns to 35)
            int colCount = rsmd.getColumnCount();
            int rowCount = 0;
            int colWidth[] = new int[colCount];
            for (int i=1; i <= colCount; i++)
            {	colWidth[i-1] = rsmd.getColumnDisplaySize(i);
                if (colWidth[i-1] > 35)
                    colWidth[i-1] = 35;
            }

            System.out.println();
            // Print header
            for (int i=1; i <= colCount; i++)
            {	String colName = rsmd.getColumnName(i);
                System.out.print(colName+spaces(colWidth[i-1]-colName.length())+' ');
            }
            System.out.println("\n-----------------------------------------------------------------------");

            while (rst.next())
            {
                for (int i=1; i <= colCount; i++)
                {	Object obj = rst.getObject(i);
                    if (obj == null)
                        System.out.print(spaces(colWidth[i-1]));
                    else
                    {	String data = obj.toString();
                        System.out.print(data+spaces(colWidth[i-1]-data.length())+' ');
                    }
                }
                System.out.println();
                rowCount++;
            }
            if (rowCount == 0)
                System.out.println("No results.");

            rst.close();
        }
        catch (SQLException ex) {
            System.err.println("SQLException: " + ex);
        }
    }

    private static String spaces(int space)
    {
        String sp = "";
        for(int i=0;i<space;i++)
            sp = sp+" ";
        return sp;
    }

    private void closeConnection()
    {	try {
        con.close();
    }
    catch (SQLException ex) {
        System.err.println("Exception during connection close: " + ex);
    }
    }

    private void enterOrder()
    {
        String cid, onum, odate;
        System.out.print("Enter customer id for order: ");
        cid = getLine();
        System.out.print("Enter order number: ");
        onum = getLine();
        System.out.print("Enter order date: ");
        odate = getLine();

        String sqlSt = "INSERT INTO Orders (orderId, customerId, orderDate) VALUES ('"+onum+"','"+cid+"','"+odate+"');";
        doUpdate(sqlSt);

        enterLineItems(onum);

        updateOrderTotal(onum);
    }

    private void enterLineItems(String onum)
    {	String prodId, qty, pr, num;
        int count;
        int quantity = -1;
        double price = -1;

        // Get number of line items in order
        while (true)
        {
            System.out.print("How many items in this order? ");
            num = getLine();

            try {
                count = Integer.parseInt(num);
                break;
            }
            catch (Exception e)
            {	System.err.println("Invalid number entered.");
            }
        }

        double total = 0.0;

        for (int i=0; i < count; i++)
        {	System.out.print("Enter lineitem product id: ");
            prodId = getLine();
            System.out.print("Enter lineitem quantity: ");
            qty = getLine();
            System.out.print("Enter lineitem price: ");
            pr = getLine();

            try {
                quantity = Integer.parseInt(qty);
                price = Double.parseDouble(pr);
                total += quantity*price;
            }
            catch (Exception e)
            {	System.err.println("Invalid price or quantity entered.  Try again");
                i--;
            }

            String sqlSt = "INSERT INTO OrderedProduct (orderId, productId, quantity, price) VALUES ('"+onum+"','"+prodId+"',"+quantity+","+price+");";
            doUpdate(sqlSt);
        }
        System.out.println("Total value of ordered entered: "+total);
    }



    private void updateOrderTotal(String onum)
    {
        try
        {
            String sqlSt = "SELECT sum(quantity*price) from OrderedProduct where orderId = '"+onum+"';";
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery(sqlSt);
            rst.next();
            double total = rst.getDouble(1);
            rst.close();
            sqlSt = "UPDATE Orders SET total = "+total+" WHERE orderId= '"+onum+"';";
            doUpdate(sqlSt);
            System.out.println("Set order total to: "+total);
        }
        catch (SQLException e)
        {       System.out.println("Operation failed: "+e);
        }
    }


    private void updateCustomer()
    {
        String cid;
        System.out.print("Enter customer id to update: ");
        cid = getLine();

        String sqlSt = "SELECT * FROM Customer WHERE customerId = '"+cid+"';";

        try {	// Use an updatable resultset
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rst = stmt.executeQuery(sqlSt);

            if (!rst.next())
                System.out.println("No customer found with id: "+cid);
            else
            {	// Print current student values and ask for new ones
                ResultSetMetaData rsmd = rst.getMetaData();
                int colCount = rsmd.getColumnCount();
                String []newVals = new String[colCount];

                System.out.println("Below are the current values for the customer.\nEnter new ones or press Enter to keep current value.\n");

                for (int i=1; i <= colCount; i++)
                {	System.out.print(rsmd.getColumnName(i)+": ");

                    Object obj = rst.getObject(i);
                    if (obj == null)
                        System.out.println("[null]");
                    else
                    {	String data = obj.toString();

                        if (i == 1)
                            System.out.println(data+" (Not updatable)");
                        else
                        {
                            System.out.print(data+" New value: ");
                            newVals[i-1] = getLine();
                        }
                    }
                }

                // With updatable resultSets, just update the fields and save the row.
                // Note could have been done while entering data as well
                boolean update = false;
                for (int i=1; i <= colCount; i++)
                {	if (newVals[i-1] != null && !newVals[i-1].equals(""))	// Different value
                {	rst.updateString(i,newVals[i-1]);
                }
                }
                rst.updateRow();

				/*
				// Create update statement
				// Used when driver does not support updatable resultsets
				sqlSt = "UPDATE Customer SET ";
				boolean update = false;
				for (int i=1; i <= colCount; i++)
				{	if (newVals[i-1] != null && !newVals[i-1].equals(""))	// Different value
					{	if (i == colCount)
							sqlSt = sqlSt + rsmd.getColumnName(i)+ " = "+newVals[i-1]+",";
						else
							sqlSt = sqlSt + rsmd.getColumnName(i)+ " = '"+newVals[i-1]+"',";
						update = true;
					}
				}
				sqlSt = sqlSt.substring(0,sqlSt.length()-1);
				if (update)
					doUpdate(sqlSt);
				*/
            }
            rst.close();
        }
        catch (SQLException e)
        {	System.out.println("Operation failed: "+e);
        }
    }
}
