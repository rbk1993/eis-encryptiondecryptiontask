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
	
	   public static void sendMail(String destination, String body, String subject, String attachmentFilePath, String attachmentFileName) {

	      // Get system properties
	      Properties properties = System.getProperties();

	      // Setup mail server     
	      properties.put("mail.smtp.host", "smtp.gmail.com");
  	      properties.put("mail.smtp.socketFactory.port", "465");
	      properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
	      properties.put("mail.smtp.auth", "true");
	      properties.put("mail.smtp.port", "465");

	      Session session = Session.getDefaultInstance(properties,
	    		  new javax.mail.Authenticator() {
	    			protected PasswordAuthentication getPasswordAuthentication() {
	    				return new PasswordAuthentication(mailCredentials.platformEmail, mailCredentials.platformPassword);
	    			}
	    		  });

	      try{
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(mailCredentials.platformEmail));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(destination));

	         // Set Subject: header field
	         message.setSubject(subject);
	         
	         // Create the message part
	         BodyPart messageBodyPart = new MimeBodyPart();

	         // Now set the actual message
	         messageBodyPart.setText(body);
	         
	         // Create a multipart message
	         Multipart multipart = new MimeMultipart();
	         
	         // Set text message part
	         multipart.addBodyPart(messageBodyPart);
	         
	         // Part two is attachment
	         messageBodyPart = new MimeBodyPart();
	         DataSource source = new FileDataSource(attachmentFilePath+attachmentFileName);
	         messageBodyPart.setDataHandler(new DataHandler(source));
	         messageBodyPart.setFileName(attachmentFileName);
	         multipart.addBodyPart(messageBodyPart);

	         // Send the complete message parts
	         message.setContent(multipart);
	         
	         // Send message
	         Transport.send(message);

	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
}
}