package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.TipoMovimiento;

import java.util.List;

public interface ITipoMovimientoService {

	 List<TipoMovimiento> findAll();
	
	 Page<TipoMovimiento> findAll(Pageable pageable);
	
	 TipoMovimiento findById(Integer id);
	
	 TipoMovimiento save(TipoMovimiento tipoMovimiento);
	
	 void delete(Integer id);


}
