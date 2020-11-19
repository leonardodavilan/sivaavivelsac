package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.MetodoVacuna;

import java.util.List;

public interface IMetodoVacunaService {

	 List<MetodoVacuna> findAll();
	
	 Page<MetodoVacuna> findAll(Pageable pageable);

	MetodoVacuna findById(Integer id);

	MetodoVacuna save(MetodoVacuna metodoVacuna);
	
	 void delete(Integer id);


}
