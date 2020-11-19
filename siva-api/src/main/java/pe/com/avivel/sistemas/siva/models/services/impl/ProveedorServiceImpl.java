package pe.com.avivel.sistemas.siva.models.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.IProveedorDao;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Proveedor;
import pe.com.avivel.sistemas.siva.models.services.spec.IProveedorService;

import java.util.List;

@Service
public class ProveedorServiceImpl implements IProveedorService {

	@Autowired
	private IProveedorDao proveedorDao;

	@Override
	@Transactional(readOnly = true)
	public List<Proveedor> findAll() {
		return (List<Proveedor>) proveedorDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Proveedor> findAll(Pageable pageable) {
		return proveedorDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Proveedor findById(Integer id) {
		return proveedorDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Proveedor save(Proveedor proveedor) {

		return proveedorDao.save(proveedor);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		proveedorDao.deleteById(id);
	}

}
