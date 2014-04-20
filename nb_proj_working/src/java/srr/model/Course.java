package srr.model;

/**
 * A class to represent a course
 *
 * @author Sean Carney
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import srr.utilities.DbUtilities;


public class Course {
    
    private String courseID;
    private String name;
    
    public Course(String courseID)
    {
        this.courseID = courseID;
        DbUtilities db = new DbUtilities();
        try {
           ResultSet rs = db.getResultSet("Select * From srr.course where"
                    + "  srr.course.courseID ='"+ this.courseID + "'" );
            while(rs.next())
            {
             String tmp =   rs.getString("name");
             if(tmp!=null)
             {
                 this.name = tmp;
             }
             
            }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            db.releaseConnection();
        }
        
    }
    
    public Course()
    {
      
    }
    
    /**
     *
     * @param name
     */
    public void addCourse(String name)
    {
         DbUtilities db = new DbUtilities();
        try {
            db.executeQuery("Insert into srr.course (null.'"+ name+"');");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public String getName(){
        return this.name;
    }
}
