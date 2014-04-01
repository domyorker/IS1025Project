<%-- 
    Document   : about
    Created on : Mar 29, 2014, 5:39:22 PM
    Author     : Jose Marte
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title> SRR: [ Home ] </title>
        <meta name="Author" content="Jose Marte">
        <link rel="stylesheet" href="css/ice_blue.css" type="text/css" media="screen,projection,print">	<!--// Document Style //-->
        <!--link rel="stylesheet" href="index_style.css" type="text/css" media="screen,projection,print">	<!--// Page Style //-->
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
                    About this Project and its Audience
                </h1>
                <h2 class="page-sub-title" id="employer">Employers</h2>
                <span>
                    <p>
                        Are you looking for talent? You may search our resume database for keywords, programming language, and education level.
                        You may also browse our collection interactively. See talent you are interested in? Click on the contact link at the top of the resume.
                    </p>
                </span>
                <h2 class="page-sub-title" id="faculty">Faculty</h2>
                <span>
                    <p>
                        Ready to help bridging the future? All faculty have access to resources in this resume repository. Your membership and contribution in this project are vital to its success.
                    </p>
                </span>
                <h2 class="page-sub-title" id="student">Students</h2>
                <span>
                    <p>
                        This project would not mean anything without you, the student. Post your resume. Build it and tweak it until you're satisfied.
                    </p>
                </span>
                <h3 class="page-sub-title" id="author">Meet the Authors</h3>
                Jose Marte<br />
                Steven Schilinski<br />
                Sean Carney


            </div>
            <div class="clear"></div>
            <div id="footer">
                <ul>
                    <li>
                        <a href="">
                            This Project is in Beta Stage
                        </a>
                    </li>
                    <li>
                        <a href="">
                            This Project is based on a request for proposal by the School of Information Science at University of Pittsburgh.
                        </a>
                    </li>
                    <li>
                        Project Supervisor: <a href="http://www.ischool.pitt.edu/people/babichenko.php"> Dmitriy Babichenko</a>
                    </li>
                    <li>
                        <a href="aboutus.jsp">
                            Project Authors: Jose Marte, Steven Schilinski and Sean Carney
                        </a>
                    </li>
                </ul>
            </div> <!--End footer-->
        </div> <!--End wrapper-->
    </body>
</html>