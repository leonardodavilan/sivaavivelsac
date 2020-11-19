package pe.com.avivel.sistemas.siva.models.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.IMetodoVacunaDao;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.MetodoVacuna;
import pe.com.avivel.sistemas.siva.models.services.spec.IMetodoVacunaService;

import java.util.List;

@Service
public class MetodoVacunaServiceImpl implements IMetodoVacunaService {

	@Autowired
	private IMetodoVacunaDao metodoVacunaDao;

	@Override
	@Transactional(readOnly = true)
	public List<MetodoVacuna> findAll() {
		return (List<MetodoVacuna>) metodoVacunaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<MetodoVacuna> findAll(Pageable pageable) {
		return metodoVacunaDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public MetodoVacuna findById(Integer id) {
		return metodoVacunaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public MetodoVacuna save(MetodoVacuna metodoVacuna) {
		return metodoVacunaDao.save(metodoVacuna);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		metodoVacunaDao.deleteById(id);
	}

}
