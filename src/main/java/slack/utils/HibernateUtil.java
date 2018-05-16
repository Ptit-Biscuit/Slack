package slack.utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate util
 */
public class HibernateUtil {
	/**
	 * Logger
	 */
	private static final Logger LOGGER = LogManager.getLogger(HibernateUtil.class);

	/**
	 * Hibernate session
	 */
	private static final SessionFactory sessionFactory = buildSessionFactory();

	/**
	 * Build the session factory
	 * @return SessionFactory
	 */
	private static SessionFactory buildSessionFactory() throws ExceptionInInitializerError {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			return new Configuration().configure().buildSessionFactory();
		} catch (HibernateException ex) {
			// Make sure you log the exception, as it might be swallowed
			LOGGER.error("Initial SessionFactory creation failed.", ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * Getter of session factory
	 * @return SessionFactory
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
