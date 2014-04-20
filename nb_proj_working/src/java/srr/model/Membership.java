package srr.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import srr.utilities.DbUtilities;

/**
 * A class to represent a membership
 *
 * @author Sean Carney
 */
public class Membership {

    String membershipID;
    String membershipName;

    /**
     * Constructor takes a membership Id as a string and retrieves it from the
     * database
     *
     * @param membershipID
     */
    public Membership(String membershipID) {
        DbUtilities db = new DbUtilities();
        try {
            ResultSet rs = db.getResultSet("Select * from srr.membership where membershipID ='" + membershipID + "'");
            while (rs.next()) {
                this.membershipName = rs.getString("membershipName");
            }
            this.membershipID = membershipID;
        } catch (SQLException ex) {
            Logger.getLogger(Membership.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String getName() {
        return this.membershipName;
    }

}
