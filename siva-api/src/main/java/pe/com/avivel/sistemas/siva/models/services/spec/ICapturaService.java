package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.dto.FiltroConsumoDTO;
import pe.com.avivel.sistemas.siva.models.entity.roedor.Captura;

import java.util.List;

public interface ICapturaService {

	List<Captura> findAll();
	
	Page<Captura> findAll(Pageable pageable);
	
	Captura findById(Integer id);
	
	Captura save(Captura captura);
	
	void delete(Integer id);

	List<Captura> findAllByFiltro(FiltroConsumoDTO filtroConsumoDTO);

}
