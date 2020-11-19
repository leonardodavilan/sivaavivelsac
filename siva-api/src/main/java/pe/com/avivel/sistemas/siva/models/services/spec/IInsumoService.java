package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.dto.InsumoDTO;
import pe.com.avivel.sistemas.siva.models.dto.InsumoDetalladoDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Insumo;

import java.util.List;

public interface IInsumoService {

	List<Insumo> findAll();

	Page<Insumo> findAll(Pageable pageable);

	Insumo findById(Integer id);

	Insumo save(InsumoDTO insumoDTO);

	void delete(Integer id);

	Integer saveInsumoMultiple(InsumoDTO insumoDTO);

	Integer saveInsumoDetallado(InsumoDetalladoDTO insumoDetalladoDTO);

	List<Insumo> findAllByTipo(Integer tipoInsumoId);

}
