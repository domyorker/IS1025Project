<%-- 
    Document   : index
    Created on : Mar 29, 2014, 5:31:31 PM
    Author     : Jose Marte
--%>

<%@page import="srr.model.Resume"%>
<% //ensure resume data is in session object, if not redirect
    Resume resume = (Resume) session.getAttribute("Resume");
    if (resume == null) {
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
                    Editing: <i> <% out.print(resume.getTitle());%></i>

                </h1>
                <form id="frmAuthor" name="frmAuthor" action="author?action=commit" method="post">
                    <table>
                        <tr><th colspan="2">Create your resume using the form below</th></tr>
                        <tr><td colspan="2" class="page-sub-title">Summary</td></tr>
                        <tr><td>Objective:</td><td><textarea name="txtObjective" id="txtObjective"></textarea></td></tr>
                        <tr><td>Experience:</td><td><textarea name="txtExperience" id="txtExperience"></textarea></td></tr>
                        <tr><td>Accomplishments:</td><td><textarea name="txtAccomplishments" id="txtAccomplishments"></textarea></td></tr>

                        <tr><td colspan="2" class="page-sub-title">Work Experience</td></tr>
                        <tr><td>Job Title:</td><td><input type="text" name="txtJobTitle1" id="txtJobTitle1" placeholder="[ Job Title ]" /></td></tr>
                        <tr><td>Start Date:</td><td><input type="text" name="txtStartDate1" id="txtStartDate1" placeholder="[ Start Date ]" /></td></tr>
                        <tr>
                            <td>Present Job:</td>
                            <td>
                                <input type="radio" name="radioPresentJob1" id="radioPresentJob1" value="Yes" checked="checked" onclick="toggleVisibility('rowEndDate1', 'hidden');">&nbsp;Yes&nbsp;&nbsp;
                                <input type="radio" name="radioPresentJob1" id="radioPresentJob1" value="No" onclick="toggleVisibility('rowEndDate1', 'shown');">&nbsp;No<br />
                            </td>
                        </tr>
                        <tr id="rowEndDate1" class="hidden"><td>End Date:</td><td><input type="text" name="txtEndDate1" id="txtEndDate1" placeholder="[ End Date ]" /></td></tr>
                        <tr><td>Summary of Responsibilities:</td><td><textarea name="txtJobSummary1" id="txtJobSummary1"></textarea></td></tr>

                        <tr><td colspan="2" class="page-sub-title">Skills</td></tr>
                        <tr id="trSkill_1"><td colspan="2"><input name="txtSkill_1" id="txtSkill_1"/></td></tr>
                        <tr  id="trSkill_2" class="hidden"><td colspan="2"><input name="txtSkill_2" id="txtSkill_2" /></td></tr>
                        <tr  id="trSkill_3" class="hidden"><td colspan="2"><input name="txtSkill_3" id="txtSkill_3"/></td></tr>
                        <tr  id="trSkill_4" class="hidden"><td colspan="2"><input name="txtSkill_4" id="txtSkill_4" /></td></tr>
                        <tr  id="trSkill_5" class="hidden"><td colspan="2"><input name="txtSkill_5" id="txtSkill_5"/></td></tr>
                        <tr  id="trSkill_6" class="hidden"><td colspan="2"><input name="txtSkill_6" id="txtSkill_6" /></td></tr>
                        <tr  id="trSkill_7" class="hidden"><td colspan="2"><input name="txtSkill_7" id="txtSkill_7"/></td></tr>
                        <tr  id="trSkill_8" class="hidden"><td colspan="2"><input name="txtSkill_8" id="txtSkill_8" /></td></tr>
                        <tr  id="trSkill_9" class="hidden"><td colspan="2"><input name="txtSkill_9" id="txtSkill_9"/></td></tr>
                        <tr  id="trSkill_10" class="hidden"><td colspan="2"><input name="txtSkill_10" id="txtSkill_10" /></td></tr>
                        <tr><td colspan="2"> <input type="button" id="btnAddSkill" onclick="showNextSkill(2)" value="Add Skill" /></td></tr>                               

                        <tr><td></td><td><input type="submit" id="bntSubmit" name="bntSubmit" value="Submit" /></td></tr>
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
