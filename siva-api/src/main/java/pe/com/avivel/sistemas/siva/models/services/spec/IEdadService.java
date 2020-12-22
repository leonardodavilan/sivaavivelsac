package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.entity.roedor.Edad;

import java.util.List;

public interface IEdadService {

	 List<Edad> findAll();
	
	 Page<Edad> findAll(Pageable pageable);

	Edad findById(Integer id);

	Edad save(Edad edad);
	
	 void delete(Integer id);


}
