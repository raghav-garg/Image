package com.ipu.imagedropbox.utility;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

@SuppressWarnings("deprecation")
public class HibernateUtility {
	private static SessionFactory sessionFactory;
	private static SessionFactory imageSession;
	static {
		AnnotationConfiguration config = new AnnotationConfiguration();

		sessionFactory = config.configure("hibernate.cfg.xml")
				.buildSessionFactory();
		// config.addAnnotatedClass(ImageDetails.class);
		// imageSession = config.configure("hibernate.cfg.xml")
		// .buildSessionFactory();
	}

	public static Session getSession() {
		return sessionFactory.openSession();
	}

	public static void closeSesssionFactory() {
		sessionFactory.close();
	}

	// public static Session getImageSession() {
	// return imageSession.openSession();
	// }
	//
	// public static void closeImageSesssionFactory() {
	// imageSession.close();
	// }
}
