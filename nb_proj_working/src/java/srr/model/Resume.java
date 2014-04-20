package srr.model;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import srr.utilities.DbUtilities;
import static srr.utilities.StringUtilities.cleanMySqlInsert;
import static srr.utilities.StringUtilities.encodePassword;

/**
 * A class to represent a resume
 *
 * @author Jose Marte
 * @author stevenschilinski
 */
public class Resume {

    private BigInteger userID, resumeID = null;
    private String title, objective, experience, accomplishments;

    //list of related related objects
    private ArrayList<Certification> certificationsList;
    private ArrayList<Course> coursesList;
    private ArrayList<Education> educationList;
    private ArrayList<Interest> interestsList;
    private ArrayList<Membership> membershipsList;
    private ArrayList<Reference> referencesList;
    private ArrayList<Skill> skillsList;
    private ArrayList<Experience> experienceList;
    private DbUtilities db;
    private ResultSet rs;

    /**
     * Empty constructor
     */
    public Resume() {

    }

    /**
     * Constructor takes in an ID and loads the record from the database
     *
     * @param ID The ID of the resume in the database
     */
    public Resume(BigInteger ID) {
        this.resumeID = ID;
        loadFromDb();
    }

    /**
     * Constructor takes in a title to instantiate this object
     *
     * @param title
     */
    public Resume(String title) {
        this.userID = null;
        this.resumeID = null;
        this.title = title;
    }

    /**
     * Constructor takes in an ID and a title; populates those fields
     *
     * @param userID
     * @param title
     */
    public Resume(BigInteger userID, String title) {
        this.userID = userID;
        this.title = title;
    }

    /**
     * Helper method to load the resume given a String ID
     *
     * @param ID The ID as a String
     * @return True on success
     */
    public boolean loadFromString(String ID) {
        this.resumeID = new BigInteger(ID);
        System.out.println("LATEST: this resume's ID: " + this.getResumeID());
        return (loadFromDb());

    }

    /**
     * Constructor takes in an ID and populates all fields from the database
     *
     * @param ID The record's id as a String
     *
     */
    private boolean loadFromDb() {
        if (this.resumeID == null) {
            return false;
        }
        boolean success = false;
        System.out.println("in loadFromDb***********************************");
        db = new DbUtilities();
        try {
            rs = db.getResultSet("SELECT * FROM srr.student_resume WHERE resumeID ='" + this.resumeID + "'");
            //set the title
            if (rs.next()) {
                this.setTitle(rs.getString("title"));
                this.setObjective(rs.getString("objective"));
                this.setExperience(rs.getString("experience"));
                this.setAccomplishments(rs.getString("accomplishments"));
            }
            rs = db.getResultSet("SELECT userID FROM srr.student_resume WHERE resumeID ='" + this.resumeID + "';");
            if (rs.next()) {
                this.userID = new BigInteger(rs.getString("userID"));
            }

            rs = db.getResultSet("SELECT * FROM srr.resume_skill WHERE resumeID ='"
                    + this.resumeID + "'");
            while (rs.next()) {
                if (this.skillsList == null) {
                    this.skillsList = new ArrayList<>();
                }
                this.skillsList.add(new Skill(BigInteger.valueOf(rs.getLong("skillID"))));
            }
            // this.certificationsList = temp.getCertificationsFromDb(this.resumeID);
            //  this.coursesList = temp.getCoursesFromDb(this.resumeID);
          //  String id = String.valueOf(this.resumeID);
            // this.membershipsList = temp.getMembershipsFromDb(id);
            //  this.interestsList = temp.getInterestsFromDb(id);
            System.out.println("about to call getExp...");
            this.experienceList = getExperienceFromDb();
            System.out.println("called getExperience...");
            // this.educationList = temp.getEducationFromDb(this.userID);
            success = true;
        } catch (SQLException | NullPointerException ex) {
            System.out.println("Error while trying to load the resume: " + ex.getStackTrace());
            success = false;
        } finally {
            db.releaseConnection();

        }
        return success;

    }

