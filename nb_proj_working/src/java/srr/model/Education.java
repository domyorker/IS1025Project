package srr.model;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import srr.utilities.DbUtilities;

/**
 *
 * @author stevenschilinski
 */
public class Education {

    private BigInteger educationID;
    private String institution;
    private int graduationMonth;
    private int graduationYear;
    private String nameOfDegree;
    private String abbreviation;
    private String city;
    private int stateID;
    private DbUtilities db;
/**
 * Empty constructor 
 */
    public Education() {
        
    }
    /**
     * Constructor takes in educationID and retrieves the record from the
     * database
     *
     * @param educationID The id of the record in the database
     */
    public Education(String educationID) {

        db = new DbUtilities();
        String sql = "SELECT * FROM srr.education E JOIN degree_type_lookup on "
                + "degreeTypeID =  degree_type_lookup.typeID  where educationID='" + educationID + "'";
        try {
            ResultSet rs = db.getResultSet(sql);

            while (rs.next()) {
                this.graduationMonth = rs.getInt("graduationMonth");
                this.graduationYear = rs.getInt("graduationYear");
                this.educationID = BigInteger.valueOf(rs.getLong("educationID"));

                this.city = rs.getString("city");
                this.stateID = rs.getInt("stateID");
                this.nameOfDegree = rs.getString("name");
                this.abbreviation = rs.getString("abbreviation");

                String tmpIn = rs.getString("institution");
                if (tmpIn != null) {
                    this.institution = tmpIn;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Education.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public String getInstitution(){
        return this.institution;
    }
    
    public int getGraduationMonth(){
        return this.graduationMonth;
    }
    
    public int getGraduationYear(){
        return this.graduationYear;
    }
    
    public String getDegree(){
        return this.nameOfDegree;
    }
    
    public String getCity(){
        return this.city;
    }

    /**
     * A method to return this Education object as JSON
     *
     * @return A JSON-formatted string representing this instance
     */
    public JSONObject getEducationAsJSON() {

        JSONObject educationJSON = new JSONObject();
        try {
            educationJSON.put("educationID", educationID);
            educationJSON.put("institution", institution);
            educationJSON.put("graduationMonth", graduationMonth);
            educationJSON.put("graduationYear", graduationYear);
            educationJSON.put("nameOfDegree", nameOfDegree);
            educationJSON.put("abbreviation", abbreviation);
            educationJSON.put("city", city);
            educationJSON.put("stateID", stateID);
        } catch (JSONException ex) {
            Logger.getLogger(Education.class.getName()).log(Level.SEVERE, null, ex);
        }
        return educationJSON;
    }

}
