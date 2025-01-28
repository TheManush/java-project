
package airlinemanagementsystem;

import java.sql.*;

public class Conn
{
    Connection c;
    Statement s;
    
    
    
   public Conn()
   {
    try
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        c=DriverManager.getConnection("jdbc:mysql:///airlinemanagementsystem", "root","ahnaferpassword");
        s=c.createStatement();
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
    
    
 }
     public Connection getConnection() {
        return c;
    }

    // Optional: Expose Statement as well if needed
    public Statement getStatement() {
        return s;
    }
           
    
    
}