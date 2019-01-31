package controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Documento;
import domain.Oferta;
import domain.Valoracion;
import forms.ActaForm;
import forms.DocumentoForm;
import forms.MensajeForm;
import services.ActorService;
import services.DocumentoService;
import services.MensajeService;
import services.OfertaService;
import services.UtilService;
import services.ValoracionService;

@Controller
@RequestMapping("/documento")
public class DocumentoController extends AbstractController {

	@PersistenceContext( unitName="Gestion-Practicas" )
	private EntityManager em;
	
	// Services ---------------------------------------------------------------

	@Autowired
	private DocumentoService documentoService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private OfertaService ofertaService;
	
	@Autowired
	private ValoracionService valoracionService;
	
	@Autowired
    private UtilService utilService;
	
	@Autowired
	private MensajeService	mensajeService;

	// Constructors -----------------------------------------------------------

	public DocumentoController() {
		super();
	}
	
	// Listing --------------------------------------
		@RequestMapping(value = "/list")
		public ModelAndView list(@RequestParam (required = true) final int ofertaId, final HttpServletRequest request) {
			ModelAndView result;
			Collection<Documento> documentos;
			Oferta oferta;
			boolean esAlumno;
			
			oferta = ofertaService.findOne(ofertaId);
			
			documentos = documentoService.findDocumentosByOferta(ofertaId);				
			
			result = new ModelAndView("documento/list");
			
			esAlumno = actorService.isAlumno();

			result.addObject("documentos", documentos);
			result.addObject("oferta", oferta);
			result.addObject("esAlumno", esAlumno);
			
			return result;
		}	


		// Save -------------------------------------------------------------------

		

		// Ancillary methods ------------------------------------------------------

		@RequestMapping(value = "/delete", method = RequestMethod.GET)
		public ModelAndView delete(@RequestParam(required = true) final int documentoId) {
			ModelAndView result;
			Documento documento;

			documento = this.documentoService.findOne(documentoId);
			
			result = new ModelAndView("redirect:/documento/list.do?ofertaId=" + documento.getOferta().getId());
			
			this.documentoService.delete(documento);					

			return result;
		}
		
		@RequestMapping(value = "/acta/create", method = RequestMethod.GET)
		public ModelAndView create(@RequestParam(required = true) final int ofertaId, final HttpServletRequest request) {
			ModelAndView result;
			ActaForm actaForm;
			
			actaForm = new ActaForm();
			actaForm.setIdOferta(ofertaId);

			result = new ModelAndView("documento/acta/create");
			result.addObject("actaForm", actaForm);

			return result;
		}
		
