package utilities;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
 
@WebServlet("/sendEmailServlet")
public class EnviarEmailServlet extends HttpServlet{
    
    @Autowired
    private JavaMailSender mailSender;
     
    @Override
	public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
          config.getServletContext());
      }
    
    @Override
	protected void doPost(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException, IOException {    	
   	
	    	// informacion del correo
	        String recipientAddress = "dani.rodriguez.sev@gmail.com";
	        String subject = "asunto";
	        String message = "correo de informacion";       
		     
		    // debug
		    System.out.println("To: " + recipientAddress);
		    System.out.println("Subject: " + subject);
		    System.out.println("Message: " + message);
		     
		    // creacion del SimpleMailMessage
		    SimpleMailMessage email = new SimpleMailMessage();
		    email.setTo(recipientAddress);
		    email.setSubject(subject);
		    email.setText(message);
		     
		    // envia el email
		    mailSender.send(email);        
    }
}
