package utils;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage;

/**
 * Classe di supporto per l'invio delle email
 * @author Luca
 */
public class Email {
	
	/**
	 * Invia un email.
	 * @param username
	 * @param password
	 */
	public static void send(String username, String password) {
		
		try {
			String host = "smtp.gmail.com";
			String user = "w3play.staff@gmail.com";
			String pass = "w3playroot";
			String to = username;
			String from = "w3play.staff@gmail.com";
			String subject = "W3Play: Password Recovery";
			String messageText = "Your Password:" + password;
			boolean sessionDebug = false;

			Properties props = System.getProperties();

			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.required", "true");

			// java.security.Security.addProvider(new
			// com.sun.net.ssl.internal.ssl.Provider());
			Session mailSession = Session.getDefaultInstance(props, null);
			mailSession.setDebug(sessionDebug);
			
			Message msg = new MimeMessage(mailSession);
			msg.setFrom(new InternetAddress(from));
			
			InternetAddress[] address = { new InternetAddress(to) };
			
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			msg.setText(messageText);

			Transport transport = mailSession.getTransport("smtp");
			transport.connect(host, user, pass);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
			
			System.out.println("message send successfully to: " + user);
			
		} catch (Exception ex) {
			System.out.println(ex);
		}

	}
}
