<%-- 
    Document   : index
    Created on : Mar 29, 2014, 5:31:31 PM
    Author     : Jose Marte
--%>

<%@page import="srr.model.StudentAccount"%>
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
                    <%                        StudentAccount userAccount = null;
                        if (session.getAttribute("StudentAccount") != null) {
                            userAccount = (StudentAccount) session.getAttribute("StudentAccount");
                            out.print("Logged in as " + userAccount.getUserName());
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
                <h1 class="page-title">
                    Welcome
                </h1>

                <h2 class="page-sub-title">
                    About the Site
                </h2>

                <p>This site is a place where students can create resumes, employers can view them, and faculty can help to facilitate the process between the two.  Students have the ability to create a whole, functioning resume directly on this repository.  Employers can browse the gallery containing a list of all prospective students, or they can use the search feature to look up only students with certain requirements, such as GPA, programming languages, and education level.  This repository can make the resume creating process extremely simply for the student, and also make hiring available candidates much easier for companies. 
                    <br/><a href="about.jsp">Learn more...</a><br/>
                </p>

                <p>Our site allows students to submit a resume for potential employers to view.  It can be created and updated simply and all in one place, right here on our website. This project would not mean anything without you, the student. Post your resume. Build it and tweak it until you're satisfied.  Once your resume is created, it will be able to be accessed by potential employers looking for talent.  All that you have to do is register to get started.  Once you are registered, simply login and begin creating your resume.
                    <br/><a href="register.jsp">Register here</a><br/>
                    <a href="login.jsp">Login here</a><br/>
                    After registering, students can create a resume using our resume management.  It offers a simple way to enter in all of your necessary information that can be found on a resume.  It enables you to enter information in about your previous work experience, your education level, skills, any memberships that you may have, interests, awards, and certifications that you have completed.
                    <br/><a href="manage.jsp">Manage Resumes</a><br/>
                </p>
                <h2 class="page-sub-title">
                    About the Project
                </h2>

                <p>This project contains five different screens, including a login, user registration, resume creation, resume search, and resume view screen.  The infrastructure includes a database, project code, an HTML5 compliant user interface, information returned as JSON, a class diagram, and an ER Model from MySQL Workbench, with the final project complying with the model-view-controller pattern.
                </p>
                <div class="clear"></div>
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