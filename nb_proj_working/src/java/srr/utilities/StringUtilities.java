/**
 * This class handles general utilities and operations involving strings
 *
 * @author Dmitriy Babichenko
 * @version 1.1
 */
package srr.utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StringUtilities {

    /**
     * Takes current date, separates month, day, and year with underscores and
     * adds a file extension
     *
     * @param extension - Extension of a file, usually a .txt
     * @return File name
     */
    public static String dateToFilename(String extension) {
        Date tempDate = new Date();
        return tempDate.getMonth() + "_" + tempDate.getDay() + "_" + tempDate.getYear() + "." + extension;
    }

    /**
     * Checks if a String varialbe consists only of numbers. For example, if a
     * String variable contains "1234" (only numbers), this method will return
     * "true". If a String variable contains even one non-numeric character -
     * "123x4" - this method will return false.
     *
     * @param str - String variable - we need to check if it can be converted to
     * a number without an error
     * @return boolean (true/false)
     */
    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Replaces dangerous characters in SQL strings to prevent injections
     *
     * @param data - a string parameter used to concatenate SQL queries
     * @return String with dangerous characters escaped or replaced
     */
    public static String cleanMySqlInsert(String data) {
        if (data.isEmpty()) {
            return "";
        }
        String cleanData = data.replace("  ", " ");
        //cleanData = cleanData.replace("\\", "\\\\");
        cleanData = cleanData.replace("'", "\\'");
        return cleanData;
    }

    /**
     * Encodes password using MD5 encryption
     *
     * @param password - user-provided password
     * @return encoded string
     */
    public static String encodePassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());

            byte byteData[] = md.digest();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(StringUtilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    /**
     * Checks if a UUID (a globally unique identifier - GUID) is in correct and
     * valid format
     *
     * @param guid - a UUID in string format - the value that we need to
     * validate
     * @return true if valid, false if invalid
     */
    public static boolean isValidGUID(String guid) {
        try {
            UUID uuid = UUID.fromString(guid);
            if (guid.equals(uuid.toString())) {
                return true;
            }
        } catch (IllegalArgumentException ex) {
            return false;
        }
        return false;
    }

    /**
     * A helper method to properly format a date b4 submission to MySql
     *
     * @param date The date to format
     * @return A formatted Date String as ("yyyy-MM-dd")
     */
    public static String getFormattedDate(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    /**
     * A helper method to parse date strings to MySql-formatted Dates
     *
     * @param strDate the date as a string in the format yyyy-MM-dd
     * @return The string converted into a Date object in the format yyyy-MM-dd
     * @throws java.text.ParseException
     */
    public static Date parseStringToMySqlDate(String strDate) throws ParseException {
        SimpleDateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd");
        return readFormat.parse(strDate);
    }

    /**
     * A helper method to parse date strings to MySql-formatted Dates
     *
     * @param strDate the date as a string in the format MM/dd/yyyy
     * @return The string converted into a Date object
     * @throws java.text.ParseException
     */
    public static String parseDateString(String strDate) throws ParseException {
        if (strDate.isEmpty()) {
            return "";
        }
        SimpleDateFormat readFormat = new SimpleDateFormat("MM/dd/yyyy"); //input from web page
        SimpleDateFormat writeFormat = new SimpleDateFormat("yyyy-MM-dd"); //output for MySql

        Date date;
        String formattedDate = "";

        date = readFormat.parse(strDate);
        if (date != null) {
            formattedDate = writeFormat.format(date);
        }
        return formattedDate;
    }
    public static String parseMySqlDateString(String strDate) throws ParseException {
        if (strDate.isEmpty()) {
            return "";
        }
        SimpleDateFormat writeFormat = new SimpleDateFormat("MM/dd/yyyy"); //input from web page
        SimpleDateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd"); //output for MySql

        Date date;
        String formattedDate = "";

        date = readFormat.parse(strDate);
        if (date != null) {
            formattedDate = writeFormat.format(date);
        }
        return formattedDate;
    }
}
