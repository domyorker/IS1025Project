Ok...now that we have a db model, let's get to work....

Jose
	I will work on the resume creation part: SQL queries to save, update and delete a resume.
	I will also work on the UI for entering the data.

Steave
	Please start thinking about managing the session: tracking the login information b/c we will need to check
	against this when making any changes to the db. I (Jose) will start thinking about session too, but my focus
	will be on the db queries for adding/update/deleting.
	A session can be created this way:
		HttpSession session=request.getSession(true);
		session.setAttribute("userName", userName);
		session.setAttribute("UserID", userID); 
	I believe this is how it will go: when a request comes in to a servlet, we check the session object for the user and 
	if the session is set, then we know the user is logged in. The db tracks users will a user ID, so that will have to be tracked as well.
Sean
	Please create classes for the tables (you may ignore the non-essential ones for now, such as admin). 

Note: I now I'm not "in charge" but we have to focus our time and energy in order to make this happen.