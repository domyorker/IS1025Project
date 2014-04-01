
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
                        //Protected page...confirm login...
                        String userName = (String) session.getAttribute("userName");
                        if (userName != null) {
                            out.print("Logged in as " + userName);
                            out.print(" | <a href='logout'>Logout</a>");
                        } else {
                            //not logged in...
                            response.sendRedirect("login.jsp");
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
                <div id="divMessages">
                    <%
                        String msg = request.getParameter("msg");
                        if (msg != null && !msg.isEmpty()) {
                            out.println("<p class='info'>" + msg + "</p>");
                        }
                    %>
                </div>
                <form id="frmAuthor" name="frmAuthor" action="Manage?action=addResume" method="post">
                    <table>
                        <tr><th colspan="2">Your Resumes</th></tr>
                        <tr><td colspan="2">                 <div id="divResumes">
                                    <%
                                        out.print("Getting your resumes....");

                                        //  String strCurrentResume = (String) session.getAttribute("currentResumeID");
                                        //go through list of resumes: simulated for now. Need to query db through 
                                        out.println("<ul id='resumeList'>");
                                        for (int i = 1; i <= 10; i++) {
                                            out.println("<li>");
                                            out.println("<a href='Manage?action=editResume&resumeID=" + i + "'>Simulated [dataset] Resume #" + i + "</a>");
                                            out.println("</li>");
                                        }
                                        out.println("</ul>"); //end the list
%>
                                </div></td></tr>
                        <tr><td><input type="text" id="txtResumeName" name="txtResumeName"/></td><td><button id="btnCreateResume" name="btnCreateResume" type="submit">Create new Resume</button></td></tr>
                    </table>
                </form>
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