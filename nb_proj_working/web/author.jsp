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
                <div id="errorDIV">
                    <% //check for the existence of errors if this is a redirect
                        String isSuccessRedirect = request.getParameter("success");
                        String isErrorRedirect = request.getParameter("error");
                        if (isSuccessRedirect != null && isSuccessRedirect.equals("true")) {
                            String msg = (String) session.getAttribute("successMsg");
                            out.println(msg);
                        } else if (isErrorRedirect != null && isErrorRedirect.equals("true")) {
                            String msg = (String) session.getAttribute("errorList");
                            out.println(msg);
                        }
                    %>
                </div>
                <h1 class="page-title">
                    Editing: <i> <% out.print(resume.getTitle());%></i>

                </h1>
                <form id="frmAuthor" name="frmAuthor" action="author?action=create" method="post">
                    <table>
                        <tr><th colspan="2">Create your resume using the form below</th></tr>
                        <tr><td colspan="2" class="page-sub-title">Summary</td></tr>
                        <tr><td>Objective:</td><td><textarea name="txtObjective" id="txtObjective"><% out.print(resume.getObjective());%></textarea></td></tr>
                        <tr><td>Experience:</td><td><textarea name="txtExperience" id="txtExperience"><% out.print(resume.getExperience());%></textarea></td></tr>
                        <tr><td>Accomplishments:</td><td><textarea name="txtAccomplishments" id="txtAccomplishments"><% out.print(resume.getAccomplishments());%></textarea></td></tr>
                    </table>
                    <div id="divWorkExperience">
                        <h3 class="page-sub-title">Work Experience</h3>
                        <table id="WorkExperience1">
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
                        </table>
                        <table id="WorkExperience2" class="hidden">
                            <tr><td>Job Title:</td><td><input type="text" name="txtJobTitle2" id="txtJobTitle2" placeholder="[ Job Title ]" /></td></tr>
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
                        <table id="WorkExperience3" class="hidden">
                            <tr><td>Job Title:</td><td><input type="text" name="txtJobTitle3" id="txtJobTitle3" placeholder="[ Job Title ]" /></td></tr>
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
                        <table id="WorkExperience4" class="hidden">
                            <tr><td>Job Title:</td><td><input type="text" name="txtJobTitle4" id="txtJobTitle4" placeholder="[ Job Title ]" /></td></tr>
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
                        <table id="WorkExperience5" class="hidden">
                            <tr><td>Job Title:</td><td><input type="text" name="txtJobTitle5" id="txtJobTitle5" placeholder="[ Job Title ]" /></td></tr>
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
                        <input type="button" id="btnAddExperience" class='float-right' onclick="showNextExperience(2)" value="Add Another" />
                    </div>
                    <table>
                        <div id="divSkills">
                            <%
                                String[] skills = {"", "", "", "", "", "", "", "", "", ""}; //10 skills
                                ArrayList<Skill> skillsList = resume.getSkillsList();
                                if (skillsList != null) {
                                    for (int i = 0; i < skillsList.size(); i++) {
                                        skills[i] = skillsList.get(i).getName();
                                    }
                                }


                            %>
                            <h3 class="page-sub-title">Skills</h3>
                            <table id="tblSkills">
                                <tr id="trSkill_1"><td colspan="2"><input type="text" name="txtSkill_1" id="txtSkill_1" value="<% out.print(skills[0]); %>"/></td></tr>
                                <tr  id="trSkill_2" class="hidden <% if (!skills[1].isEmpty()) {
                                        out.print("shown");
                                    }%>"><td colspan="2"><input type="text" name="txtSkill_2" id="txtSkill_2" value="<% out.print(skills[1]);%>"/></td></tr>
                                <tr  id="trSkill_3" class="hidden"><td colspan="2"><input type="text" name="txtSkill_3" id="txtSkill_3"/></td></tr>
                                <tr  id="trSkill_4" class="hidden"><td colspan="2"><input type="text" name="txtSkill_4" id="txtSkill_4" /></td></tr>
                                <tr  id="trSkill_5" class="hidden"><td colspan="2"><input type="text" name="txtSkill_5" id="txtSkill_5"/></td></tr>
                                <tr  id="trSkill_6" class="hidden"><td colspan="2"><input type="text" name="txtSkill_6" id="txtSkill_6" /></td></tr>
                                <tr  id="trSkill_7" class="hidden"><td colspan="2"><input type="text" name="txtSkill_7" id="txtSkill_7"/></td></tr>
                                <tr  id="trSkill_8" class="hidden"><td colspan="2"><input type="text" name="txtSkill_8" id="txtSkill_8" /></td></tr>
                                <tr  id="trSkill_9" class="hidden"><td colspan="2"><input type="text" name="txtSkill_9" id="txtSkill_9"/></td></tr>
                                <tr  id="trSkill_10" class="hidden"><td colspan="2"><input type="text" name="txtSkill_10" id="txtSkill_10" /></td></tr>
                                <tr><td colspan="2"> <input type="button" id="btnAddSkill" onclick="showNextSkill(2)" value="Add Skill" /></td></tr>                               
                            </table>
                            <input type="submit" class="float-right" id="btnSubmit" name="btnSubmit" value="Save" /> 
                        </div>
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
