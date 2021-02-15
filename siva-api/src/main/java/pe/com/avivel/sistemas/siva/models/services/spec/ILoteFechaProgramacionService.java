package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.dto.FiltroVacunacionDTO;
import pe.com.avivel.sistemas.siva.models.entity.produccion.LoteFechaProgramacion;

import java.math.BigDecimal;
import java.util.List;

public interface ILoteFechaProgramacionService {

	 List<LoteFechaProgramacion> findAll();

	 Page<LoteFechaProgramacion> findAll(Pageable pageable);

	LoteFechaProgramacion findById(BigDecimal id);

	List<LoteFechaProgramacion> findByInsumo(Integer id);

	List<LoteFechaProgramacion> findAllByFiltroLote(FiltroVacunacionDTO filtroVacunacionDTO);

	List<LoteFechaProgramacion> findAllByFullFiltroFechaVac(FiltroVacunacionDTO filtroVacunacionDTO);

	List<LoteFechaProgramacion> findAllByFullFiltroFechaProg(FiltroVacunacionDTO filtroVacunacionDTO);

	List<LoteFechaProgramacion> findAllByFiltroLoteOnlyFechVac(FiltroVacunacionDTO filtroVacunacionDTO);



}
