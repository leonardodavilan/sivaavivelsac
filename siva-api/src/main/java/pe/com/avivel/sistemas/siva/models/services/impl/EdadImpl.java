package pe.com.avivel.sistemas.siva.models.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.IEdadDao;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Edad;
import pe.com.avivel.sistemas.siva.models.services.spec.IEdadService;

import java.util.List;

@Service
public class EdadImpl implements IEdadService {

	@Autowired
	private IEdadDao edadDao;

	@Override
	@Transactional(readOnly = true)
	public List<Edad> findAll() {
		return (List<Edad>) edadDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Edad> findAll(Pageable pageable) {
		return edadDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Edad findById(Integer id) {
		return edadDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Edad save(Edad edad) {
		return edadDao.save(edad);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		edadDao.deleteById(id);
	}

}
