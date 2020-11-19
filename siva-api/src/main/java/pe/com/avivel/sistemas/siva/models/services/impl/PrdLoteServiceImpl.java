package pe.com.avivel.sistemas.siva.models.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.IPrdLoteDao;
import pe.com.avivel.sistemas.siva.models.dto.PrdLoteQueryDTO;
import pe.com.avivel.sistemas.siva.models.entity.produccion.PrdLote;
import pe.com.avivel.sistemas.siva.models.services.spec.IPrdLoteService;


import java.util.ArrayList;
import java.util.List;

@Service
public class PrdLoteServiceImpl implements IPrdLoteService {

	@Autowired
	private IPrdLoteDao prdLoteDao;

	@Override
	@Transactional(readOnly = true)
	public List<PrdLote> findAll() {
		return (List<PrdLote>) prdLoteDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PrdLote> findAll(Pageable pageable) {
		return prdLoteDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public PrdLote findById(Integer id) {
		return prdLoteDao.findById(id).orElse(null);
	}

	@Override
	public List<PrdLoteQueryDTO> getLotesByFiltro(String filtroLote) {
		List<PrdLoteQueryDTO> lotesQueryDTO = new ArrayList<>();
		prdLoteDao.findAllByFiltro(filtroLote).forEach(lote -> lotesQueryDTO.add(PrdLoteQueryDTO.getInstance(lote)));
		return lotesQueryDTO;
	}

	@Override
	public List<PrdLoteQueryDTO> getLotesByGranjaId(Integer granjaId) {
		List<PrdLoteQueryDTO> lotesQueryDTO = new ArrayList<>();
		prdLoteDao.findAllByGranjaId(granjaId).forEach(lote -> lotesQueryDTO.add(PrdLoteQueryDTO.getInstance(lote)));
		return lotesQueryDTO;
	}
}
