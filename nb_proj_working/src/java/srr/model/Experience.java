package srr.model;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import srr.utilities.DbUtilities;
import static srr.utilities.StringUtilities.cleanMySqlInsert;

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
    DbUtilities db;

    /**
     * Empty constructor allows the creation of in-memory instances
     *
     */
    public Experience() {

    }

    /**
     * Constructor allows the creation of an in-memory instance with all fields,
     * except for ID
     *
     * @param emp The name of the employer
     * @param jobTitle The job title
     * @param endDate The ending date if any
     * @param startDate The starting date
     * @param present Boolean to indicate if this job is the present job
     * @param summary A summary of responsibilities
     */
    public Experience(String emp, String jobTitle, Date startDate, boolean present, Date endDate, String summary) {
        this.employer = emp;
        this.jobTitle = jobTitle;
        this.startDate = startDate;
        this.presentJob = present;
        this.endDate = endDate;
        this.summary = summary;
    }

    /**
     * Constructor takes in the ID of an existing Experience in the database and
     * instantiates the properties
     *
     * @param experienceID
     */
    public Experience(BigInteger experienceID) {
        db = new DbUtilities();
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
     * A method to create and store this instance of Experience in the database
     *
     * @param resumeID
     *
     * @param startDate The job's starting date
     * @param presentJob Boolean indicating whether this experience is the
     * current occupation
     * @return
     */
    public boolean commitToDb(BigInteger resumeID) {

        boolean success = false;
        //already in db..use update()
        if (this.experienceID != null) {
            return false;
        }
        ResultSet rs;
        String sql;
        sql = "INSERT INTO srr.experience VALUES (NULL, '" + cleanMySqlInsert(this.employer) + "', '" + cleanMySqlInsert(this.jobTitle) + "', '";
        sql += this.startDate + "', '" + this.presentJob + "', '" + this.endDate + "', '";
        sql += cleanMySqlInsert(this.summary) + "');";
        db = new DbUtilities();

        try {
            rs = db.executeQuery(sql);
            if (rs.next()) {
                this.experienceID = BigInteger.valueOf(rs.getLong(1));
            }
            sql = "INSERT INTO srr.resume_experience VALUES(" + resumeID + ", " + this.experienceID + ");";
            rs = db.executeQuery(sql);
            rs = null;
            success = true;
        } catch (SQLException ex) {
            System.out.print("ERROR IN EXPERIENCE!" + ex.getMessage());
        } finally {
            db.releaseConnection();
        }
        return success;
    }

    /**
     * @return the experienceID
     */
    public BigInteger getExperienceID() {
        return experienceID;
    }

    /**
     * A setter to set the experience ID of this object
     *
     * @param experienceID The experience to save
     */
    public void setExperienceID(BigInteger experienceID) {
        this.experienceID = experienceID;
    }

    /**
     * A method to obtain the employer's name
     *
     * @return the employer
     */
    public String getEmployer() {
        return employer;
    }

    /**
     * A method to set the employer
     *
     * @param employer the employer to set
     */
    public void setEmployer(String employer) {
        this.employer = employer;
    }

    /**
     * @return the jobTitle
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * @param jobTitle the jobTitle to set
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the presentJob
     */
    public boolean isPresentJob() {
        return presentJob;
    }

    /**
     * @param presentJob the presentJob to set
     */
    public void setPresentJob(boolean presentJob) {
        this.presentJob = presentJob;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary the summary to set
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * A method to delete an experience from the database
     *
     * @return
     */
    public boolean removeFromDb() {
        boolean success = false;
        if (this.experienceID == null) {
            return false;
        }
        String sql = "DELETE FROM srr.experience WHERE experienceID=" + this.experienceID;
        System.out.println("About to remove this EXPERIENCE from the db: " + this.experienceID);

        try {
            db = new DbUtilities();
            db.executeQuery(sql);
            success = true;
        } catch (SQLException ex) {
            System.out.println("Error while trying to remove experience: " + this.experienceID);
        } finally {
            db.releaseConnection();
        }
        return success;
    }

}
