package srr.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import srr.utilities.DbUtilities;
import static srr.utilities.StringUtilities.encodePassword;

/**
 *
 * @author stevenschilinski
 *
 */
public class Admin {

    private int adminID;
    private String username;
    private String password;
    private String fName;
    private String lName;
    DbUtilities db;

    public Admin(int adminID) {

        this.adminID = adminID;
        db = new DbUtilities();
        try {
            ResultSet rs = db.getResultSet("SELECT * FROM srr.admin WHERE adminID ='" + adminID + "'");
            while (rs.next()) {

                this.username = rs.getString("username");
                this.fName = rs.getString("fName");
                this.lName = rs.getString("lName");
                this.password = rs.getString("password");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Admin(String username, String password, String fName, String lName) {
        // this.adminID = ""; //UUID.randomUUID().toString(); >don't pass this it's auto
        this.username = username;
        this.password = password;
        this.fName = fName;
        this.lName = lName;

        db = new DbUtilities();
        try {
            db.executeQuery("INSERT INTO srr.admin VALUES (NULL,'" + username + "','" + encodePassword(password) + "','" + fName + "','" + lName + "');");
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.releaseConnection();
        }

    }
//NOW NEED SETTERS AND GETTERS...NOT A PRIORITY FOR NOW
}
