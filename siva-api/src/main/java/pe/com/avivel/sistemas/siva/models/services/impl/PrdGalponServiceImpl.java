package pe.com.avivel.sistemas.siva.models.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.IPrdGalponDao;
import pe.com.avivel.sistemas.siva.models.entity.produccion.PrdGalpon;
import pe.com.avivel.sistemas.siva.models.services.spec.IPrdGalponService;

import java.util.List;

@Service
public class PrdGalponServiceImpl implements IPrdGalponService {

	@Autowired
	private IPrdGalponDao prdGalponDao;

	@Override
	@Transactional(readOnly = true)
	public List<PrdGalpon> findAll() {
		return (List<PrdGalpon>) prdGalponDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PrdGalpon> findAll(Pageable pageable) {
		return prdGalponDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public PrdGalpon findById(Integer id) {
		return prdGalponDao.findById(id).orElse(null);
	}


}
