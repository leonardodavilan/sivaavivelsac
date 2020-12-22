package pe.com.avivel.sistemas.siva.models.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.IControlRoedorDao;
import pe.com.avivel.sistemas.siva.models.dto.FiltroConsumoDTO;
import pe.com.avivel.sistemas.siva.models.entity.roedor.ControlRoedor;
import pe.com.avivel.sistemas.siva.models.services.spec.IControlRoedorService;

import java.util.List;

@Service
public class ControlRoedorServiceImpl implements IControlRoedorService {

	@Autowired
	private IControlRoedorDao controlRoedorDao;

	@Override
	@Transactional(readOnly = true)
	public List<ControlRoedor> findAll() {
		return (List<ControlRoedor>) controlRoedorDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ControlRoedor> findAll(Pageable pageable) {
		return controlRoedorDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ControlRoedor findById(Integer id) {
		return controlRoedorDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public ControlRoedor save(ControlRoedor controlRoedor) {

		return controlRoedorDao.save(controlRoedor);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		controlRoedorDao.deleteById(id);
	}

	@Override
	public List<ControlRoedor> findAllByFiltro(FiltroConsumoDTO filtroConsumoDTO) {
		return controlRoedorDao.findAllByFiltro(filtroConsumoDTO);
	}

}
