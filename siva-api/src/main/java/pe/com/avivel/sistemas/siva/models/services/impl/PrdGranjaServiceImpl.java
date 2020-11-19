package pe.com.avivel.sistemas.siva.models.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.IPrdGranjaDao;
import pe.com.avivel.sistemas.siva.models.entity.produccion.PrdGranja;
import pe.com.avivel.sistemas.siva.models.services.spec.IPrdGranjaService;

import java.util.List;

@Service
public class PrdGranjaServiceImpl implements IPrdGranjaService {

	@Autowired
	private IPrdGranjaDao prdGranjaDao;

	@Override
	@Transactional(readOnly = true)
	public List<PrdGranja> findAll() {
		return (List<PrdGranja>) prdGranjaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PrdGranja> findAll(Pageable pageable) {
		return prdGranjaDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public PrdGranja findById(Integer id) {
		return prdGranjaDao.findById(id).orElse(null);
	}



}
