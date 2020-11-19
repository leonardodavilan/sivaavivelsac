package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.entity.produccion.PrdGranja;

import java.util.List;

public interface IPrdGranjaService {

	 List<PrdGranja> findAll();
	
	 Page<PrdGranja> findAll(Pageable pageable);

	PrdGranja findById(Integer id);


}
