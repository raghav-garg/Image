package com.ipu.imagedropbox.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ipu.imagedropbox.model.ImageDetails;

/**
 * Servlet implementation class EditImage
 * @author Raghav
 */
@WebServlet("/EditImage")
public class EditImage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditImage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		List<ImageDetails> list = (List<ImageDetails>) session
				.getAttribute("list");
		String itemId = request.getParameter("hidden");
		System.out.println(request.getParameter("hidden"));
		ImageDetails item1 = null;
		for (ImageDetails item : list) {
			// System.out.println(item.getName() + " and " + itemName);
			if (item.getImageId() == Integer.parseInt(itemId)) {
				item1 = item;
			}
		}

		session.setAttribute("editItem", item1);

		request.setAttribute("uploaded", true);
		request.getRequestDispatcher("editImage.jsp")
				.forward(request, response);
	}

}
