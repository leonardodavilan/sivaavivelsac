package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.dto.StockInsumoDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.StockInsumo;

import java.util.List;

public interface IStockInsumoService {

	 List<StockInsumo> findAll();
	
	 Page<StockInsumo> findAll(Pageable pageable);

	StockInsumo findById(Integer id);

	StockInsumo save(StockInsumo StockInsumo);
	
	 void delete(Integer id);

	List<StockInsumoDTO> findAllByGranjaId(Integer granjaId);


}
