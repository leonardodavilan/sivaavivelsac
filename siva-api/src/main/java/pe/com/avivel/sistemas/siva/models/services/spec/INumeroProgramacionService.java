package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.NumeroProgramacion;

import java.util.List;

public interface INumeroProgramacionService {

	List<NumeroProgramacion> findAll();
	
	Page<NumeroProgramacion> findAll(Pageable pageable);

	NumeroProgramacion findById(Integer id);

	NumeroProgramacion save(NumeroProgramacion numeroProgramacion);
	
	void delete(Integer id);


}
