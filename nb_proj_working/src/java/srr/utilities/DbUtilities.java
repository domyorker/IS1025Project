package srr.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Provides methods for: 1. Retrieving data sets from MySQL databases. 2. For
 * executing UPDATE, INSERT, DELETE queries 3. For building tables to populate
 * data grids (JTable)
 *
 * @author Dmitriy Babichenko
 * @version 1.1
 */
public class DbUtilities {

    private Connection conn = null; // connection object
    private String hostName = "127.0.0.1:3306"; // server address + port number
    private String dbName = "srr"; // default database name
    private String dbUserName = "root"; // login name for the database server
    private String dbPassword = "coffee#79"; // password for the database server

    /**
     * Default constructor creates a connection to database at the time of
     * instantiation.
     */
    public DbUtilities() {
        createDbConnection();
    }

    /**
     * Alternate constructor - use it to connect to any MySQL database
     *
     * @param hostName - server address/name of MySQL database
     * @param dbName - name of the database to connect to
     * @param dbUserName - user name for MySQL database
     * @param dbPassword - password that matches dbUserName for MySQL database
     */
    public DbUtilities(String hostName, String dbName, String dbUserName, String dbPassword) {
        // Set class-level (instance) variables
        this.hostName = hostName;
        this.dbName = dbName;
        this.dbUserName = dbUserName;
        this.dbPassword = dbPassword;
        // Create new database connection
        createDbConnection();
    }

    /**
     * Creates database connection using credentials stored in class variables.
     * Connection to database is the most resource-consuming part of the
     * database transaction. That's why we create a connection once when the
     * object is instantiated and keep it alive through the life of this object.
     * Note that this is a private method and cannot be accessed from outside of
     * this class.
     */
    private void createDbConnection() {
        try {
            // Build connection string
            String mySqlConn = "jdbc:mysql://" + hostName + "/" + dbName + "?user=" + dbUserName + "&password=" + dbPassword;
            // Instantiate the MySQL database connector driver
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            // Connect to the database
            conn = DriverManager.getConnection(mySqlConn);
        } catch (Exception e) {
            System.err.print(e.toString());
            System.err.println("Unable to connect to database");
        }
    }

    /**
     * Get SQL result set (data set) based on an SQL query
     *
     * @param sql - SQL SELECT query
     * @return - ResultSet - java.sql.ResultSet object, contains results from
     * SQL query argument
     * @throws SQLException
     */
    public ResultSet getResultSet(String sql) throws SQLException {

        if (conn == null) { // Check if connection object already exists
            createDbConnection(); // If does not exist, create new connection
        }
        Statement statement = conn.createStatement();
        return statement.executeQuery(sql); // Return ResultSet
    }

    /**
     * Executes INSERT, UPDATE, DELETE queries
     *
     * @param sql - SQL statement - a well-formed INSERT, UPDATE, or DELETE
     * query
     * @return true if execution succeeded, false if failed
     * @throws java.sql.SQLException
     */
    public boolean executeQuery(String sql) throws SQLException {
        if (conn == null) {
            createDbConnection();
        }
        Statement statement = conn.createStatement();
        statement.executeUpdate(sql); // execute query
        return true; //success
    }

    /**
     * This method converts a ResultSet into a JSON object
     *
     * @param sqlQuery - an SQL query - we need to get a data set from a
     * database and convert it to JSON
     * @return JSON object
     * @throws SQLException
     * @throws JSONException
     */
    public JSONArray getJsonDataTable(String sqlQuery) throws SQLException, JSONException {
        ResultSet rs = this.getResultSet(sqlQuery);
        /* 
         * Create new JSON object.  Note that you will need to download and install java-json library
         * http://www.java2s.com/Code/JarDownload/java/java-json.jar.zip
         */
        JSONArray json = new JSONArray();

        // We need the metadata object to get each database field's data type
        ResultSetMetaData rsmd = rs.getMetaData();

        /*
         * Loop through the ResultSet.  For each value, we need to get corresponding field's data type
         */
        while (rs.next()) {
            int numColumns = rsmd.getColumnCount();
            JSONObject obj = new JSONObject();

            for (int i = 1; i < numColumns + 1; i++) {
                String column_name = rsmd.getColumnName(i);

                if (rsmd.getColumnType(i) == java.sql.Types.ARRAY) {
                    obj.put(column_name, rs.getArray(column_name));
                } else if (rsmd.getColumnType(i) == java.sql.Types.BIGINT) {
                    obj.put(column_name, rs.getInt(column_name));
                } else if (rsmd.getColumnType(i) == java.sql.Types.BOOLEAN) {
                    obj.put(column_name, rs.getBoolean(column_name));
                } else if (rsmd.getColumnType(i) == java.sql.Types.BLOB) {
                    obj.put(column_name, rs.getBlob(column_name));
                } else if (rsmd.getColumnType(i) == java.sql.Types.DOUBLE) {
                    obj.put(column_name, rs.getDouble(column_name));
                } else if (rsmd.getColumnType(i) == java.sql.Types.FLOAT) {
                    obj.put(column_name, rs.getFloat(column_name));
                } else if (rsmd.getColumnType(i) == java.sql.Types.INTEGER) {
                    obj.put(column_name, rs.getInt(column_name));
                } else if (rsmd.getColumnType(i) == java.sql.Types.NVARCHAR) {
                    obj.put(column_name, rs.getNString(column_name));
                } else if (rsmd.getColumnType(i) == java.sql.Types.VARCHAR) {
                    obj.put(column_name, rs.getString(column_name));
                } else if (rsmd.getColumnType(i) == java.sql.Types.TINYINT) {
                    obj.put(column_name, rs.getInt(column_name));
                } else if (rsmd.getColumnType(i) == java.sql.Types.SMALLINT) {
                    obj.put(column_name, rs.getInt(column_name));
                } else if (rsmd.getColumnType(i) == java.sql.Types.DATE) {
                    obj.put(column_name, rs.getDate(column_name));
                } else if (rsmd.getColumnType(i) == java.sql.Types.TIMESTAMP) {
                    obj.put(column_name, rs.getTimestamp(column_name));
                } else {
                    obj.put(column_name, rs.getObject(column_name));
                }
            }

            json.put(obj);
        }

        return json;

    }

    /**
     *
     * @param sqlQuery
     * @param tableID
     * @return
     * @throws SQLException
     */
    public String getHtmlDataTable(String sqlQuery, String tableID) throws SQLException {
        ResultSet rs = this.getResultSet(sqlQuery);
        ResultSetMetaData metaData = rs.getMetaData();
        String tbl = "<table id='" + tableID + "'><tr>";
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            tbl += "<td>" + metaData.getColumnName(column) + "</td>";
        }
        tbl += "</tr>";

        while (rs.next()) {
            tbl += "<tr>";
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                tbl += "<td>" + rs.getString(columnIndex) + "</td>";
            }
            tbl += "</tr>";
        }
        return tbl;
    }
/**
 * Author: Jose Marte
 * A method to release the connection once done with it
 */
    public void releaseConnection() {
        try {
            conn.close();
        } catch (SQLException ex) {
           System.err.println("Error attempting to release the connection: " + ex.getSQLState());
        }
       // conn = null;
    }
}
