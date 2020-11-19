package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Moneda;

import java.util.List;

public interface IMonedaService {

	 List<Moneda> findAll();
	
	 Page<Moneda> findAll(Pageable pageable);
	
	 Moneda findById(Integer id);
	
	 Moneda save(Moneda moneda);
	
	 void delete(Integer id);


}
