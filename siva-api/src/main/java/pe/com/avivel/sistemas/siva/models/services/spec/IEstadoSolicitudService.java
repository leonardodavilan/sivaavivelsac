package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.EstadoSolicitud;

import java.util.List;

public interface IEstadoSolicitudService {

	 List<EstadoSolicitud> findAll();
	
	 Page<EstadoSolicitud> findAll(Pageable pageable);

	EstadoSolicitud findById(Integer id);

	EstadoSolicitud save(EstadoSolicitud estadoSolicitud);
	
	 void delete(Integer id);


}
