package com.ipu.imagedropbox.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ipu.imagedropbox.model.ImageDetails;
import com.ipu.imagedropbox.model.UserDetails;
import com.ipu.imagedropbox.utility.HibernateUtility;

/**
 * Servlet DeleteImage which deletes image as per requested by user.
 * 
 * @author Raghav
 */
@WebServlet("/DeleteImage")
public class DeleteImage extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteImage() {
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
		HttpSession session = request.getSession();
		List<ImageDetails> list = (List<ImageDetails>) session
				.getAttribute("list");
		String itemId = request.getParameter("hidden");

		Session hibernateSession = HibernateUtility.getSession();
		Transaction transaction = hibernateSession.beginTransaction();
		ImageDetails item1 = null;
		for (ImageDetails item : list) {
			if (item.getImageId() == Integer.parseInt(itemId)) {
				item1 = item;
			}
		}
		if (item1 != null) {
			list.remove(item1);
			UserDetails user = (UserDetails) session.getAttribute("user");
			user.getImageDetails().remove(item1);
			hibernateSession.delete(item1);

			session.setAttribute("list", list);
		} else
			System.out.println("no such item ");
		transaction.commit();
		request.setAttribute("uploaded", true);
		request.getRequestDispatcher("dashboard.jsp")
				.forward(request, response);
	}

}
