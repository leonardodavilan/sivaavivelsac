package pe.com.avivel.sistemas.siva.models.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.ISexoDao;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Sexo;
import pe.com.avivel.sistemas.siva.models.services.spec.ISexoService;

import java.util.List;

@Service
public class SexoServiceImpl implements ISexoService {

	@Autowired
	private ISexoDao sexoDao;

	@Override
	@Transactional(readOnly = true)
	public List<Sexo> findAll() {
		return (List<Sexo>) sexoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Sexo> findAll(Pageable pageable) {
		return sexoDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Sexo findById(Integer id) {
		return sexoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Sexo save(Sexo sexo) {
		return sexoDao.save(sexo);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		sexoDao.deleteById(id);
	}

}
