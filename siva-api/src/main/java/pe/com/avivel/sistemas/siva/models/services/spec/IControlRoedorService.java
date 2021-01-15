package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.dto.FiltroConsumoDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.ControlRoedor;

import java.util.List;

public interface IControlRoedorService {

	List<ControlRoedor> findAll();
	
	Page<ControlRoedor> findAll(Pageable pageable);

	ControlRoedor findById(Integer id);

	ControlRoedor save(ControlRoedor controlRoedor);
	
	void delete(Integer id);

	List<ControlRoedor> findAllByFiltro(FiltroConsumoDTO filtroConsumoDTO);

}
