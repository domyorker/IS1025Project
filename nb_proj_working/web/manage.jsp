
<%@page import="java.util.Set"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.math.BigInteger"%>
<%@page import="srr.model.Resume"%>
<%@page import="srr.model.StudentAccount"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title> SRR: [Resume Creation] </title>
        <meta name="Author" content="Jose Marte">
        <link rel="stylesheet" href="css/jquery-ui.css">
        <script src="js/jquery-2.1.0.min.js"></script>
        <script src="js/jquery-ui.min.js"></script>
        <link rel="stylesheet" href="css/ice_blue.css" type="text/css" media="screen,projection,print">	<!--// Document Style //-->
        <script>
            function toggleVisibility(elemID, strToggle) {
                var elem = document.getElementById(elemID);
                if (elem) {
                    elem.className = strToggle;
                }
            }
            function showNextSkill(n) {
                if (n > 10) { //10 is the max
                    return;
                }
                toggleVisibility("trSkill_" + n, "shown");
                $("#btnAddSkill").attr("onclick", "showNextSkill(" + (n + 1) + ")");
            }

        </script>
    </head>

    <body>
        <div id="wrapper">
            <div id="masthead">
                <div id="logoDIV"><img src="img/logos/srr-banner.jpg" alt="School of Information Science Logo" /></div>
                <div id="loginStatus">
                    <%
                        StudentAccount userAccount = (StudentAccount) session.getAttribute("StudentAccount");
                        if (userAccount != null) {
                            out.print("Logged in as " + userAccount.getUserName());
                            out.print(" | <a href='logout'>Logout</a>");
                        } else {
                            response.sendRedirect(response.encodeRedirectURL("login.jsp"));
                            return;
                        }
                    %>

                </div>
            </div>
            <div class="clear"></div><!--important-->
            <div id="nav">
                <ul>
                    <li id="navHomeLink">
                        <a href="index.jsp">
                            Home
                        </a>
                    </li>
                    <li id="navAuthorLink">
                        <a href="manage.jsp">
                            Manage Resumes
                        </a>
                    </li>
                    <li id="navSearchLink">
                        <a href="search.jsp">
                            Search Resumes
                        </a>
                    </li>
                    <li id="navBrowseLink">
                        <a href="browse.jsp">
                            Browse Gallery
                        </a>
                    </li>
                    <li id="navRegisterLink">
                        <a href="register.jsp">
                            Register
                        </a>
                    </li>
                    <li id="navLoginLink">
                        <a href="login.jsp">
                            Login
                        </a>
                    </li>
                </ul>
            </div>
            <div id="main">
                <h1 class="page-title">
                    Resume Management
                </h1>
                <div id="errorDIV">
                    <% //check for the existence of errors if this is a redirect
                        String isErrorRedirect = request.getParameter("error");
                        if (isErrorRedirect != null && isErrorRedirect.equals("true")) {
                            String errorList = (String) session.getAttribute("errorList");
                            if (errorList != null && !errorList.isEmpty()) {
                                out.println("<h3 class='title'>Errors found...</h3>");
                                out.println(errorList);
                            }
                        }
                    %>
                </div>                                         
                <form id="frmAuthor" name="frmAuthor" action="edit?action=create" method="post">
                    <table>
                        <tr>
                            <td><input type="text" id="txtResumeName" name="txtResumeName"/></td>
                            <td><button id="btnCreateResume" name="btnCreateResume" type="submit">Create new Resume</button></td>
                        </tr>
                    </table>
                </form>
                <table>
                    <tr><th colspan="2"><h2 class="page-sub-title">Your Resumes</h2></th></tr>
                    <tr><td colspan="2">                 <div id="divResumes">
                                <%
                                    out.println("<ul id='resumeList'>");
                                    HashMap list = userAccount.getResumeList();
                                    BigInteger tempID;
                                    String tempTitle;

                                    try {
                                        if (list != null) {
                                            //borrowed from http://www.tutorialspoint.com/java/java_hashmap_class.htm
                                            // Get a set of the entries
                                            Set set = list.entrySet();
                                            // Get an iterator
                                            Iterator i = set.iterator();
                                            // Display elements
                                            out.println("<ul class='resume-list'>");
                                            while (i.hasNext()) {
                                                Map.Entry me = (Map.Entry) i.next();
                                                tempID = (BigInteger) me.getKey();
                                                tempTitle = (String) me.getValue();
                                                out.println("<li>");
                                                out.println("<a href='view?resumeID=" + tempID.toString() + "'>" + tempTitle + "</a>");
                                                out.println("<a href='edit?action=edit&resumeID=" + tempID.toString() + "'><button>Edit</button></a>");
                                                out.println("</li>");
                                            }
                                            //end borrowed code
                                            out.println("</ul>"); //end the list
                                            session.setAttribute("ResumeList", list); //save the entire list to the db

                                        }

                                    } catch (NullPointerException ex) {
                                        System.out.println("Error trying to generate the list of resumes: " + ex.getMessage());
                                    }
                                %>
                            </div></td></tr>
                </table>

            </div>
            <div class="clear"></div><!--important-->
            <div id="footer">
                <ul>
                    <li>
                        <a href="">
                            This Project is in Beta Stage
                        </a>
                    </li>
                    <li>
                        <a href="">
                            This Project is based on a request for proposal by the School of Information Science at University of Pittsburgh
                        </a>
                    </li>
                    <li>
                        <a href="http://www.ischool.pitt.edu/people/babichenko.php" rel="Project Supervisor"> Project Supervisor: Dmitriy Babichenko</a>
                    </li>
                    <li>
                        <a href="aboutus.jsp#authors">
                            Project Authors: Jose Marte, Steven Schilinski and Sean Carney
                        </a>

                    </li>
                </ul>
            </div> <!--End footer-->
        </div> <!--End wrapper-->
    </body>
</html>