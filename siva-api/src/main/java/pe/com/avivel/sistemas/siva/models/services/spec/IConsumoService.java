package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.dto.FiltroConsumoDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Consumo;

import java.util.List;

public interface IConsumoService {

	List<Consumo> findAll();
	
	Page<Consumo> findAll(Pageable pageable);
	
	Consumo findById(Integer id);
	
	Consumo save(Consumo proveedor);
	
	void delete(Integer id);

	List<Consumo> findAllByFiltro(FiltroConsumoDTO filtroConsumoDTO);

}
