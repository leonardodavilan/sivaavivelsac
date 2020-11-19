package pe.com.avivel.sistemas.siva.models.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.IPresentacionDao;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Presentacion;
import pe.com.avivel.sistemas.siva.models.services.spec.IPresentacionService;

import java.util.List;

@Service
public class PresentacionServiceImpl implements IPresentacionService {

	@Autowired
	private IPresentacionDao presentacionDao;

	@Override
	@Transactional(readOnly = true)
	public List<Presentacion> findAll() {
		return (List<Presentacion>) presentacionDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Presentacion> findAll(Pageable pageable) {
		return presentacionDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Presentacion findById(Integer id) {
		return presentacionDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Presentacion save(Presentacion presentacion) {
		return presentacionDao.save(presentacion);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		presentacionDao.deleteById(id);
	}

}
