package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.UnidadMedida;

import java.util.List;

public interface IUnidadMedidaService {

	public List<UnidadMedida> findAll();
	
	public Page<UnidadMedida> findAll(Pageable pageable);
	
	public UnidadMedida findById(Integer id);
	
	public UnidadMedida save(UnidadMedida unidadMedida);
	
	public void delete(Integer id);


}
