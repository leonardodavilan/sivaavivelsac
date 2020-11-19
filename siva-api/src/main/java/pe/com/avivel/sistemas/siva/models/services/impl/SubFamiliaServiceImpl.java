package pe.com.avivel.sistemas.siva.models.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.ISubfamiliaDao;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.SubFamilia;
import pe.com.avivel.sistemas.siva.models.services.spec.ISubFamiliaService;

import java.util.List;

@Service
public class SubFamiliaServiceImpl implements ISubFamiliaService {

	@Autowired
	private ISubfamiliaDao subfamiliaDao;

	@Override
	@Transactional(readOnly = true)
	public List<SubFamilia> findAll() {
		return (List<SubFamilia>) subfamiliaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<SubFamilia> findAll(Pageable pageable) {
		return subfamiliaDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public SubFamilia findById(Integer id) {
		return subfamiliaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public SubFamilia save(SubFamilia subFamilia) {
		return subfamiliaDao.save(subFamilia);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		subfamiliaDao.deleteById(id);
	}

}
