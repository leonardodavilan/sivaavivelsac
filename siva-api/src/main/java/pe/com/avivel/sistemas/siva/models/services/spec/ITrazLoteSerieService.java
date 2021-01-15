package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.LoteSerie;

import java.util.List;

public interface ITrazLoteSerieService {

	List<LoteSerie> findAll();

	Page<LoteSerie> findAll(Pageable pageable);

	LoteSerie findById(Integer id);

	LoteSerie save(LoteSerie loteSerie);

	void delete(Integer id);

	List<LoteSerie> findAllByIsumoFromStock(Integer insumoId);

	List<LoteSerie> findAllByFamiliaFromStock(Integer insumoId);


}
