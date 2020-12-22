package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.dto.FiltroSolicitudDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Solicitud;

import java.util.List;

public interface ISolicitudService {

	 List<Solicitud> findAll();
	
	 Page<Solicitud> findAll(Pageable pageable);

	 Solicitud findById(Integer id);

	 Solicitud save(Solicitud solicitud);
	
	 void delete(Integer id);

	 List<Solicitud> findAllByFiltro(FiltroSolicitudDTO filtroSolicitudDTO);

	List<Solicitud> findAllByCodigo(Integer codigoSolicitud);

	Integer findMaxCodSolicitud();


}
