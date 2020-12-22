package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.entity.roedor.Sexo;


import java.util.List;

public interface ISexoService {

	 List<Sexo> findAll();
	
	 Page<Sexo> findAll(Pageable pageable);

	Sexo findById(Integer id);

	Sexo save(Sexo sexo);
	
	 void delete(Integer id);


}