		@RequestMapping(value = "/acta/create", method = RequestMethod.POST, params="save")
		public ModelAndView create(@Valid final ActaForm actaForm, final BindingResult bindingResult) {
			ModelAndView result;
			Oferta oferta;
			Valoracion valoracion;
			MensajeForm mensajeForm;
			Map<String, Object> propiedades = em.getEntityManagerFactory().getProperties();
			String dominio = "";
			
			oferta = ofertaService.findOne(actaForm.getIdOferta());
			valoracion = valoracionService.findByOferta(oferta.getId());
			
			PDDocument pdfDocument;
			Connection conn = null;
		    try {
		    	String dbURL = "jdbc:mysql://localhost:3306/Gestion-Practicas?useSSL=false";
		        String dbUser = "root";
		        String dbPass = "USpracticas18";
		        
		        conn = DriverManager.getConnection(dbURL,dbUser,dbPass);
		    	
		        String workingDirectory = System.getProperty("user.dir");
		        
		        String absoluteFilePath = "";
		        String nombrePlantilla = "plantillaActa.pdf";
				
				//absoluteFilePath = workingDirectory + System.getProperty("file.separator") + filename;
				absoluteFilePath = workingDirectory + "\\src\\main\\webapp\\plantillas\\" + nombrePlantilla;
		        
				System.out.println(absoluteFilePath);
				
		        pdfDocument = PDDocument.load(new File(absoluteFilePath));

		        PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog();
		        PDAcroForm acroForm = docCatalog.getAcroForm();
		        
		        LocalDateTime fechaAux = LocalDateTime.now();
		        String fecha = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH).format(fechaAux);

		        documentoService.rellenarCampo(acroForm, "estudiante", oferta.getAlumnoAsignado().getNombreCompleto());
		        documentoService.rellenarCampo(acroForm, "dni", oferta.getAlumnoAsignado().getNif());
		        documentoService.rellenarCampo(acroForm, "curso", actaForm.getCurso());
		        documentoService.rellenarCampo(acroForm, "convocatoria", actaForm.getConvocatoria());
		        documentoService.rellenarCampo(acroForm, "titulacion", oferta.getAlumnoAsignado().getTitulacion());
		        documentoService.rellenarCampo(acroForm, "tutor", oferta.getTutorAsignado().getNombreCompleto());
		        documentoService.rellenarCampo(acroForm, "departamento", oferta.getTutorAsignado().getDepartamento());
		        if(oferta.getEsCurricular()) {
		        	documentoService.rellenarCampo(acroForm, "calificacion", valoracion.getNotaCurricular().toString());
		        }else {
		        	documentoService.rellenarCampo(acroForm, "calificacion", valoracion.getNotaExtracurricular());
		        }
		        documentoService.rellenarCampo(acroForm, "fecha", fecha);
		        
		        //Se guarda el pdf autogenerado temporalmente para poder obtenerlo como un array de bytes y almacenarlo en la BBDD
		        pdfDocument.save(workingDirectory + "\\src\\main\\webapp\\plantillas\\actaTemporal.pdf");
		        pdfDocument.close();
		        
		        String insertSQL = "INSERT INTO documento (id, version, titulo, formato, archivo, uploader_id, oferta_id) values (?, ?, ?, ?, ?, ?, ?)";
				 
		        PreparedStatement statement = conn.prepareStatement(insertSQL);			        
		        
		        statement.setInt(1, utilService.maximoIdDB()+1);
	            statement.setInt(2, 0);
	            statement.setString(3, "Acta.pdf");
	            statement.setString(4, "pdf");	             

	            //Se recupera el pdf como un array de bytes
	        	Path pdfPath = Paths.get(workingDirectory + "\\src\\main\\webapp\\plantillas\\actaTemporal.pdf");
	        	byte[] pdf = Files.readAllBytes(pdfPath);
	        	
	        	//Se elimina el pdf temporal previamente guardado
	        	Files.deleteIfExists(pdfPath); 
	            
	            if (pdf != null) {                
	                statement.setBytes(5, pdf);
	            }
	            
	            statement.setInt(6, actorService.findByPrincipal().getId());	            
	            
	            statement.setInt(7, oferta.getId());	            
		        
	            statement.executeUpdate();	            
	            
	            //Se notifica al tutor de que puede firmar el acta	    		
	    		dominio = propiedades.get("javax.persistence.jdbc.url").toString(); // jdbc:mysql://localhost:3306/Gestion-Practicas?useSSL=false
	    		dominio = dominio.substring(dominio.indexOf("jdbc:mysql://") + 13, dominio.indexOf("/Gestion-Practicas?useSSL=false"));
	            
	            mensajeForm = new MensajeForm();
	    		mensajeForm.setAsunto("PETICI�N DE FIRMA");
	    		mensajeForm.setCuerpo("Se requiere firmar por parte del tutor el acta disponible para la siguiente pr�ctica: http://" + dominio + "/Gestion-Practicas/oferta/display.do?ofertaId=" + oferta.getId() + 
	    				" \r\r Descargue el acta disponible en 'Documentos' y vu�lvala a subir firmada. La firma se puede realizar con Adobe Acrobat si se desea."
	    				+ "\r\r - Este mensaje ha sido generado autom�ticamente -");
	    		mensajeForm.setIdReceptor(oferta.getTutorAsignado().getId());
	    		
	    		this.mensajeService.createMensaje(mensajeForm);
	            
		    } catch (IOException | SQLException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }
		    
		    if (conn != null) {
                // cierra la conexi�n de la db
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

			result = new ModelAndView("welcome/index");
			result.addObject("message", "acta.administrativo.success");

			return result;
		}
		
		protected ModelAndView createEditModelAndView(final DocumentoForm documentoForm) {
			ModelAndView result;

			result = this.createEditModelAndView(documentoForm, null);

			return result;
		}

		protected ModelAndView createEditModelAndView(final DocumentoForm documentoForm, final String message) {
			ModelAndView result;

			result = new ModelAndView("documento/edit");
			result.addObject("documentoForm", documentoForm);
			result.addObject("message", message);

			return result;
		}

}
