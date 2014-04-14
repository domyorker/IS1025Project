<%-- 
    Document   : login
    Created on : Mar 29, 2014, 5:39:22 PM
    Author     : Jose Marte
--%>

<%@page import="srr.model.StudentAccount"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title> SRR: Login</title>
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
                <div id="errorDIV">
                    <span>
                        <% String isSuccessRedirect = request.getParameter("success");
                            String isErrorRedirect = request.getParameter("error");
                            if (isSuccessRedirect != null && isSuccessRedirect.equals("true")) {
                                String msg = (String) session.getAttribute("successMsg");
                                out.println(msg);
                            } else if (isErrorRedirect != null && isErrorRedirect.equals("true")) {
                                String msg = (String) session.getAttribute("errorList");
                                out.println(msg);
                            }

                        %>
                    </span>
                </div>
                <h1 class="page-title">
                    Login
                </h1>
                <form id="frmLogin" name="frmLogin" action="login" method="post">
                    <table>

                        <tr><th colspan="2">Enter your credentials below</th></tr>
                        <tr><td>Username:</td><td><input type="text" name="txtUserName" id="txtUserName" placeholder="[ username ]" /></td></tr>
                        <tr><td>Password:</td><td><input type="password" name="txtPassword" id="txtPassword" placeholder="[ password ]" /></td></tr>
                        <tr><td></td><td><input type="submit" id="btnSubmit" name="bntSubmit" value="Login" /></td></tr>
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