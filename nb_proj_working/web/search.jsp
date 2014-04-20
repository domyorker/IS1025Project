<%-- 
    Document   : search
    Created on : Mar 29, 2014, 5:39:22 PM
    Author     : Jose Marte
--%>

<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashMap"%>
<%@page import="srr.model.StudentAccount"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title> SRR: Search the Repository</title>
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
                    <% //check for the existence of errors if this is a redirect
                        String isErrorRedirect = request.getParameter("error");
                        if (isErrorRedirect != null && isErrorRedirect.equals("true")) {
                            String msg = (String) session.getAttribute("errorList");
                            out.println(msg);
                        }
                    %>
                </div> <!-- end error div-->
                <h1 class="page-title">
                    Search Resume Repository
                </h1>
                <form id="frmSearch" name="frmSearch" action="searchws" method="get">
                    <table>
                        <tr>
                            <td><input type="text" name="key" id="txtSearchQuery" placeholder="[ search query ]" /></td>
                            <td><input type="submit" id="btnSubmit" name="bntSubmit" value="Search" /></td>
                        </tr>
                    </table>
                </form>
                <div id="searchResultsDIV">
                    <%
                        HashMap results;
                        try {
                            results = (HashMap) session.getAttribute("matchedResults");
                            if (results != null) {
                                    //borrowed from http://www.tutorialspoint.com/java/java_hashmap_class.htm
                                // Get a set of the entries
                                Set set = results.entrySet();
                                // Get an iterator
                                Iterator i = set.iterator();
                                // Display elements
                                out.println("<ul class='search-results-list'>");
                                while (i.hasNext()) {
                                    Map.Entry me = (Map.Entry) i.next();

                                    out.println("<li>");
                                    out.println("<a href='view?resumeID=" + me.getKey().toString() + "'>" + me.getValue() + "</a>");
                                    out.println("</li>");
                                }
                                //end borrowed code
                                out.println("</ul>"); //end the list
                            }

                        } catch (NullPointerException ex) {
                            System.out.println("Error trying to regenerate the serach results list: " + ex.getMessage());
                        }
                    %>
                </div>

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
                        <a href="http://www.ischool.pitt.edu/people/babichenko.php" > Project Supervisor: Dmitriy Babichenko</a>
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