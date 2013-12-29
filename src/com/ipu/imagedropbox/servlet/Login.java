package com.ipu.imagedropbox.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ipu.imagedropbox.model.UserDetails;
import com.ipu.imagedropbox.utility.HibernateUtility;

/**
 * Class Login which gets user details from table in database and validates
 * his/her authenticity.Redirects to login page again if invalid user else lets
 * the user carry operations on list belonging to him/her.
 * 
 * @author Raghav
 * 
 */
public class Login extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static org.apache.log4j.Logger logger = org.apache.log4j.Logger
			.getLogger(Login.class);

	@SuppressWarnings("deprecation")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Session hibernateSession = HibernateUtility.getSession();
		Transaction transaction = hibernateSession.beginTransaction();
		response.setContentType("text/html");
		PrintWriter printWriter = response.getWriter();
		String name = request.getParameter("name");
		String password = request.getParameter("pass");

		// For invalid user.
		boolean wrongUser = true;

		// Retrieves all the user details from database.
		Query userDetailsQueryResult = hibernateSession
				.createQuery("from com.ipu.imagedropbox.model.UserDetails where userName=:username");
		userDetailsQueryResult.setParameter("username", name);
		@SuppressWarnings("unchecked")
		// Convert the query result to a list of all users.
		List<UserDetails> userList = (List<UserDetails>) userDetailsQueryResult
				.list();
		if (!userDetailsQueryResult.list().isEmpty()) {
			UserDetails user = userList.get(0);
			if ((password).equals(user.getPassword())) {
				request.setAttribute("uploaded", true);
				request.getSession().setAttribute("user", user);
				request.getSession().setAttribute("list",
						user.getImageDetails());

				RequestDispatcher rd = request
						.getRequestDispatcher("dashboard.jsp");
				rd.forward(request, response);
			} else {

				if (wrongUser == true) {
					request.setAttribute("error", "true");
					RequestDispatcher rd = request
							.getRequestDispatcher("login.jsp");
					rd.forward(request, response);
				}
			}

		}

		else if (name == null || password == null) {
			request.setAttribute("empty", "true");
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
		} else {
			request.setAttribute("error", "true");
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);

		}
		transaction.commit();
		printWriter.close();

	}
}
