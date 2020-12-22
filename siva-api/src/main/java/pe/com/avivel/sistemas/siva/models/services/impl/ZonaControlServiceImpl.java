package pe.com.avivel.sistemas.siva.models.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.IZonaControlDao;
import pe.com.avivel.sistemas.siva.models.entity.roedor.ZonaControl;
import pe.com.avivel.sistemas.siva.models.services.spec.IZonaControlService;

import java.util.List;

@Service
public class ZonaControlServiceImpl implements IZonaControlService {

	@Autowired
	private IZonaControlDao zonaControlDao;

	@Override
	@Transactional(readOnly = true)
	public List<ZonaControl> findAll() {
		return (List<ZonaControl>) zonaControlDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ZonaControl> findAll(Pageable pageable) {
		return zonaControlDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ZonaControl findById(Integer id) {
		return zonaControlDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public ZonaControl save(ZonaControl proveedor) {

		return zonaControlDao.save(proveedor);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		zonaControlDao.deleteById(id);
	}

}
