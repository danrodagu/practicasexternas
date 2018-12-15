package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
@WebServlet("/downloadServlet")
public class DescargarDocumentoDBServlet extends HttpServlet{
	// tamaño de buffer
    private static final int BUFFER_SIZE = 4096; 
	
	// propiedades de la conexión a la bd
    private String dbURL = "jdbc:mysql://localhost:3306/Gestion-Practicas?useSSL=false";
    private String dbUser = "root";
    private String dbPass = "USpracticas18"; 
     
//    @Override
//	public void init(final ServletConfig config) throws ServletException {
//        super.init(config);
//        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
//          config.getServletContext());
//      }
//    
    @Override
	protected void doGet(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException, IOException {    	
    	// id del documento a descargar
        int documentoId = Integer.parseInt(request.getParameter("id"));
         
        Connection conn = null; // connection to the database
         
        try {
            // conecta a la bd
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
 
            // query para obtener el documento a descargar
            String sql = "SELECT * FROM documento WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, documentoId);
 
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                String fileName = result.getString("titulo");
                Blob blob = result.getBlob("archivo");
                InputStream inputStream = blob.getBinaryStream();
                int fileLength = inputStream.available();
                 
                System.out.println("fileLength = " + fileLength);
 
                ServletContext context = getServletContext();
 
                // se setea el MIME type para el archivo a descargar
                String mimeType = context.getMimeType(fileName);
                if (mimeType == null) {        
                    mimeType = "application/octet-stream";
                }              
                 
                // atributos de la respuesta http
                response.setContentType(mimeType);
                response.setContentLength(fileLength);
                String headerKey = "Content-Disposition";
                String headerValue = String.format("attachment; filename=\"%s\"", fileName);
                response.setHeader(headerKey, headerValue);
 
                // escribe el archivo al cliente
                OutputStream outStream = response.getOutputStream();
                 
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead = -1;
                 
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }
                 
                inputStream.close();
                outStream.close();             
            } else {
                // archivo no encontrado
                response.getWriter().print("No se encuentra archivo con id: " + documentoId);  
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            response.getWriter().print("SQL Error: " + ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
            response.getWriter().print("IO Error: " + ex.getMessage());
        } finally {
            if (conn != null) {
                // cierre de conexion de bd
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }          
        }
    }
}
