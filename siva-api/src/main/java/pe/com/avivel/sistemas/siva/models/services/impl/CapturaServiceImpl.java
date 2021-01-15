package pe.com.avivel.sistemas.siva.models.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.ICapturaDao;
import pe.com.avivel.sistemas.siva.models.dto.FiltroConsumoDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Captura;
import pe.com.avivel.sistemas.siva.models.services.spec.ICapturaService;

import java.util.List;

@Service
public class CapturaServiceImpl implements ICapturaService {

	@Autowired
	private ICapturaDao capturaDao;

	@Override
	@Transactional(readOnly = true)
	public List<Captura> findAll() {
		return (List<Captura>) capturaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Captura> findAll(Pageable pageable) {
		return capturaDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Captura findById(Integer id) {
		return capturaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Captura save(Captura captura) {

		return capturaDao.save(captura);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		capturaDao.deleteById(id);
	}


	@Override
	public List<Captura> findAllByFiltro(FiltroConsumoDTO filtroConsumoDTO) {
		return capturaDao.findAllByFiltro(filtroConsumoDTO);
	}

}
