package pe.com.avivel.sistemas.siva.models.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.ITipoCeboDao;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.TipoCebo;
import pe.com.avivel.sistemas.siva.models.services.spec.ITipoCeboService;

import java.util.List;

@Service
public class TipoCeboServiceImpl implements ITipoCeboService {

	@Autowired
	private ITipoCeboDao tipoCeboDao;

	@Override
	@Transactional(readOnly = true)
	public List<TipoCebo> findAll() {
		return (List<TipoCebo>) tipoCeboDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TipoCebo> findAll(Pageable pageable) {
		return tipoCeboDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public TipoCebo findById(Integer id) {
		return tipoCeboDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public TipoCebo save(TipoCebo tipoCebo) {
		return tipoCeboDao.save(tipoCebo);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		tipoCeboDao.deleteById(id);
	}

}
