/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package srr.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import srr.utilities.DbUtilities;

/**
 * A service to pull the grade levels from the db; pulls one grade level when passed gradeLevelID
 * @author Jose Marte
 */
@WebServlet(name = "getGradeLevels", urlPatterns = {"/rest/getGradeLevels"})
public class getGradeLevels extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String sql = "SELECT * FROM srr.class_level";
            String levelID = request.getParameter("levelID");
            if (levelID != null) {
                sql += " WHERE levelID = '" +levelID + "'";
            }
            //create the json
            JSONObject level;
            JSONArray levels = new JSONArray();
            DbUtilities db = new DbUtilities();
            ResultSet rs;
            try {
                rs = db.getResultSet(sql);
                while (rs.next()) {
                    level = new JSONObject();
                    level.put("levelID", rs.getString("levelID"));
                    level.put("levelName", rs.getString("levelName"));
                    levels.put(level);
                }
                //now output the json array
                out.print(levels);
            } catch (SQLException | JSONException ex) {
                Logger.getLogger(getStates.class.getName()).log(Level.SEVERE, null, ex);
                System.out.print("Error: " + ex.getMessage()); //***********************
            } finally {
                db.releaseConnection();
            }
        }
    }

    // <editor-fold defaultlevel="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
