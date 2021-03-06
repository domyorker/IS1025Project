package srr.model;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import srr.utilities.DbUtilities;
import static srr.utilities.StringUtilities.*;

/**
 * A class to represent a student's account
 *
 * @author stevenschilinski
 * @author Jose Marte
 */
public class StudentAccount {

    private BigInteger studentID;
    private String userName = "";
    private String password = "";
    private String fName;
    private String lName;
    private String email;
    private String addressLine1;
    private String addressLine2;
    private int stateID;
    private String city;
    private String ZIP;
    private String phone;
    private int classLevelID;
    //education is a 1 to many rel.
    ArrayList<Education> educationList;
    ArrayList<Resume> resumeList;

    private DbUtilities db;

    /**
     * Constructor initializes a student's account with the given userName
     *
     * @param userName The login username
     */
    public StudentAccount(String userName) {
        this.userName = userName;
    }

    /**
     * Constructor initializes a student's account with the given userName and
     * password
     *
     * @param userName The login username
     * @param password the login password as plaintext
     */
    public StudentAccount(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     * A method to obtain this account's associated resume IDs.
     *
     * @return
     */
    public HashMap getResumeList() {

        String sql;
        ResultSet rs;
        BigInteger tempID;
        String tempTitle;
        //ID required...;
        HashMap tempList = new HashMap();

        if (this.studentID == null) {
            return null;
        }
        sql = "SELECT resumeID, title FROM srr.student_resume WHERE userID=" + this.studentID;

        try {
            db = new DbUtilities();
            rs = db.getResultSet(sql);
            while (rs.next()) {
                tempID = BigInteger.valueOf(rs.getLong("resumeID"));
                tempTitle = rs.getString("title");

                tempList.put(tempID, tempTitle);
            }
        } catch (SQLException | NullPointerException ex) {
            System.out.println("Exception in getResumeList: " + ex.getMessage());
            return null;
        } finally {
            db.releaseConnection();
        }
        return tempList;
    }

    /**
     * Constructor pulls the given account from the db and populates the class'
     * fields
     *
     * @param studentID The student account's ID in the db
     */
    public StudentAccount(BigInteger studentID) {
        //get this ID from db
        getStudentAccountFromDb(studentID);
    }

    /**
     * A method to permanently store the properties of this class to the db
     *
     * @return True if values are saved successfully or false otherwise
     */
    public boolean commit() {
        String sql = "INSERT INTO srr.student_account (studentID, userName, password, fName, lName, email, addressLine1, addressLine2, stateID, city, ZIP, phone, classLevelID)";
        sql += " VALUES (NULL, '" + this.userName + "', '" + encodePassword(this.password) + "', '" + this.fName + "', '" + this.lName;
        sql += "', '" + this.email + "', '" + this.addressLine1 + "', '" + this.addressLine2 + "', " + this.stateID;
        sql += ", '" + this.city + "', " + this.ZIP + ", '" + this.phone + "', " + this.classLevelID + ")";

        DbUtilities db = new DbUtilities();
        boolean success = false;
        try {
            ResultSet rsIdentityKeys = db.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return success;
    }
//helper methods ***************

    /**
     * A helper method to check whether the userName exists in the database
     *
     * @return true if the userName exists or false otherwise
     */
    public boolean isExistingUser() {
        boolean exists = true; //assume user is already in db

        if (!this.userName.isEmpty()) {
            String sql = "SELECT studentID FROM srr.student_account WHERE userName = '" + this.userName + "';";
            this.db = new DbUtilities();
            ResultSet rs;
            try {
                rs = db.getResultSet(sql);
                if (!rs.first()) { //no rows
                    exists = false;
                }
            } catch (SQLException ex) {
                System.out.println("Error: " + ex.getMessage());
            } finally {
                db.releaseConnection();
            }
        }
        return exists; //default to true = user exists
    }

    /**
     * A helper method to pull the account from the database
     *
     * @param studentID The id of the corresponding record in the db
     */
    private void getStudentAccountFromDb(BigInteger studentID) {

        this.studentID = studentID; //to make sure it's set
        DbUtilities db = new DbUtilities();
        ResultSet rs;
        String sql = "SELECT * FROM srr.student_account WHERE studentID = " + studentID;
        try {
            rs = db.getResultSet(sql);
            if (rs.next()) {
                this.userName = rs.getString("userName");
                this.fName = rs.getString("fName");
                this.lName = rs.getString("lName");
                this.email = rs.getString("email");
                this.phone = rs.getString("phone");
                this.addressLine1 = rs.getString("addressLine1");
                this.addressLine2 = rs.getString("addressLine1");
                this.stateID = rs.getInt("stateID");
                this.city = rs.getString("city");
                this.ZIP = rs.getString("ZIP");
                this.classLevelID = rs.getInt("classLevelID");
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

    }

    /**
     * A method to return this object as JSON
     *
     * @return This object's properties as a JSON string
     */
    public JSONObject getStudentAccountAsJSON() {

        JSONObject studentJSON = new JSONObject();
        JSONArray jsonEducationList = new JSONArray();

        try {
            studentJSON.put("userName", this.userName);
            studentJSON.put("studentID", this.studentID);
            studentJSON.put("lName", this.lName);
            studentJSON.put("fName", this.fName);
            studentJSON.put("email", this.email);
            studentJSON.put("stateID", this.stateID);
            studentJSON.put("zip", this.ZIP);

            //  for (Education tempEd : getEducationList()) {
            //      jsonEducationList.put(tempEd.getEducationAsJSON());
            //  }
            studentJSON.put("EducationList", jsonEducationList); //add the education list
        } catch (JSONException ex) {
            //need to work on error logging (a consistent method): not priority
            System.out.println("Error in getStudentAccountAsJSON of StudentAccount: " + ex.getMessage());
        }

        return studentJSON;

    }

    /**
     * A method to obtain this student account's education from the database
     *
     * @return A list of Education objects related to this instance
     */
    private ArrayList<Education> getEducationList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //SETTERS **************
    public void setStudentID(BigInteger id) {
        this.studentID = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String psw) {
        this.password = psw;
    }

    public void setFirstName(String fname) {
        this.fName = fname;
    }

    public void setLastName(String lname) {
        this.lName = lname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public void setStateID(int id) {
        this.stateID = id;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZIP(String zip) {
        this.ZIP = zip;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setClassLevelID(int id) {
        this.classLevelID = id;
    }
    //GETTERS **********

    public BigInteger getStudentID() {
        return this.studentID;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getFirstName() {
        return this.fName;
    }

    public String getLastName() {
        return this.lName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getAddressLine1() {
        return this.addressLine1;
    }

    public String getAddressLine2() {
        return this.addressLine2;
    }

    public int getStateID() {
        return this.stateID;
    }

    public String getCity() {
        return this.city;
    }

    public String getZIP() {
        return this.ZIP;
    }

    public String getPhoneNumber() {
        return this.phone;
    }

    public int getClassLevelID() {
        return this.classLevelID;
    }

    /**
     * A method to obtain the name of the state for this account
     *
     * @return The name of the State as a String
     */
    public String getStateName() {
        String sql = "SELECT name FROM srr.state_lookup WHERE stateID=" + this.stateID;
        ResultSet rs;
        String name = "";
        db = new DbUtilities();
        try {
            rs = db.getResultSet(sql);
            if (rs.next()) {
                name = rs.getString("name");
            }

        } catch (SQLException ex) {
            System.out.println("Error in getStateName in StudentAcccount: " + ex.getMessage());
        }
        return name;
    }

    /**
     * A method to obtain the abbreviation of the State related to this account
     *
     * @return The abbreviation of the State as a String
     */
    public String getStateAbbreviation() {
        String sql = "SELECT abbreviation FROM srr.state_lookup WHERE stateID=" + this.stateID;
        ResultSet rs;
        String abbr = "";
        db = new DbUtilities();
        try {
            rs = db.getResultSet(sql);
            if (rs.next()) {
                abbr = rs.getString("abbreviation");
            }
        } catch (SQLException ex) {
            System.out.println("Error in getStateName in StudentAcccount: " + ex.getMessage());
        } finally {
            db.releaseConnection();
        }
        return abbr;
    }

}
