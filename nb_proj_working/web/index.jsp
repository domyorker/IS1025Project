<%-- 
    Document   : index
    Created on : Mar 29, 2014, 5:31:31 PM
    Author     : Jose Marte
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title> SRR: [ Home ] </title>
        <meta name="Author" content="Jose Marte">
        <link rel="stylesheet" href="css/ice_blue.css" type="text/css" media="screen,projection,print">	<!--// Document Style //-->
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
                <h1>
                    Welcome
                </h1>
                <span id="employer-note" class="float-right">
                    Are you looking for talent? You may search our r�sum� database for keywords, programming language, and education level.
                    You may also browse our collection interactively.
                    We encourage you to also rate, post comments and bookmark any r�sum�.<br />  <a class="float-right" href="aboutus.jsp">Learn more...</a>
                </span><div class="clear"></div>
                <span id="faculty-note" class="float-right">
                    Ready to help bridging the future? All faculty have access to resources in this r�sum� repository. Your membership and contribution in this project are vital to its success. <br /><a class="float-right" href="aboutus.jsp">Learn more...</a>
                </span><div class="clear"></div>
                <span id="student-note" class="float-right">
                    This project would not mean anything without you, the student. Post your r�sum�. Build it and tweak it until you're satisfied.<br /><a class="float-right" href="aboutus.jsp">Learn more...</a>
                </span><div class="clear"></div>
            </div>
            <div class="clear"></div><!--important-->
            <div id="footer">
                <ul>
                    <li>
                        <a href="">
                            This Project is in Preliminary Stage
                        </a>
                    </li>
                    <li>
                        <a href="">
                            This Project is based on a request for proposal by the School of Information Science at University of Pittsburgh.
                        </a>
                    </li>
                    <li>
                        <a href="http://www.ischool.pitt.edu/people/babichenko.php">
                            Project Supervisor:  Dmitriy Babichenko
                        </a>
                    </li>
                    <li>
                        <a href="aboutus.jsp#author">
                            Project Authors: Jose Marte, Steven Schilinski and Sean Carney
                        </a>
                    </li>
                </ul>
            </div> <!--End footer-->
        </div> <!--End wrapper-->
    </body>
</html>