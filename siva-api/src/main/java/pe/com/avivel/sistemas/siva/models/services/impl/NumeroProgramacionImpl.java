package pe.com.avivel.sistemas.siva.models.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.INumeroProgramacionDao;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.NumeroProgramacion;
import pe.com.avivel.sistemas.siva.models.services.spec.INumeroProgramacionService;

import java.util.List;

@Service
public class NumeroProgramacionImpl implements INumeroProgramacionService {

	@Autowired
	private INumeroProgramacionDao numeroProgramacionDao;

	@Override
	@Transactional(readOnly = true)
	public List<NumeroProgramacion> findAll() {
		return (List<NumeroProgramacion>) numeroProgramacionDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<NumeroProgramacion> findAll(Pageable pageable) {
		return numeroProgramacionDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public NumeroProgramacion findById(Integer id) {
		return numeroProgramacionDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public NumeroProgramacion save(NumeroProgramacion numeroProgramacion) {
		return numeroProgramacionDao.save(numeroProgramacion);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		numeroProgramacionDao.deleteById(id);
	}

}
