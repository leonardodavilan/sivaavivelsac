package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.UnidadMedida;

import java.util.List;

public interface IUnidadMedidaService {

	List<UnidadMedida> findAll();
	
	Page<UnidadMedida> findAll(Pageable pageable);
	
	UnidadMedida findById(Integer id);
	
	UnidadMedida save(UnidadMedida unidadMedida);
	
	void delete(Integer id);


}
