package pe.com.avivel.sistemas.siva.models.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.IMonedaDao;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Moneda;
import pe.com.avivel.sistemas.siva.models.services.spec.IMonedaService;

import java.util.List;

@Service
public class MonedaServiceImpl implements IMonedaService {

	@Autowired
	private IMonedaDao monedaDao;

	@Override
	@Transactional(readOnly = true)
	public List<Moneda> findAll() {
		return (List<Moneda>) monedaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Moneda> findAll(Pageable pageable) {
		return monedaDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Moneda findById(Integer id) {
		return monedaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Moneda save(Moneda moneda) {
		return monedaDao.save(moneda);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		monedaDao.deleteById(id);
	}

}
