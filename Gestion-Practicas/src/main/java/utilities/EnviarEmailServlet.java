package utilities;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import services.ActorService;
 
@WebServlet("/sendEmailServlet")
public class EnviarEmailServlet extends HttpServlet{
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private ActorService actorService;
     
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
	        String subject = "Plataforma de Gestión de Prácticas Externas";
	        String message = "Sus credenciales de usuario son: //r//r"
	        		+ "Usuario: " + "paco" + "//r//r"
	        		+ "Contraseña: " + actorService.generateSecureRandomPassword();  
	        
		     
		    // debug
		    System.out.println("To: " + recipientAddress);
		    System.out.println("Subject: " + subject);
		    System.out.println("Message: " + message);
		     
		    // creacion del SimpleMailMessage
//		    SimpleMailMessage email = new SimpleMailMessage();
//		    email.setTo(recipientAddress);
//		    email.setSubject(subject);
//		    email.setText(message);
		    
		    // creacion de mensaje
		    MimeMessage mimeMessage = mailSender.createMimeMessage();
		    MimeMessageHelper helper;
			try {
				helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
				helper.setSubject(subject);
				helper.setTo("dani.rodriguez.sev@gmail.com");
				String htmlMsg = "Bienvenido a la Plataforma de Gestión de Prácticas Externas."
						+ "<br /><br />"
						+ "Sus credenciales de usuario son:"
						+ "<br />"
						+ "<b>Usuario</b>: " + "paco" + "<br />"
		        		+ "<b>Contraseña</b>: " + actorService.generateSecureRandomPassword()
		        		+ "<br /><br />"
		        		+ "Puede acceder haciendo click " + "<a href='http://localhost:8080/Gestion-Practicas/security/login.do' target='_blank'>aquí</a>"; 
			    mimeMessage.setContent(htmlMsg, "text/html");			    
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    mailSender.send(mimeMessage);
		    
		     
		    // envia el email
//		    mailSender.send(email);        
    }
}
