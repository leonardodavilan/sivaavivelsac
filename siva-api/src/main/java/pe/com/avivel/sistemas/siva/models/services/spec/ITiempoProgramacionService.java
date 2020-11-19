package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.TiempoProgramacion;

import java.util.List;

public interface ITiempoProgramacionService {

	List<TiempoProgramacion> findAll();
	
	Page<TiempoProgramacion> findAll(Pageable pageable);

	TiempoProgramacion findById(Integer id);

	TiempoProgramacion save(TiempoProgramacion tiempoProgramacion);
	
	void delete(Integer id);


}
