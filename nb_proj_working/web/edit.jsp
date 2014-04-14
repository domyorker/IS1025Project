<%-- 
    Document   : index
    Created on : Mar 29, 2014, 5:31:31 PM
    Author     : Jose Marte
--%>

<%@page import="srr.model.Skill"%>
<%@page import="java.util.ArrayList"%>
<%@page import="srr.model.StudentAccount"%>
<%@page import="srr.model.Resume"%>
<% //ensure resume data is in session object, if not redirect
    if (session.getAttribute("Resume") == null) {
        response.sendRedirect("manage.jsp");
    }
    Resume resume = (Resume) session.getAttribute("Resume");

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

            function showNextExperience(n) {
                if (n > 5) { //5 is the max
                    return;
                }
                toggleVisibility("WorkExperience" + n, "shown");
                $("#btnAddExperience").attr("onclick", "showNextExperience(" + (n + 1) + ")");
            }

        </script>
    </head>
    <body>
        <div id="wrapper">
            <div id="masthead">
                <div id="logoDIV"><img src="img/logos/srr-banner.jpg" alt="School of Information Science Logo" /></div>
                <div id="loginStatus">
                    <%                        StudentAccount userAccount = (StudentAccount) session.getAttribute("StudentAccount");
                        if (userAccount != null) {
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
                <div id="errorDIV">
                    <% //check for the existence of errors if this is a redirect
                        String isErrorRedirect = request.getParameter("error");
                        if (isErrorRedirect != null && isErrorRedirect.equals("true")) {
                            String msg = (String) session.getAttribute("errorList");
                            out.println(msg);
                        }
                    %>
                </div> <!-- end error div-->
                <div id="successDIV">
                    <% //check for a success msg..
                        String isSuccessRedirect = request.getParameter("success");
                        if (isSuccessRedirect != null && isSuccessRedirect.equals("true")) {
                            String msg = (String) session.getAttribute("successMsg");
                            if (msg != null && !msg.isEmpty()) {
                                out.println(msg);
                            }
                        }

                    %>
                </div>
                <h1 class="page-title">
                    Editing: <i> <% out.print(resume.getTitle());%></i>

                </h1>
                <form id="frmEdit" name="frmEdit" action="edit?action=save" method="post">
                    <div id="summaryDIV">
                        <table>
                            <tr><th colspan="2">Create your resume using the form below</th></tr>
                            <tr><td colspan="2" class="page-sub-title">Title: <input type="text" id="txtResumeName" name="txtResumeName" value="<% out.print(resume.getTitle());%>"/></td></tr>
                            <tr><td colspan="2" class="page-sub-title">Summary</td></tr>
                            <tr><td>Objective:</td><td><textarea name="txtObjective" id="txtObjective"><% out.print(resume.getObjective());%></textarea></td></tr>
                            <tr><td>Experience:</td><td><textarea name="txtExperience" id="txtExperience"><% out.print(resume.getExperience());%></textarea></td></tr>
                            <tr><td>Accomplishments:</td><td><textarea name="txtAccomplishments" id="txtAccomplishments"><% out.print(resume.getAccomplishments());%></textarea></td></tr>
                        </table></div>
                    <div id="divWorkExperience">
                        <h3 class="page-sub-title">Work Experience</h3>
                        <table id="WorkExperience1">
                            <tr><td>Job Title:</td><td><input type="text" name="txtJobTitle1" id="txtJobTitle1" placeholder="[ Job Title ]" /></td></tr>
                            <tr><td>Employer:</td><td><input type="text" name="txtEmployer1" id="txtEmployer1" placeholder="[ Employer ]" /></td></tr>
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
                        </table>
                        <table id="WorkExperience2" class="<% if ("".isEmpty()) {
                                out.print("hidden");
                            } else {
                                out.print("shown");
                            } %>">
                            <tr><td>Job Title:</td><td><input type="text" name="txtJobTitle2" id="txtJobTitle2" placeholder="[ Job Title ]" /></td></tr>
                            <tr><td>Employer:</td><td><input type="text" name="txtEmployer2" id="txtEmployer2" placeholder="[ Employer ]" /></td></tr>
                            <tr><td>Start Date:</td><td><input type="text" name="txtStartDate2" id="txtStartDate2" placeholder="[ Start Date ]" /></td></tr>
                            <tr>
                                <td>Present Job:</td>
                                <td>
                                    <input type="radio" name="radioPresentJob2" id="radioPresentJob2" value="Yes" checked="checked" onclick="toggleVisibility('rowEndDate2', 'hidden');">&nbsp;Yes&nbsp;&nbsp;
                                    <input type="radio" name="radioPresentJob2" id="radioPresentJob2" value="No" onclick="toggleVisibility('rowEndDate2', 'shown');">&nbsp;No<br />
                                </td>
                            </tr>
                            <tr id="rowEndDate2" class="hidden"><td>End Date:</td><td><input type="text" name="txtEndDate2" id="txtEndDate2" placeholder="[ End Date ]" /></td></tr>
                            <tr><td>Summary of Responsibilities:</td><td><textarea name="txtJobSummary2" id="txtJobSummary2"></textarea></td></tr>
                        </table>
                        <table id="WorkExperience3" class="<% if ("".isEmpty()) {
                                out.print("hidden");
                            } else {
                                out.print("shown");
                            } %>">
                            <tr><td>Job Title:</td><td><input type="text" name="txtJobTitle3" id="txtJobTitle3" placeholder="[ Job Title ]" /></td></tr>                            
                            <tr><td>Employer:</td><td><input type="text" name="txtEmployer3" id="txtEmployer3" placeholder="[ Employer ]" /></td></tr>
                            <tr><td>Start Date:</td><td><input type="text" name="txtStartDate3" id="txtStartDate3" placeholder="[ Start Date ]" /></td></tr>
                            <tr>
                                <td>Present Job:</td>
                                <td>
                                    <input type="radio" name="radioPresentJob3" id="radioPresentJob3" value="Yes" checked="checked" onclick="toggleVisibility('rowEndDate3', 'hidden');">&nbsp;Yes&nbsp;&nbsp;
                                    <input type="radio" name="radioPresentJob3" id="radioPresentJob3" value="No" onclick="toggleVisibility('rowEndDate3', 'shown');">&nbsp;No<br />
                                </td>
                            </tr>
                            <tr id="rowEndDate3" class="hidden"><td>End Date:</td><td><input type="text" name="txtEndDate3" id="txtEndDate3" placeholder="[ End Date ]" /></td></tr>
                            <tr><td>Summary of Responsibilities:</td><td><textarea name="txtJobSummary3" id="txtJobSummary3"></textarea></td></tr>
                        </table>
                        <table id="WorkExperience4" class="<% if ("".isEmpty()) {
                                out.print("hidden");
                            } else {
                                out.print("shown");
                            } %>">
                            <tr><td>Job Title:</td><td><input type="text" name="txtJobTitle4" id="txtJobTitle4" placeholder="[ Job Title ]" /></td></tr>                            
                            <tr><td>Employer:</td><td><input type="text" name="txtEmployer4" id="txtEmployer4" placeholder="[ Employer ]" /></td></tr>
                            <tr><td>Start Date:</td><td><input type="text" name="txtStartDate4" id="txtStartDate4" placeholder="[ Start Date ]" /></td></tr>
                            <tr>
                                <td>Present Job:</td>
                                <td>
                                    <input type="radio" name="radioPresentJob4" id="radioPresentJob4" value="Yes" checked="checked" onclick="toggleVisibility('rowEndDate4', 'hidden');">&nbsp;Yes&nbsp;&nbsp;
                                    <input type="radio" name="radioPresentJob4" id="radioPresentJob4" value="No" onclick="toggleVisibility('rowEndDate4', 'shown');">&nbsp;No<br />
                                </td>
                            </tr>
                            <tr id="rowEndDate4" class="hidden"><td>End Date:</td><td><input type="text" name="txtEndDate4" id="txtEndDate4" placeholder="[ End Date ]" /></td></tr>
                            <tr><td>Summary of Responsibilities:</td><td><textarea name="txtJobSummary4" id="txtJobSummary4"></textarea></td></tr>
                        </table>
                        <table id="WorkExperience5" class="<% if ("".isEmpty()) {
                                out.print("hidden");
                            } else {
                                out.print("shown");
                            } %>">
                            <tr><td>Job Title:</td><td><input type="text" name="txtJobTitle5" id="txtJobTitle5" placeholder="[ Job Title ]" /></td></tr>
                            <tr><td>Employer:</td><td><input type="text" name="txtEmployer5" id="txtEmployer5" placeholder="[ Employer ]" /></td></tr>
                            <tr><td>Start Date:</td><td><input type="text" name="txtStartDate5" id="txtStartDate5" placeholder="[ Start Date ]" /></td></tr>
                            <tr>
                                <td>Present Job:</td>
                                <td>
                                    <input type="radio" name="radioPresentJob5" id="radioPresentJob5" value="Yes" checked="checked" onclick="toggleVisibility('rowEndDate5', 'hidden');">&nbsp;Yes&nbsp;&nbsp;
                                    <input type="radio" name="radioPresentJob5" id="radioPresentJob5" value="No" onclick="toggleVisibility('rowEndDate5', 'shown');">&nbsp;No<br />
                                </td>
                            </tr>
                            <tr id="rowEndDate5" class="hidden"><td>End Date:</td><td><input type="text" name="txtEndDate5" id="txtEndDate5" placeholder="[ End Date ]" /></td></tr>
                            <tr><td>Summary of Responsibilities:</td><td><textarea name="txtJobSummary5" id="txtJobSummary4"></textarea></td></tr>                        
                        </table>
                        <input type="button" id="btnAddExperience" class='float-right' onclick="showNextExperience((<% out.print("{alert('Jose is the man')}");%>)" value="Add Experience" />
                    </div>
                    <div id="divSkills">
                        <%
                            String[] skills = {"", "", "", "", "", "", "", "", "", ""}; //10 skills
                            int lastSkill = 2; //track the last skill for add button
                            ArrayList<Skill> skillsList = resume.getSkillsList();
                            if (skillsList
                                    != null) {
                                for (int i = 0; i < skillsList.size(); i++) {
                                    skills[i] = skillsList.get(i).getName();
                                    System.out.println("SIZE OF SKILL LIST is " + skillsList.size());
                                }
                            }


                        %>

                        <h3 class="page-sub-title">Skills</h3>
                        <table id="tblSkills">
                            <tr id="trSkill_1">
                                <td colspan="2"><input type="text" name="txtSkill_1" id="txtSkill_1" value="<% out.print(skills[0]); %>"/></td>
                            </tr>
                            <tr  id="trSkill_2" class="<% if (skills[1].isEmpty()) {
                                    out.print("hidden");
                                } else {
                                    out.print("shown");
                                } %>"><td colspan="2"><input type="text" name="txtSkill_2" id="txtSkill_2" value="<% out.print(skills[1]);%>"/></td></tr>
                            <tr  id="trSkill_3" class="<% if (skills[2].isEmpty()) {
                                    out.print("hidden");
                                } else {
                                    out.print("shown");
                                    lastSkill = 3;
                                } %>"><td colspan="2"><input type="text" name="txtSkill_3" id="txtSkill_3"  value="<% out.print(skills[2]);%>"/></td></tr>
                            <tr  id="trSkill_4" class="<% if (skills[3].isEmpty()) {
                                    out.print("hidden");
                                } else {
                                    out.print("shown");
                                    lastSkill = 4;
                                } %>"><td colspan="2"><input type="text" name="txtSkill_4" id="txtSkill_4"  value="<% out.print(skills[3]);%>"/></td></tr>
                            <tr  id="trSkill_5" class="<% if (skills[4].isEmpty()) {
                                    out.print("hidden");
                                } else {
                                    out.print("shown");
                                    lastSkill = 5;
                                } %>"><td colspan="2"><input type="text" name="txtSkill_5" id="txtSkill_5"  value="<% out.print(skills[4]);%>"/></td></tr>
                            <tr  id="trSkill_6" class="<% if (skills[5].isEmpty()) {
                                    out.print("hidden");
                                } else {
                                    out.print("shown");
                                    lastSkill = 6;
                                } %>"><td colspan="2"><input type="text" name="txtSkill_6" id="txtSkill_6"  value="<% out.print(skills[5]);%>" /></td></tr>
                            <tr  id="trSkill_7" class="<% if (skills[6].isEmpty()) {
                                    out.print("hidden");
                                } else {
                                    out.print("shown");
                                    lastSkill = 7;
                                } %>"><td colspan="2"><input type="text" name="txtSkill_7" id="txtSkill_7"  value="<% out.print(skills[6]);%>"/></td></tr>
                            <tr  id="trSkill_8" class="<% if (skills[7].isEmpty()) {
                                    out.print("hidden");
                                } else {
                                    out.print("shown");
                                    lastSkill = 8;
                                } %>"><td colspan="2"><input type="text" name="txtSkill_8" id="txtSkill_8"  value="<% out.print(skills[7]);%>" /></td></tr>
                            <tr  id="trSkill_9" class="<% if (skills[8].isEmpty()) {
                                    out.print("hidden");
                                } else {
                                    out.print("shown");
                                    lastSkill = 9;
                                } %>"><td colspan="2"><input type="text" name="txtSkill_9" id="txtSkill_9"  value="<% out.print(skills[8]);%>"/></td></tr>
                            <tr  id="trSkill_10" class="<% if (skills[9].isEmpty()) {
                                    out.print("hidden");
                                } else {
                                    out.print("shown");
                                    lastSkill = 10;
                                }%>"><td colspan="2"><input type="text" name="txtSkill_10" id="txtSkill_10"  value="<% out.print(skills[9]);%>"/></td></tr>           
                        </table>
                        <input type="button" id="btnAddSkill" onclick="showNextSkill(<% out.print(lastSkill + 1);%>)" value="Add Skill" />
                        <div class="clear"></div>
                    </div> <!-- end div skills-->
                    <input type="submit" class="float-right" id="btnSubmit" name="btnSubmit" value="Save" /> 
                </form> 

            </div>                <div class="clear"></div><!--important-->

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
