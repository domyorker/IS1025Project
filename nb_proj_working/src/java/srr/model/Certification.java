package srr.model;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import srr.utilities.DbUtilities;
import static srr.utilities.StringUtilities.parseStringToDate;

/**
 * A class to represent a certification
 *
 * @author Jose Marte
 */
public class Certification {

    private BigInteger certificationID;
    private String name;
    private String institution;
    private Date dateAttained;
    private String summary;
    DbUtilities db;

    /**
     * Empty constructor
     */
    public Certification() {
    }

    /**
     * Constructor takes an existing certificationID and pulls it from the
     * database
     *
     * @param certificationID The record id to pull from the db
     */
    public Certification(BigInteger certificationID) {
        this.certificationID = certificationID; //initialize this prop.
        getCertificationFromDb(certificationID);
    }

    //SETTERS ***************
    public void setCertificationID(BigInteger ID) {
        this.certificationID = ID;
    }

    public void setName(BigInteger ID) {
        this.certificationID = ID;
    }

    public void setInstitution(String inst) {
        this.institution = inst;
    }

    public void setDateAttained(Date date) {
        this.dateAttained = date;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    //GETTERS ***************
    public BigInteger getCertificationID() {
        return this.certificationID;
    }

    public String getName() {
        return this.name;
    }

    public String getInstitution() {
        return this.institution;
    }

    public Date getDateAttained() {
        return this.dateAttained;
    }

    public String getSummary() {
        return this.summary;
    }

    /**
     * A method to grab the certification with the given id from the database
     *
     * @param certificationID The record's id.
     */
    private void getCertificationFromDb(BigInteger certificationID) {

        this.db = new DbUtilities();
        //ensure private field is set...
        this.certificationID = certificationID;

        ResultSet rs;
        try {
            rs = this.db.getResultSet("SELECT * FROM srr.certification WHERE certificationID = " + certificationID + ";");
            while (rs.next()) {
                this.name = rs.getString("name");
                this.institution = rs.getString("institution");
                this.dateAttained = parseStringToDate(rs.getString("dateAttained"));
                this.summary = rs.getString("summary");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Certification.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.releaseConnection();
        }
    }
}
