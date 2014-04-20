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
 * A service to return a state (when stateID is supplied) or list of all states
 * as a JSON array
 *
 * @author Jose Marte
 */
@WebServlet(name = "getStates", urlPatterns = {"/rest/getStates"})
public class getStates extends HttpServlet {

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
            String sql = "SELECT * FROM srr.state_lookup";
            String stateID = request.getParameter("stateID");
            if (stateID != null) {
                sql += " WHERE stateID = '" + stateID + "'";
            }
            //create the json
            JSONObject state;
            JSONArray states = new JSONArray();
            DbUtilities db = new DbUtilities();
            ResultSet rs;
            try {
                rs = db.getResultSet(sql);
                while (rs.next()) {
                    state = new JSONObject();
                    state.put("stateID", rs.getString("stateID"));
                    state.put("name", rs.getString("name"));
                    state.put("abbreviation", rs.getString("abbreviation"));
                    states.put(state);
                }
                //now output the json array
                out.print(states);
            } catch (SQLException | JSONException ex) {
                Logger.getLogger(getStates.class.getName()).log(Level.SEVERE, null, ex);
                System.out.print("Error in getStates.java: " + ex.getMessage()); //***
            } finally {
                db.releaseConnection();
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
