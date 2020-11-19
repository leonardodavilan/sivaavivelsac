package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.dto.PrdLoteQueryDTO;
import pe.com.avivel.sistemas.siva.models.entity.produccion.PrdLote;

import java.util.List;

public interface IPrdLoteService {

	 List<PrdLote> findAll();
	
	 Page<PrdLote> findAll(Pageable pageable);

	PrdLote findById(Integer id);

	List<PrdLoteQueryDTO> getLotesByFiltro(String filtroLote);

	List<PrdLoteQueryDTO> getLotesByGranjaId(Integer granjaId);

}