    /**
     * A helper method to populate the Certification list
     *
     * @param resumeID The resume id of the certifications
     * @return A list of Certification objects for that particular resume
     */
    private ArrayList<Certification> getCertificationsFromDb(BigInteger resumeID) {

        db = new DbUtilities();
        ArrayList<Certification> tempList = new ArrayList<>();
        Certification tempCertification;

        String sql = "SELECT * FROM srr.resume_certification RC";
        sql += " LEFT JOIN srr.certification C ON RC.certificationID = C.certificationID";
        sql += " WHERE  resumeID =" + resumeID + ";";

        try {
            this.rs = db.getResultSet(sql);

            while (rs.next()) {
                tempCertification = new Certification(BigInteger.valueOf(rs.getLong("certificationID")));
                tempList.add(tempCertification);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentAccount.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("The sql string: " + sql); //*****************
            System.out.println("Error: " + ex.getMessage());
        } finally {
            db.releaseConnection();
        }
        return tempList;
    }

    /**
     * A helper method to populate the Course list
     *
     * @param resumeID The resume id of the courses
     * @return A list of Course objects for that particular resume
     */
    //TODO: continue to work on Course Class
    private ArrayList<Course> getCoursesFromDb(BigInteger resumeID) {

        db = new DbUtilities();
        ArrayList<Course> tempList = new ArrayList<>();
        Course tempCourse;

        String sql = "SELECT * FROM srr.resume_course RC";
        sql += " LEFT JOIN srr.course C ON RC.courseID = C.courseID";
        sql += " WHERE  resumeID =" + resumeID + ";";

        try {
            this.rs = db.getResultSet(sql);

            while (rs.next()) {
                long courseID = rs.getLong("courseID");
                String ID = String.valueOf(courseID);
                tempCourse = new Course(ID);
                tempList.add(tempCourse);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentAccount.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("The sql string: " + sql); //*****************
            System.out.println("Error: " + ex.getMessage());
        } finally {
            db.releaseConnection();
        }
        return tempList;
    }

    private ArrayList<Education> getEducationFromDb(BigInteger userID) {
        ArrayList<Education> tmpE = new ArrayList<>();
        db = new DbUtilities();
        try {
            rs = db.getResultSet("Select * from srr.education join srr.student_education on"
                    + " srr.education.educationID = srr.student_education.educationID"
                    + " where srr.student_education.studentId = '" + userID + "';");
            while (rs.next()) {
                tmpE.add(new Education(rs.getString("educationID")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Resume.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tmpE;
    }

    private ArrayList<Membership> getMembershipsFromDb(String resumeID) {
        db = new DbUtilities();
        ArrayList<Membership> tmpList = new ArrayList<>();
        try {
            rs = db.getResultSet("Select * from srr.resume_membership join srr.membership on"
                    + " srr.resume_membership.membershipID = srr.membership.membershipID "
                    + " where srr.resume_membership.resumeID ='" + resumeID + "';");

            while (rs.next()) {
                String meID = rs.getString("membershipID");
                Membership tmpMember = new Membership(meID);
                tmpList.add(tmpMember);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Resume.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.releaseConnection();
        }

        //this.membershipsList = tmpList;
        return tmpList;

    }

    private ArrayList<Interest> getInterestsFromDb(String resumeID) {
        db = new DbUtilities();
        ArrayList<Interest> tmpList = new ArrayList<>();
        try {
            rs = db.getResultSet("Select * from srr.resume_interest join srr.interest on"
                    + " srr.resume_interest.interestID = srr.interest.interestID "
                    + " where srr.resume_interest.resumeID ='" + resumeID + "';");

            while (rs.next()) {
                String meID = rs.getString("interestID");
                Interest tmpInterest = new Interest(meID);
                tmpList.add(tmpInterest);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Resume.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.releaseConnection();
        }

        //this.membershipsList = tmpList;
        return tmpList;

    }

    /**
     * A helper method to obtain the list of Experience from the DB
     *
     * @param resumeID
     * @return An ArrayList of Experience objects
     */
    private ArrayList<Experience> getExperienceFromDb() {
         System.out.println("in getExperience...." );
        db = new DbUtilities();
         Experience tempExperience;
        ArrayList<Experience> tempList = null;
        String sql = "SELECT experienceID FROM srr.resume_experience WHERE resumeID ='" + this.resumeID + "';";
        System.out.println("select experience: " + sql);
        try {
            rs = db.getResultSet(sql);
            while (rs.next()) {
                if (tempList == null) {
                    tempList = new ArrayList<>();
                }
                tempExperience = new Experience(BigInteger.valueOf(rs.getLong("experienceID")));
                tempList.add(tempExperience);
            }
        } catch (SQLException ex) {
            System.out.println("Error while trying to load the experience list (getExperienceFromDb()): " + ex.getMessage());
        } finally {
            db.releaseConnection();
        }

        //this.membershipsList = tmpList;
        return tempList;
    }

    /**
     * Commit for the first time
     *
     * @param account
     * @return
     */
    public boolean createToDb(StudentAccount account) {
        String sql = "";
        ResultSet rs;

        if (this.resumeID != null) {
            return true; //already saved, must use update() to make changes
        }
        sql = "INSERT INTO srr.student_resume (resumeID, userID, title) VALUES"
                + "(NULL, " + account.getStudentID() + ", '" + cleanMySqlInsert(this.title) + "');";

        this.db = new DbUtilities();
        try {
            rs = db.executeQuery(sql);
            if (rs.next()) {//grab the generated ID
                this.resumeID = BigInteger.valueOf(rs.getLong(1));
            } else {
                return false; //failed to commit to db
            }

        } catch (SQLException ex) {
            System.out.println("The sql string: " + sql); //*****************
            System.out.println("Error: " + ex.getMessage());
        }
        finally {
            db.releaseConnection();
        }
        return true;
    }

    /**
     * A getter method to obtain the resume's ID
     *
     * @return the resumeID
     */
    public BigInteger getResumeID() {
        return this.resumeID;
    }

    /**
     * @param resumeID the resumeID to set
     */
    public void setResumeID(BigInteger resumeID) {
        this.resumeID = resumeID;
    }

    /**
     * @return the userID
     */
    public BigInteger getUserID() {
        return this.userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(BigInteger userID) {
        this.userID = userID;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the objective
     */
    public String getObjective() {
        if (objective == null) {
            objective = "";
        }
        return objective;
    }

    /**
     * @param objective the objective to set
     */
    public void setObjective(String objective) {
        this.objective = objective;
    }

    /**
     * @return the experience
     */
    public String getExperience() {
        if (experience == null) {
            experience = "";
        }
        return experience;
    }

    /**
     * @param experience the experience to set
     */
    public void setExperience(String experience) {
        this.experience = experience;
    }

    /**
     * @return the accomplishments
     */
    public String getAccomplishments() {
        if (accomplishments == null) {
            accomplishments = "";
        }
        return accomplishments;
    }

    /**
     * @param accomplishments the accomplishments to set
     */
    public void setAccomplishments(String accomplishments) {
        this.accomplishments = accomplishments;
    }

    /**
     * @return the certificationsList
     */
    public ArrayList<Certification> getCertificationsList() {
        return this.certificationsList;
    }

    /**
     * @return the certificationsList
     */
    public ArrayList<Experience> getExperienceList() {
        return this.experienceList;
    }

    /**
     * @param certificationsList the certificationsList to set
     */
    public void setCertificationsList(ArrayList<Certification> certificationsList) {
        this.certificationsList = certificationsList;
    }

    /**
     * @return the coursesList
     */
    public ArrayList<Course> getCoursesList() {
        return coursesList;
    }

    /**
     * @param coursesList the coursesList to set
     */
    public void setCoursesList(ArrayList<Course> coursesList) {
        this.coursesList = coursesList;
    }

    /**
     * @return the educationList
     */
    public ArrayList<Education> getEducationList() {
        return educationList;
    }

    /**
     * @param educationList the educationList to set
     */
    public void setEducationList(ArrayList<Education> educationList) {
        this.educationList = educationList;
    }

    /**
     * @return the interestsList
     */
    public ArrayList<Interest> getInterestsList() {
        return interestsList;
    }

    /**
     * @param interestsList the interestsList to set
     */
    public void setInterestsList(ArrayList<Interest> interestsList) {
        this.interestsList = interestsList;
    }

    /**
     * @return the membershipsList
     */
    public ArrayList<Membership> getMembershipsList() {
        return membershipsList;
    }

    /**
     * @param membershipsList the membershipsList to set
     */
    public void setMembershipsList(ArrayList<Membership> membershipsList) {
        this.membershipsList = membershipsList;
    }

    /**
     * @return the referencesList
     */
    public ArrayList<Reference> getReferencesList() {
        return referencesList;
    }

    /**
     * @param referencesList the referencesList to set
     */
    public void setReferencesList(ArrayList<Reference> referencesList) {
        this.referencesList = referencesList;
    }

    /**
     * @return the skillsList
     */
    public ArrayList<Skill> getSkillsList() {
        return skillsList;
    }

    /**
     * @param skillsList the skillsList to set
     */
    public void setSkillsList(ArrayList<Skill> skillsList) {
        this.skillsList = skillsList;
    }

    /**
     * Updates this resume in the DB
     *
     * @return True if successful, false otherwise
     */
    public boolean update() {
        boolean success = false;
        String sqlResumeUpdate, sqlExperienceUpdate;

        //resume must already exist
        if (this.resumeID == null) {
            return false;
        }

        //important: delete all records related to this resume...we are starting fresh...
        if (!clearDBRelations()) {
            return false; //stop here
        }

        sqlResumeUpdate = "UPDATE srr.student_resume SET title= '" + cleanMySqlInsert(this.title) + "', objective= '" + cleanMySqlInsert(this.objective)
                + "', experience= '" + cleanMySqlInsert(this.experience) + "', accomplishments='" + cleanMySqlInsert(this.accomplishments) + "' WHERE resumeID=" + this.resumeID;

        sqlExperienceUpdate = "";
        //NOW SAVE THE SKILLS
        if (this.skillsList != null) {
            for (Skill skill : this.skillsList) {
                skill.commitToDb(this.resumeID);
            }
        }
        //NOW SAVE THE Experiece list
        if (this.experienceList != null) {
            for (Experience exp : this.experienceList) {
                exp.commitToDb(this.resumeID);
            }
        }
        this.db = new DbUtilities();
        ResultSet rsTemp;
        try {
            rsTemp = db.executeQuery(sqlResumeUpdate);

            //skills INSPECT..................
            if (this.skillsList != null) {
                for (Skill skill : this.skillsList) {
                    if (skill.getSkillID() == null) {
                        skill.commitToDb(this.resumeID);
                    }
                }
            } else {
                System.out.println("The skills list is null!");
            }
            success = true;
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            db.releaseConnection();
        }
        return success;

    }

    public void addSkill(String skillText) {
        if (this.skillsList == null) {
            this.skillsList = new ArrayList<>();
        }
        this.skillsList.add(new Skill(skillText));
    }

    public void addExperience(Experience experience) {
        if (this.experienceList == null) {
            this.experienceList = new ArrayList<>();
        }
        this.experienceList.add(experience);
    }

    public boolean isCommitted() {
        if (this.resumeID == null) {
            return false;
        }
        return true;
    }

    /**
     * Helper method to remove records associated with a resume in order to
     * update it with new ones
     *
     * @return
     */
    public boolean clearDBRelations() {
        boolean success = true;

        //first, skills....
        try {
            if (this.skillsList != null) {
                for (Skill skill : this.skillsList) {
                    skill.removeFromDb();
                }
            }
        } catch (NullPointerException ex) {
            System.out.println("Error in clearDBRelations: " + ex.getMessage());
            success = false;
        }
        //now experience
        if (this.experienceList != null) {
            for (Experience exp : this.experienceList) {
                exp.removeFromDb();
            }
        }
        return success;
    }
}
