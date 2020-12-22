package pe.com.avivel.sistemas.siva.models.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.IAreaCapturaDao;
import pe.com.avivel.sistemas.siva.models.entity.roedor.AreaCaptura;
import pe.com.avivel.sistemas.siva.models.services.spec.IAreaCapturaService;

import java.util.List;

@Service
public class AreaCapturaImpl implements IAreaCapturaService {

	@Autowired
	private IAreaCapturaDao areaCapturaDao;

	@Override
	@Transactional(readOnly = true)
	public List<AreaCaptura> findAll() {
		return (List<AreaCaptura>) areaCapturaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<AreaCaptura> findAll(Pageable pageable) {
		return areaCapturaDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AreaCaptura findById(Integer id) {
		return areaCapturaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public AreaCaptura save(AreaCaptura areaCaptura) {
		return areaCapturaDao.save(areaCaptura);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		areaCapturaDao.deleteById(id);
	}

}
