package srr.model;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import srr.utilities.DbUtilities;
import static srr.utilities.StringUtilities.cleanMySqlInsert;
import static srr.utilities.StringUtilities.parseDateString;
import static srr.utilities.StringUtilities.parseMySqlDateString;

/**
 *
 * @author stevenschilinski
 */
public class Experience {

    private BigInteger experienceID;
    private String employer, jobTitle, startDate = ""; //working with dates in java sucks!
    private int presentJob;
    private String endDate = ""; //I'm sticking with strings!
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
    public Experience(String jobTitle, String emp, String startDate, int present, String endDate, String summary) {
        this.jobTitle = jobTitle;
        this.employer = emp;
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
                this.startDate = rs.getString("startDate");
                if (!this.startDate.isEmpty()) {
                    this.startDate = parseMySqlDateString(this.startDate); //convert to MM/dd/yyyy format
                }
                this.jobTitle = rs.getString("jobTitle");
                this.endDate = rs.getString("endDate");
//                if (!this.endDate.isEmpty()) {
//                    System.out.println("End date not emppty");
//                    //    this.endDate = parseMySqlDateString(this.endDate); //convert to MM/dd/yyyy format
//                }
                if (this.endDate != null) {
                    System.out.println("End date not null");
                    //    this.endDate = parseMySqlDateString(this.endDate); //convert to MM/dd/yyyy format
                }
                this.presentJob = rs.getInt("presentJob");
                this.summary = rs.getString("summary");
            }

        } catch (SQLException | ParseException ex) {
            System.out.println("Error while trying to laod the resume experience: " + ex.getMessage());
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
        String sql, sqlStartDate, sqlEndDate;

        db = new DbUtilities();

        try {
            if (this.startDate == null || this.startDate.isEmpty()) {
                return false; //startDate is required
            } else {
                sqlStartDate = "'" + parseDateString(this.startDate) + "'";
            }
            if (this.presentJob == 0) { // 0 = not present job
                if (this.endDate == null || this.endDate.isEmpty()) {
                    sqlEndDate = "NULL";
                } else {
                    sqlEndDate = "'" + parseDateString(this.endDate) + "'";
                }
            } else {
                sqlEndDate = "NULL";
            }
            sql = "INSERT INTO srr.experience VALUES (NULL, '" + cleanMySqlInsert(this.employer) + "', '" + cleanMySqlInsert(this.jobTitle) + "', "
                    + sqlStartDate + ", " + this.presentJob + ", " + sqlEndDate + ", '" + cleanMySqlInsert(this.summary) + "');";
            System.out.println(sql);
            rs = db.executeQuery(sql);
            if (rs.next()) {
                this.experienceID = BigInteger.valueOf(rs.getLong(1));
            }
            sql = "INSERT INTO srr.resume_experience VALUES(" + resumeID + ", " + this.experienceID + ");";
            rs = db.executeQuery(sql);
            rs = null;
            success = true;
        } catch (SQLException | ParseException ex) {
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
    public String getStartDate() {
        if (startDate == null || startDate.isEmpty()) {
            return "";
        }
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the presentJob
     */
    public boolean isPresentJob() {
        if (this.presentJob == 1) {
            return true;
        }
        return false;
    }

    /**
     * @param presentJob the presentJob to set
     */
    public void setPresentJob(int presentJob) {
        this.presentJob = presentJob;
    }

    /**
     * @return the endDate
     */
    public String getEndDate() {
        if (endDate == null || endDate.isEmpty()) {
            return "";
        }
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
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
        String sqlRes_Exp = "DELETE FROM srr.resume_experience WHERE experienceID=" + this.experienceID;
        String sqlExp = "DELETE FROM srr.experience WHERE experienceID=" + this.experienceID;

        try {
            db = new DbUtilities();
            db.executeQuery(sqlRes_Exp);
            db.executeQuery(sqlExp);
            success = true;
        } catch (SQLException ex) {
            System.out.println("Error while trying to remove experience: " + this.experienceID);
        } finally {
            db.releaseConnection();
        }
        return success;
    }

}
