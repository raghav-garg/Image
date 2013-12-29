package com.ipu.imagedropbox.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
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
import com.ipu.imagedropbox.model.UserDetails;
import com.ipu.imagedropbox.utility.HibernateUtility;

/**
 * Class UploadImage of type servlet that handles File upload request from valid
 * user,saves the new image to database as well as the specified location .
 * 
 * @author Raghav
 * 
 */

public class UploadImage extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static final String UPLOAD_DIRECTORY = "C:/imageUploaded";
	private static List<FileItem> imageList = new ArrayList<FileItem>();
	// File uploaded by user.
	private static List<FileItem> responseList;
	public static org.apache.log4j.Logger logger = org.apache.log4j.Logger
			.getLogger(UploadImage.class);

	/**
	 * Method getImageList() which returns the uploaded image by the user.
	 * 
	 * @return List<FileItem> type FileItem.
	 */
	public static List<FileItem> getImageList() {
		return responseList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html");

		ImageDetails imageDetails = new ImageDetails();
		// For image to be uploaded.
		HttpSession session = request.getSession();
		Session hibernateSession = HibernateUtility.getSession();
		Transaction transaction = hibernateSession.beginTransaction();

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(8 * 1024 * 1024);

		try {
			// The image uploaded by the user,if is not empty.
			responseList = upload.parseRequest(request);
			if (!responseList.isEmpty()) {

				for (FileItem item : responseList) {

					if ((item != null) && (!item.equals(""))) {
						String name = item.getName().substring(
								item.getName().lastIndexOf("\\") + 1);
						imageDetails.setImageName(name);
						imageDetails
								.setImagePath(UPLOAD_DIRECTORY + "/" + name);
						imageDetails.setImageSize((int) item.getSize());
						// Get the current user who has logged in.
						UserDetails user = (UserDetails) session
								.getAttribute("user");
						imageDetails.setUser(user);
						user.getImageDetails().add(imageDetails);
						session.setAttribute("user", user);
						hibernateSession.save(imageDetails);

						imageList.add(item);
						if (!item.isFormField()) {
							String actualImageLocation = new File(
									item.getName()).getName();
							item.write(new File(UPLOAD_DIRECTORY
									+ File.separator + actualImageLocation));

							request.setAttribute("uploaded", true);
							request.setAttribute("sizeExceed", null);

							RequestDispatcher rd = request
									.getRequestDispatcher("dashboard.jsp");
							rd.forward(request, response);
						}
					} else {
						request.setAttribute("sizeExceed", "yes");
						RequestDispatcher rd = request
								.getRequestDispatcher("dashboard.jsp");
						rd.forward(request, response);
					}
					transaction.commit();
				}
			}
		} catch (FileUploadException e) {
			logger.error("Image not uploaded ", e);
			System.out
					.println("Wrong type of file upload.Please upload an image type.");
		} catch (Exception e) {

			System.out.println("Could  not write image.");
		}

	}
}
