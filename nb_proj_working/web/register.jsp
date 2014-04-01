<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title> SRR: Registration</title>
        <meta name="Author" content="Jose Marte">
        <link rel="stylesheet" href="css/jquery-ui.css">
        <script src="js/jquery-2.1.0.min.js"></script>
        <script src="js/jquery-ui.min.js"></script>	<!--// jquery ui Style (local) //-->
        <link rel="stylesheet" href="css/ice_blue.css" type="text/css" media="screen,projection,print">	<!--// Document Style //-->
        <script>
            //call the userlistws to obtain the list of users                
            $(document).ready(function() {
                jQuery.ajax({
                    type: "GET",
                    url: "rest/getStates",
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    success: function(data, status, jqXHR) {
                        //success, so  call fn to display data
                        displayStatesListFromJson(data);
                    },
                    error: function(jqXHR, status) {
                        console.log("Error: " + status);
                    }
                }),
                        jQuery.ajax({
                            type: "GET",
                            url: "rest/getGradeLevels",
                            contentType: "application/json; charset=utf-8",
                            dataType: "json",
                            success: function(data, status, jqXHR) {
                                //success, so  call fn to display data
                                displayGradeLevelsListFromJson(data);
                            },
                            error: function(jqXHR, status) {
                                console.log("Error: " + status);
                            }
                        });
            }); //end ready fn
            //a function to render the levels dropdown list from json
            function displayStatesListFromJson(jsonData) {
                for (var i = 0; i < jsonData.length; i++) {
                    $('<option/>').attr('value', jsonData[i].levelID).text(jsonData[i].name).appendTo($('#ddlState'));
                }
            }
            //a function to render the grade levels dropdown list from json
            function displayGradeLevelsListFromJson(jsonData) {
                for (var i = 0; i < jsonData.length; i++) {
                    $('<option/>').attr('value', jsonData[i].levelID).text(jsonData[i].levelName).appendTo($('#ddlGradeLevel'));
                }
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
                    Registration
                </h1>
                <div id="errorDIV">
                    <% //check for the existence of errors if this is a redirect
                        String isErrorRedirect = request.getParameter("error");
                        if (isErrorRedirect != null && isErrorRedirect.equals("true")) {
                            String errorList = (String) session.getAttribute("errorList");
                            if (errorList != null && !errorList.isEmpty()) {
                                out.println("<h3 class='title'>Errors found.</h3>");
                                out.println(errorList);
                            }
                        }
                    %>
                </div>
                <p>Please select the appropriate registration tab below.</p>
                <div id="student-registration">
                    <h3>Student Registration</h3>
                    <div>
                        <form id="frmRegister" name="frmRegister" action="Manage?action=register" method="post">
                            <table>
                                <tr><th colspan="2">Create a Student account by registering below</th></tr>
                                <tr><td>Username:</td><td><input type="text" name="txtUserName" id="txtUserName" value="${param.t}" placeholder="[ username ]" /></td></tr>
                                <tr><td>Password:</td><td><input type="password" name="txtPassword" id="txtPassword" placeholder="[ password ]" /></td></tr>
                                <tr><td>First Name:</td><td><input type="text" name="txtFirstName" id="txtFirstName" placeholder="[ First Name ]" /></td></tr>
                                <tr><td>Last Name:</td><td><input type="password" name="txtLastName" id="txtLastName" placeholder="[ Last Name ]" /></td></tr>
                                <tr>
                                    <td>Grade Level:</td>
                                    <td>
                                        <select id="ddlGradeLevel" name="ddlGradeLevel">
                                            <option value="" selected="selected">Select your grade level</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr><td>Email Address:</td><td><input type="email" name="txtEmail" id="txtEmail" placeholder="[ username@pitt.edu ]" /></td></tr>
                                <tr><td>Telephone Number:</td><td><input type="tel" name="txtTel" id="txtTel" placeholder="[ Telephone Number ]" /></td></tr>
                                <tr><td>Address:</td><td><input type="text" name="txtAddressLine1" id="txtAddressLine1" placeholder="[ Address Line 1 ]" /></td></tr>
                                <tr><td></td><td><input type="text" name="txtAddressLine2" id="txtAddressLine2" placeholder="[ Address Line 2 ]" /></td></tr>
                                <tr><td>City:</td><td><input type="text" name="txtCity" id="txtCity" placeholder="[ City ]" /></td></tr>
                                <tr>
                                    <td>State/Province:</td>
                                    <td>
                                        <select id="ddlState" name="ddlState">
                                            <option value="" selected="selected">Select a State or Province</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr><td>ZIP/Postal Code:</td><td><input type="text" name="txtZIP" id="txtZIP" placeholder="[ ZIP/Postal Code ]" /></td></tr>
                                <tr><td></td><td><input type="submit" id="bntSubmitStudent" name="bntSubmitStudent" value="Register" /></td></tr>
                            </table>
                        </form>
                    </div><!--End student registration-->

                </div><!--End registration div-->
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