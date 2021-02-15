package pe.com.avivel.sistemas.siva.models.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.ILoteFechaProgramacionDao;
import pe.com.avivel.sistemas.siva.models.dto.FiltroVacunacionDTO;
import pe.com.avivel.sistemas.siva.models.entity.produccion.LoteFechaProgramacion;
import pe.com.avivel.sistemas.siva.models.services.spec.ILoteFechaProgramacionService;

import java.math.BigDecimal;
import java.util.List;

@Service
public class LoteFechaProgramadaServiceImpl implements ILoteFechaProgramacionService {

	@Autowired
	private ILoteFechaProgramacionDao loteFechaProgramacionDao;

	@Override
	@Transactional(readOnly = true)
	public List<LoteFechaProgramacion> findAll() {
		return (List<LoteFechaProgramacion>) loteFechaProgramacionDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<LoteFechaProgramacion> findAll(Pageable pageable) {
		return loteFechaProgramacionDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public LoteFechaProgramacion findById(BigDecimal id) {
		return loteFechaProgramacionDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<LoteFechaProgramacion> findByInsumo(Integer id){
		return loteFechaProgramacionDao.findByInsumo(id);
	}

	@Override
	public List<LoteFechaProgramacion> findAllByFiltroLote(FiltroVacunacionDTO filtroVacunacionDTO) {
		return loteFechaProgramacionDao.findAllByFiltroLote(filtroVacunacionDTO);
	}
	@Override
	public List<LoteFechaProgramacion> findAllByFullFiltroFechaVac(FiltroVacunacionDTO filtroVacunacionDTO) {
		return loteFechaProgramacionDao.findAllByFullFiltroFechaVac(filtroVacunacionDTO);
	}

	@Override
	public List<LoteFechaProgramacion> findAllByFullFiltroFechaProg(FiltroVacunacionDTO filtroVacunacionDTO) {
		return loteFechaProgramacionDao.findAllByFullFiltroFechaProg(filtroVacunacionDTO);
	}

	@Override
	public List<LoteFechaProgramacion> findAllByFiltroLoteOnlyFechVac(FiltroVacunacionDTO filtroVacunacionDTO) {
		return loteFechaProgramacionDao.findAllByFiltroLoteOnlyFechVac(filtroVacunacionDTO);
	}

}
