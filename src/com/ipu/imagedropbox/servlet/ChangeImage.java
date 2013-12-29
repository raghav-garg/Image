package com.ipu.imagedropbox.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ipu.imagedropbox.model.ImageDetails;
import com.ipu.imagedropbox.utility.HibernateUtility;

/**
 * Servlet implementation class ChangeImage
 * @author Raghav
 */
@WebServlet("/ChangeImage")
public class ChangeImage extends HttpServlet {
	private static List<FileItem> responseList;
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChangeImage() {
		super();
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
		// TODO Auto-generated method stub

		request.setAttribute("uploaded", true);
		request.setAttribute("edit", null);
		Session hibernateSession = HibernateUtility.getSession();
		Transaction transaction = hibernateSession.beginTransaction();

		HttpSession session = request.getSession();
		ImageDetails editItem = (ImageDetails) session.getAttribute("editItem");

		boolean isMultipartContent = ServletFileUpload
				.isMultipartContent(request);
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(8 * 1024 * 1024);

		try {
			responseList = upload.parseRequest(request);

			if (!responseList.isEmpty()) {

				for (FileItem item : responseList) {
					if (item.getSize() > 0) {
						editItem.setImageSize((int) item.getSize());
						String path = editItem.getImagePath();
						File file = new File(path);
						file.delete();
						item.write(file);
						hibernateSession.update(editItem);
						transaction.commit();
						RequestDispatcher dispatcher = request
								.getRequestDispatcher("dashboard.jsp");
						dispatcher.forward(request, response);
					}
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
