package com.ipu.imagedropbox.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ipu.imagedropbox.model.ImageDetails;
import com.ipu.imagedropbox.utility.HibernateUtility;

/**
 * Class EditName which gets request from user and updates the changes into the
 * database.
 * 
 * @author Raghav
 */
@WebServlet("/EditName")
public class ChangeName extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChangeName() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String name = request.getParameter("newName");
		HttpSession session = request.getSession();
		ImageDetails image = (ImageDetails) session.getAttribute("editItem");
		Session hibernateSession = HibernateUtility.getSession();
		Transaction transaction = hibernateSession.beginTransaction();

		image.setImageName(name);
		hibernateSession.update(image);
		transaction.commit();
		request.setAttribute("uploaded", true);
		request.setAttribute("edit", null);
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("dashboard.jsp");
		dispatcher.forward(request, response);

	}

}
