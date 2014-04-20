package srr.model;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import srr.utilities.DbUtilities;
import static srr.utilities.StringUtilities.cleanMySqlInsert;

/**
 * A class to represent a skill
 *
 * @author Jose Marte
 */
public class Skill {

    private BigInteger skillID = null;
    private String name = "";
    DbUtilities db;

    /**
     * Constructor takes in an ID and loads it from the database
     *
     * @param ID
     */
    public Skill(BigInteger ID) {
        this.skillID = ID;
        //retrive Skill from db 
        ResultSet rs;
        String sql = "SELECT * FROM srr.skill WHERE  skillID ='"
                + this.skillID + "';";
        try {
            db = new DbUtilities();
            rs = db.getResultSet(sql);
            if (rs.next()) {
                this.name = rs.getString("name");
            }
        } catch (SQLException ex) {
            System.out.println("Error while trying to retrieve skill from db: " + this.skillID);
        }
    }

    public Skill(String text) {
        this.name = text;
    }

    /**
     * @return the skillID
     */
    public BigInteger getSkillID() {
        return this.skillID;
    }

    /**
     * @param skillID the skillID to set
     */
    public void setSkillID(BigInteger skillID) {
        this.skillID = skillID;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method to commit this object to the database
     *
     * @param resumeID The resume this skill is related to
     * @return True if the commit succeeds; false otherwise.
     */
    public boolean commitToDb(BigInteger resumeID) {
        String sql;
        boolean success = false;
        ResultSet rs;

        if (this.skillID != null) { //already in db
            System.out.println("Skill Exists: Skill ID " + this.skillID);
            return false; //call the update method instead

        }
        sql = "INSERT INTO srr.skill VALUES(NULL,'" + cleanMySqlInsert(this.name) + "');";
       
        try {
            db = new DbUtilities();
            rs = db.executeQuery(sql);
            if (rs.next()) { //set the generated ID for each Skill in the list
                this.skillID = BigInteger.valueOf(rs.getLong(1)); //get and set the ID
            }
            //now link the resume to this skill
            sql = "INSERT INTO srr.resume_skill "
                    + " VALUES (" + resumeID + ", " + this.skillID + ");";
             rs = db.executeQuery(sql);
            rs = null;
            success = true;
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            db.releaseConnection();
        }
        return success;
    }

    private boolean update() {
        String sql;

        if (this.skillID == null) {
            return false;
        }
        sql = "UPDATE srr.skill SET name ='" + cleanMySqlInsert(this.name) + "' WHERE skillID=" + this.skillID;
        try {
            db = new DbUtilities();
            ResultSet rs = db.executeQuery(sql);
            if (rs.next()) { //set the generated ID for each Skill in the list
                this.skillID = BigInteger.valueOf(rs.getLong(1)); //get and set the ID
            }
            return true;
        } catch (SQLException ex) {
            System.out.print("Error in update skill: " + this.skillID);
        }
        return false;

    }

    /**
     * A method to remove this skill from the database
     *
     * @param resumeID The resume associated with this skill
     * @return True if successful, false otherwise
     */
    public boolean removeFromDb() {
        boolean success = false;
        ResultSet rs;
        if (this.skillID == null) {
            return false;
        }

        String sqlResumeSkill = "DELETE FROM srr.resume_skill WHERE skillID=" + this.skillID;
        String sqlSkill = "DELETE FROM srr.skill WHERE skillID=" + this.skillID;
     
        try {
            db = new DbUtilities();
            //first we delete the lookup table since it points to skills
            rs = db.executeQuery(sqlResumeSkill);
            rs = db.executeQuery(sqlSkill);
            rs = null;
            success = true;
        } catch (SQLException ex) {
            System.out.println("Error while trying to remove skill: " + this.getSkillID());
        } finally {
            db.releaseConnection();
        }
        return success;
    } //end removeFromDb()
}
