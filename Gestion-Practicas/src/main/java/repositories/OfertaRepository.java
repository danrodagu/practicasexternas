
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Oferta;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, Integer> {

	// Obtiene oferta por empresa
	@Query("select o from Oferta o where o.empresa = ?1")
	Collection<Oferta> ofertasByEmpresa(String empresa);
	
	// Obtiene oferta por pais
	@Query("select o from Oferta o where o.pais = ?1")
	Collection<Oferta> ofertasByPais(String pais);
	
	// Obtiene ofertas por alumno
	@Query("SELECT o FROM Oferta o where o.alumnoAsignado.id = ?1")
	Collection<Oferta> ofertasByAlumno(int idAlumno);
	
	// Obtiene ofertas por alumno y tutor
	@Query("SELECT o FROM Oferta o where o.alumnoAsignado.id = ?1 AND o.tutorAsignado.id = ?2")
	Collection<Oferta> ofertasByAlumnoTutor(int idAlumno, int idTutor);
	
	// Obtiene ofertas curriculares por alumno
	@Query("SELECT o FROM Oferta o where o.esCurricular = 1 AND o.alumnoAsignado.id = ?1")
	Collection<Oferta> ofertasCurricByAlumno(int idAlumno);
	
	// Obtiene ofertas extracurriculares por alumno
	@Query("SELECT o FROM Oferta o where o.esCurricular = 0 AND o.alumnoAsignado.id = ?1")
	Collection<Oferta> ofertasExtraByAlumno(int idAlumno);
	
	// Obtiene ofertas curriculares por alumno excepto la indicada como parámetro
	@Query("SELECT o FROM Oferta o where o.esCurricular = 1 AND o.alumnoAsignado.id = ?1 AND o.id != ?2")
	Collection<Oferta> ofertasCurricByAlumnoEdit(int idAlumno, int idOferta);
	
	// Obtiene ofertas extracurriculares por alumno excepto la indicada como parámetro
	@Query("SELECT o FROM Oferta o where o.esCurricular = 0 AND o.alumnoAsignado.id = ?1 AND o.id != ?2")
	Collection<Oferta> ofertasExtraByAlumnoEdit(int idAlumno, int idOferta);
	
	// Obtiene maximo id de las ofertas
	@Query("SELECT MAX(a.id) FROM Oferta a")
	Integer maxOfertaId();
	
	
}
