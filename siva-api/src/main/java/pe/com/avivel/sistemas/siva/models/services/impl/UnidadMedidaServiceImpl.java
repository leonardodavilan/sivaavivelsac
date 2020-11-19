package pe.com.avivel.sistemas.siva.models.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.IUnidadMedidaDao;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.UnidadMedida;
import pe.com.avivel.sistemas.siva.models.services.spec.IUnidadMedidaService;

import java.util.List;

@Service
public class UnidadMedidaServiceImpl implements IUnidadMedidaService {

	@Autowired
	private IUnidadMedidaDao unidadMedidaDao;

	@Override
	@Transactional(readOnly = true)
	public List<UnidadMedida> findAll() {
		return (List<UnidadMedida>) unidadMedidaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<UnidadMedida> findAll(Pageable pageable) {
		return unidadMedidaDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public UnidadMedida findById(Integer id) {
		return unidadMedidaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public UnidadMedida save(UnidadMedida unidadMedida) {
		return unidadMedidaDao.save(unidadMedida);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		unidadMedidaDao.deleteById(id);
	}

}
