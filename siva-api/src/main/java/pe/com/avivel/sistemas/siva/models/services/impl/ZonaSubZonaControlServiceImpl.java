package pe.com.avivel.sistemas.siva.models.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.IZonaSubZonaControlDao;
import pe.com.avivel.sistemas.siva.models.entity.roedor.ZonaSubZonaControl;
import pe.com.avivel.sistemas.siva.models.services.spec.IZonaSubZonaControlService;

import java.util.List;

@Service
public class ZonaSubZonaControlServiceImpl implements IZonaSubZonaControlService {

	@Autowired
	private IZonaSubZonaControlDao zonaSubZonaControlDao;

	@Override
	@Transactional(readOnly = true)
	public List<ZonaSubZonaControl> findAll() { return (List<ZonaSubZonaControl>) zonaSubZonaControlDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ZonaSubZonaControl> findAll(Pageable pageable) {
		return zonaSubZonaControlDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public ZonaSubZonaControl findById(Integer id) {
		return zonaSubZonaControlDao.findById(id).orElse(null);
	}

	@Override
	public List<ZonaSubZonaControl> findAllBySubZonaControl(Integer subZonaControlId) {
		return zonaSubZonaControlDao.findAllBySubZonaControl(subZonaControlId);
	}

	@Override
	@Transactional
	public ZonaSubZonaControl save(ZonaSubZonaControl zonaSubZonaControl) {
		return zonaSubZonaControlDao.save(zonaSubZonaControl);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		zonaSubZonaControlDao.deleteById(id);
	}

}
