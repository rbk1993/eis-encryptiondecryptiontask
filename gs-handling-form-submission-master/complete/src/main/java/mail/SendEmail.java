package mail;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/*
 * Inspired by : http://www.tutorialspoint.com/java/java_sending_email.htm
 * 
 * 
 * 
 */
public class SendEmail {
	
	   public static void sendMail(String from, String to, String password, String subject, String text) {

	      // Assuming you are sending email from localhost
	      String host = "smtp.gmail.com";

	      // Get system properties
	      Properties properties = System.getProperties();

	      // Setup mail server     
	      properties.put("mail.smtp.host", "smtp.gmail.com");
  	      properties.put("mail.smtp.socketFactory.port", "465");
	      properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
	      properties.put("mail.smtp.auth", "true");
	      properties.put("mail.smtp.port", "465");

	      // Get the default Session object.
	      //Session session = Session.getDefaultInstance(properties);
	      
	      Session session = Session.getDefaultInstance(properties,
	    		  new javax.mail.Authenticator() {
	    			protected PasswordAuthentication getPasswordAuthentication() {
	    				return new PasswordAuthentication(from, password);
	    			}
	    		  });

	      try{
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	         // Set Subject: header field
	         message.setSubject(subject);

	         // Now set the actual message
	         message.setText(text);

	         // Send message
	         Transport.send(message);

	         System.out.println("Sent message successfully....");
	         
	         System.out.println("Message body : "+text);
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
}
}