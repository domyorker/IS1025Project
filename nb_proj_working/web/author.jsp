<%-- 
    Document   : author
    Created on : Mar 29, 2014, 5:06:30 AM
    Author     : Jose Marte
--%>
<% //ensure resume data is in session object, if not redirect
    String resumeID = (String) session.getAttribute("resumeID");
    if (resumeID == null || resumeID.isEmpty()) {
        response.sendRedirect("manage.jsp");
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html lang="en">
    <head>
        <title> SRR: Resume Creation </title>
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
                        String userName = (String) session.getAttribute("userName");
                        if (userName != null) {
                            out.print("Logged in as " + userName);
                            out.print(" | <a href='logout'>Logout</a>");
                        } else {
                            out.print("<a href='login.jsp'>Login</a> | <a href='register.jsp'>Register</a>");
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
                        <a href="author.jsp">
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
                    Editing: <i> 
                        <%
                            out.println(session.getAttribute("resumeTitle"));
                        %></i> 
                </h1>
                <form id="frmAuthor" name="frmAuthor" action="author" method="post">

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
