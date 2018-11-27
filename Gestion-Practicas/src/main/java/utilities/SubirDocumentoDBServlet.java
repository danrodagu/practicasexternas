package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import services.ActorService;
import services.UtilService;
 
@WebServlet("/uploadServlet")
@MultipartConfig(maxFileSize = 16177215)    // tamaño máximo de 16MB
public class SubirDocumentoDBServlet extends HttpServlet{
	// propiedades de la conexión a la bd
    private String dbURL = "jdbc:mysql://localhost:3306/Gestion-Practicas?useSSL=false";
    private String dbUser = "root";
    private String dbPass = "Lagarto665-";
    
   
    
    @Autowired
    private UtilService utilService;
    
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
    	
    	String titulo = request.getParameter("titulo");
    	String formato = titulo.split("\\.")[1].toLowerCase();
    	int uploader = actorService.findByPrincipal().getId();
         
        InputStream inputStream = null;
         
        // se obtiene el documento
        Part filePart = request.getPart("file");
        if (filePart != null) {
        	// información para debug
            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());
            
            inputStream = filePart.getInputStream();
        }
         
        Connection conn = null; 
        String message = null;  
         
        try {
            // conexión a la bd
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
 
            // se construye la query de inserción del documento
            String sql = "INSERT INTO documento (id, version, titulo, formato, archivo, uploader_id) values (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, utilService.maximoIdDB()+1);
            statement.setInt(2, 0);
            statement.setString(3, titulo);
            statement.setString(4, formato);
             
            if (inputStream != null) {                
                statement.setBlob(5, inputStream);
            }
            
            statement.setInt(6, uploader);
            
//            String sql = "INSERT INTO documento (version, titulo, formato, archivo, uploader_id) values (?, ?, ?, ?, ?)";
//            PreparedStatement statement = conn.prepareStatement(sql);
//            statement.setInt(1, 0);
//            statement.setString(2, titulo);
//            statement.setString(3, formato);
//             
//            if (inputStream != null) {                
//                statement.setBlob(4, inputStream);
//            }
//            
//            statement.setInt(5, uploader); 
            
            int row = statement.executeUpdate();
            if (row > 0) {
                message = "El documento se ha cargado con éxito";
            }
        } catch (SQLException ex) {
            message = "ERROR: " + ex.getMessage();
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                // cierra la conexión de la db
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            
            request.setAttribute("Message", message);
             
            // se redirecciona al listado tras la subida
            getServletContext().getRequestDispatcher("/documento/list.do").forward(request, response);
        }
    }
}
