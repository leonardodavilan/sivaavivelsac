package pe.com.avivel.sistemas.siva.models.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.ITipoMovimientoDao;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.TipoMovimiento;
import pe.com.avivel.sistemas.siva.models.services.spec.ITipoMovimientoService;

import java.util.List;

@Service
public class TipoMovimientoServiceImpl implements ITipoMovimientoService {

	@Autowired
	private ITipoMovimientoDao tipoMovimientoDao;

	@Override
	@Transactional(readOnly = true)
	public List<TipoMovimiento> findAll() {
		return (List<TipoMovimiento>) tipoMovimientoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TipoMovimiento> findAll(Pageable pageable) {
		return tipoMovimientoDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public TipoMovimiento findById(Integer id) {
		return tipoMovimientoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public TipoMovimiento save(TipoMovimiento tipoMovimiento) {
		return tipoMovimientoDao.save(tipoMovimiento);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		tipoMovimientoDao.deleteById(id);
	}

}
