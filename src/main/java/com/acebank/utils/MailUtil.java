package com.acebank.utils;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.java.Log;

@Log
public class MailUtil {

	public static boolean sendMail(final String recipient, String subject, String body) throws MessagingException {

		log.info("sendMail() <<");

		try {
			/*
			 * The below snippet creates a new Session object, which is used to send the
			 * mail. It then creates a new MimeMessage object, which is used to set the
			 * sender's email address, the recipient's email address, the subject of the
			 * email, and the body of the email. Finally, it uses the Transport class to
			 * send the email.
			 */

			/*
			 * The getPasswordAuthentication() method is only necessary if you want to get
			 * the username and password dynamically, such as from a database or a file.
			 */

			// Get the session object
			Session session = Session.getInstance(getProperties(), new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(Constants.DEFAULT_MAIL, Constants.DEFAULT_MAIL_GOOGLE_APP_PASSWORD);
				}
			});

			// Create a new MimeMessage object
			Message message = new MimeMessage(session);
			/*
			 * The difference between the two code snippets is that the first code snippet
			 * uses a string to represent the recipient's email address, while the second
			 * code snippet uses an InternetAddress object. The InternetAddress object is
			 * more secure than a string because it can be used to validate the recipient's
			 * email address.
			 * 
			 * In general, it is recommended to use the InternetAddress object to represent
			 * email addresses. However, if you are only sending emails to a small number of
			 * trusted recipients, then you can use a string.
			 */
//			message.setFrom(sender);
//			message.setRecipients(Message.RecipientType.TO, recipient);

			message.setFrom(new InternetAddress(Constants.DEFAULT_MAIL));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
			message.setSubject(subject);
			message.setText(body);

			Transport.send(message);// Send the email
		} catch (MessagingException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		log.info("sendMail() >>");
		return true;
	}

	private static Properties getProperties() {
		/*
		 * The below snippet configures the sender mail to use the Gmail SMTP server.
		 * The username and password are for a Gmail account. The mail.smtp.auth
		 * property is set to true to enable authentication, and the
		 * mail.smtp.starttls.enable property is set to true to enable Transport Layer
		 * Security (TLS).
		 */

		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

//		properties.setProperty("mail.smtp.username", DEFAULT_MAIL);
//		properties.setProperty("mail.smtp.password", DEFAULT_MAIL_PASSWORD);

		/*
		 * This below snippet creates a session that uses SSL encryption for the Gmail
		 * SMTP server. You can then use this session to send and receive emails. I hope
		 * this helps! Let me know if you have any other questions.
		 */
//		properties.setProperty("mail.smtp.ssl.enable", "true");
//		properties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");

		return properties;
	}
}