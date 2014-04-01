package srr.model;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import srr.utilities.DbUtilities;

/**
 *
 * @author stevenschilinski
 */
public class Experience {

    private BigInteger experienceID;
    private String employer;
    private String jobTitle;
    private Date startDate;
    private boolean presentJob;
    private Date endDate;
    private String summary;

    public Experience(BigInteger experienceID) {
        DbUtilities db = new DbUtilities();
        try {
            ResultSet rs = db.getResultSet("SELECT * FROM srr.experience WHERE experienceID ="
                    + experienceID);

            while (rs.next()) {
                this.experienceID = BigInteger.valueOf(rs.getLong("experienceID"));
                this.employer = rs.getString("employer");// exployer in db?
                this.startDate = rs.getDate("startDate");
                this.jobTitle = rs.getString("jobTitle");
                this.endDate = rs.getDate("endDate");
                this.presentJob = rs.getBoolean("presentJob");
                this.summary = rs.getString("summary");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Experience.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.releaseConnection();
        }

    }
/**
 * Very straight-forward..I like it...don't forget to add javadoc please
 * @param employer
 * @param startDate
 * @param presentJob 
 */
    public Experience(String employer, Date startDate, boolean presentJob) {
       // this.experienceID = UUID.randomUUID().toString();
        this.startDate = startDate;
        this.presentJob = presentJob;
        this.employer = employer;

        DbUtilities db = new DbUtilities();
        //don't forget to account for relationship tables (resume_experience links Experience to the resume).
        //Also, there is a way to obtain the insert's ID...the experienceID
        //db.executeQuery("Insert into experience (NULL, employer, startDate, presentJob) "
       //         + " values ('" + this.experienceID + "','" + this.employer + "','" + this.startDate + "','" + this.presentJob + "')");

    }

}
