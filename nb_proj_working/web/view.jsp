<%-- 
    Document   : browse
    Created on : Mar 29, 2014, 5:39:22 PM
    Author     : Jose Marte
--%>

<%@page import="srr.model.Reference"%>
<%@page import="srr.model.Education"%>
<%@page import="srr.model.Experience"%>
<%@page import="srr.model.Interest"%>
<%@page import="srr.model.Membership"%>
<%@page import="srr.model.Course"%>
<%@page import="srr.model.Certification"%>
<%@page import="srr.model.Skill"%>
<%@page import="java.util.ArrayList"%>
<%@page import="srr.model.StudentAccount"%>
<%@page import="srr.model.Resume"%>
<%@page import="java.math.BigInteger"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="srr.utilities.DbUtilities"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title> SRR: Browse the Repository</title>
        <meta name="Author" content="Jose Marte" />
        <link rel="stylesheet" href="css/ice_blue.css" type="text/css" media="screen,projection,print" />	<!--// Document Style //-->
        <link rel="stylesheet" href="css/jquery-ui.css" type="text/css" />
        <script language="JavaScript" src="js/jquery-2.1.0.min.js"></script>
        <script language="JavaScript" src="js/jquery-ui.min.js"></script>
        <script>

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
                <%
                    Resume resume = (Resume)session.getAttribute("resume");
                    String userID = resume.getUserResumeID();
                    BigInteger userResumeID = new BigInteger(userID);
                    StudentAccount resumeStudent = new StudentAccount(userResumeID);
                %>
                <h1 class="page-title">
                    Viewing Resume: <i> 
                    <%
                        out.println(resume.getTitle());
                    %>
                    </i>
                </h1>
                <h2 align ="center">
                    <%
                        out.println(resumeStudent.getFirstName() + " " + resumeStudent.getLastName());
                    %>                   
                </h2>
                
                <%
                    out.println(resumeStudent.getAddressLine1() + " " + resumeStudent.getCity() + ", " + resumeStudent.getStateID() + " " + resumeStudent.getZIP() + "&nbsp;&nbsp;&nbsp; Phone: " + resumeStudent.getPhoneNumber() + "&nbsp;&nbsp;&nbsp; Email: " + resumeStudent.getEmail());
                %>
                    
                <h3>Summary</h3>
                <%
                    out.println("Objective: " + resume.getObjective() + "<br/>");
                    out.println("Accomplishment: " + resume.getAccomplishments() + "<br/>");
                    out.println("Experience: " + resume.getExperience() + "<br/>");
                %>
                <h3>Education</h3>
                    <ul>
                    <%
                        ArrayList<Education> educationList = resume.getEducationList();       
                        if (educationList != null){
                            for (int i = 0; i < educationList.size(); i++){
                                out.println("<h4 style='text-align:left;float:left;'>" + educationList.get(i).getInstitution() + ", " + educationList.get(i).getCity() + "</h4>" + "<h4 style='text-align:right;float:right;'>" + educationList.get(i).getGraduationMonth() + "/" + educationList.get(i).getGraduationYear() + "</h4>");
                                out.println("<br/><br/>");
                                out.println(educationList.get(i).getDegree());
                            }
                        }
                    %>
                    </ul>
                <h3>Experience</h3>
                    <ul>
                    <%
                        ArrayList<Experience> experienceList = resume.getExperienceList();       
                        if (experienceList != null){
                            for (int i = 0; i < experienceList.size(); i++){
                                out.println("<li>" + experienceList.get(i).getEmployer() + "</li>");
                            }
                        }
                    %>
                    </ul>
                <h3>Skills</h3>
                    <ul>
                    <%
                        ArrayList<Skill> skillList = resume.getSkillsList();
                        if (skillList != null){
                            for (int i = 0; i < skillList.size(); i++){
                                out.println("<li>" + skillList.get(i).getName() + "</li>");
                            }
                        }

                    %>
                    </ul>
                <h3>Courses</h3>
                    <ul>
                    <%
                        ArrayList<Course> courseList = resume.getCoursesList();       
                        if (courseList != null){
                            for (int i = 0; i < courseList.size(); i++){
                                out.println("<li>" + courseList.get(i).getName() + "</li>");
                            }
                        }
                    %>
                    </ul>
                <h3>Certifications</h3>
                    <ul>
                    <%
                        ArrayList<Certification> certificationList = resume.getCertificationsList();       
                        if (certificationList != null){
                            for (int i = 0; i < certificationList.size(); i++){
                                out.println("<h4 style='text-align:left;float:left;'>" + certificationList.get(i).getInstitution() + "</h4>" + "<h4 style='text-align:right;float:right;'>" + certificationList.get(i).getDateAttained() + "</h4>");
                                out.println("<br/><br/>");
                                out.println("<li>" + certificationList.get(i).getName() + "</li>");
                                out.println("<li>" + certificationList.get(i).getSummary() + "</li>");
                            }
                        }
                    %>
                    </ul>
                
                <h3>Memberships</h3>
                    <ul>
                    <%
                        ArrayList<Membership> memberList = resume.getMembershipsList();       
                        if (memberList != null){
                            for (int i = 0; i < memberList.size(); i++){
                                out.println("<li>" + memberList.get(i).getName() + "</li>");
                            }
                        }
                    %>
                    </ul>
                
                <h3>Interests</h3>
                    <ul>
                    <%
                        ArrayList<Interest> interestList = resume.getInterestsList();       
                        if (interestList != null){
                            for (int i = 0; i < interestList.size(); i++){
                                out.println("<li>" + interestList.get(i).getName() + "</li>");
                            }
                        }
                    %>
                    </ul>
                
                <h3>Reference</h3>
                    <ul>
                    <%
                        String id = String.valueOf(resume.getResumeID());
                        Reference reference = new Reference(id);  
                        out.println(reference.getTitle() + "<br/>");
                        out.println(reference.getName() + "<br/>");
                        out.println("Email: " + reference.getEmail() + "<br/>");
                        out.println("Phone: " + reference.getPhone() + "<br/>");
                    %>
                    </ul>
                <h3>Other</h3>
                    <%
                        /*
                        out.println("ExperienceList: " + resume.getExperienceList() + "<br/>");
                        out.println("Education List: " + resume.getEducationList() + "<br/>");                   
                        out.println("Reference List: " + resume.getReferencesList() + "<br/>");
                        */  
                    %>
                <div class="clear"></div><!--important-->
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
                            This Project is based on a request for proposal by the School of Information Science at University of Pittsburgh
                        </a>
                    </li>
                    <li>
                        <a href="http://www.ischool.pitt.edu/people/babichenko.php"> Project Supervisor: Dmitriy Babichenko</a>
                    </li>
                    <li>
                        <a href="aboutus.htm#authors">
                            Project Authors: Jose Marte, Steven Schilinski and Sean Carney
                        </a>

                    </li>
                </ul>
            </div> <!--End footer-->
        </div> <!--End wrapper-->
    </body>
</html>
