package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.dto.FiltroMovimientoDTO;
import pe.com.avivel.sistemas.siva.models.dto.MovimientoQueryDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Movimiento;

import java.util.List;

public interface IMovimientoService {

	 List<Movimiento> findAll();
	
	 Page<Movimiento> findAll(Pageable pageable);

	 Movimiento findById(Integer id);

	 Movimiento save(Movimiento movimiento);

	 void delete(Integer id);

	 List<MovimientoQueryDTO> findAllByFiltro(FiltroMovimientoDTO filtroMovimientoDTO);


}
