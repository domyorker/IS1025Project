/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package srr.model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import srr.utilities.DbUtilities;

/**
 * A class to represent an interest or hobby
 * @author Sean Carney
 */
public class Interest {
    String interestID;
    String interestName;
    

    public Interest(String interestID)
    {
        DbUtilities db = new DbUtilities();
           try {
               ResultSet rs = db.getResultSet("Select * from srr.interest where interestID ='" +interestID +"'");
               while(rs.next())
               {
                    this.interestName =   rs.getString("interestName");
               }
               this.interestID = interestID;
           } catch (SQLException ex) {
               Logger.getLogger(Membership.class.getName()).log(Level.SEVERE, null, ex);
           }

    }
    
    public String getName(){
        return this.interestName;
    }  
}
