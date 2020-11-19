package pe.com.avivel.sistemas.siva.models.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.ITiempoProgramacionDao;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.TiempoProgramacion;
import pe.com.avivel.sistemas.siva.models.services.spec.ITiempoProgramacionService;

import java.util.List;

@Service
public class TiempoProgramacionImpl implements ITiempoProgramacionService {

	@Autowired
	private ITiempoProgramacionDao tiempoProgramacionDao;

	@Override
	@Transactional(readOnly = true)
	public List<TiempoProgramacion> findAll() {
		return (List<TiempoProgramacion>) tiempoProgramacionDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TiempoProgramacion> findAll(Pageable pageable) {
		return tiempoProgramacionDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public TiempoProgramacion findById(Integer id) {
		return tiempoProgramacionDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public TiempoProgramacion save(TiempoProgramacion tiempoProgramacion) {
		return tiempoProgramacionDao.save(tiempoProgramacion);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		tiempoProgramacionDao.deleteById(id);
	}

}
