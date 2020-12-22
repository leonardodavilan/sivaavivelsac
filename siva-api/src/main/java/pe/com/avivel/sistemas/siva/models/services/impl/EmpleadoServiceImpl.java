package pe.com.avivel.sistemas.siva.models.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.IEmpleadoDao;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Empleado;
import pe.com.avivel.sistemas.siva.models.services.spec.IEmpleadoService;

import java.util.List;

@Service
public class EmpleadoServiceImpl implements IEmpleadoService {

	@Autowired
	private IEmpleadoDao empleadoDao;

	@Override
	@Transactional(readOnly = true)
	public List<Empleado> findAll() {
		return (List<Empleado>) empleadoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Empleado> findAll(Pageable pageable) {
		return empleadoDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Empleado findById(Integer id) {
		return empleadoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Empleado save(Empleado proveedor) {

		return empleadoDao.save(proveedor);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		empleadoDao.deleteById(id);
	}

}
