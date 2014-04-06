<%-- 
    Document   : browse
    Created on : Mar 29, 2014, 5:39:22 PM
    Author     : Jose Marte
--%>

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
            $(function() {
                $(" #resume-gallery").selectable({
                    stop: function(event, ui) {
                        $(event.target).children('.ui-selected').not(':first').removeClass('ui-selected');
                    },
                    selected: function(event, ui) {
                        var resumeId = $(".ui-selected").attr("id");
                        alert("When implemented, I will fetch resume with ID: " + resumeId);
                    }
                });
            });
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
                <h1>
                    Browse the Gallery
                </h1>
                <div id="resume-gallery">
                    <%
                 ///query db for all resumes
                        /*
                        while(rs.next()) {
                        out.println("<div class='ui-widget-content' id='" + rs.getString("resumeID") +  "'>");
                        ...span 1
                        
                        }
                        */
                    %>
                </div>-->
<!--                    <div class = "ui-widget-content"  id="206">
                        <span class="name">Last, First</span >
                        <span class="resume-summary">Vivamus aliquam lobortis dui, a egestas nisi spanlacerat vitae. Pellentesque sit amet spanretium dui, vel mollis orci.</span >
                    </div>
                </div>-->
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
