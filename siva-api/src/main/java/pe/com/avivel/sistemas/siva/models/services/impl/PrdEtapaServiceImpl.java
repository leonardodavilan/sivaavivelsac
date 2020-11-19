package pe.com.avivel.sistemas.siva.models.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.IPrdEtapaDao;
import pe.com.avivel.sistemas.siva.models.entity.produccion.PrdEtapa;
import pe.com.avivel.sistemas.siva.models.services.spec.IPrdEtapaService;

import java.util.List;

@Service
public class PrdEtapaServiceImpl implements IPrdEtapaService {

	@Autowired
	private IPrdEtapaDao prdEtapaDao;

	@Override
	@Transactional(readOnly = true)
	public List<PrdEtapa> findAll() {
		return (List<PrdEtapa>) prdEtapaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PrdEtapa> findAll(Pageable pageable) {
		return prdEtapaDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public PrdEtapa findById(Integer id) {
		return prdEtapaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public PrdEtapa save(PrdEtapa moneda) {
		return prdEtapaDao.save(moneda);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		prdEtapaDao.deleteById(id);
	}

}
