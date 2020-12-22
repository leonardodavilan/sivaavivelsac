package pe.com.avivel.sistemas.siva.models.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.ISubZonaControlDao;
import pe.com.avivel.sistemas.siva.models.entity.roedor.SubZonaControl;
import pe.com.avivel.sistemas.siva.models.services.spec.ISubZonaControlService;

import java.util.List;

@Service
public class SubZonaControlServiceImpl implements ISubZonaControlService {

	@Autowired
	private ISubZonaControlDao subZonaControlDao;

	@Override
	@Transactional(readOnly = true)
	public List<SubZonaControl> findAll() {
		return (List<SubZonaControl>) subZonaControlDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<SubZonaControl> findAll(Pageable pageable) {
		return subZonaControlDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public SubZonaControl findById(Integer id) {
		return subZonaControlDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public SubZonaControl save(SubZonaControl proveedor) {
		return subZonaControlDao.save(proveedor);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		subZonaControlDao.deleteById(id);
	}

}
