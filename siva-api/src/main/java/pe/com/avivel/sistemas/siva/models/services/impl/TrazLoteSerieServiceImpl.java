package pe.com.avivel.sistemas.siva.models.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.ITrazLoteSerieDao;
import pe.com.avivel.sistemas.siva.models.entity.trazabilidad.LoteSerie;
import pe.com.avivel.sistemas.siva.models.services.spec.ITrazLoteSerieService;

import java.util.List;

@Service
public class TrazLoteSerieServiceImpl implements ITrazLoteSerieService {

	@Autowired
	private ITrazLoteSerieDao trazLoteSerieDao;

	@Override
	@Transactional(readOnly = true)
	public List<LoteSerie> findAll() {
		return (List<LoteSerie>) trazLoteSerieDao.findAll();
	}

	@Override
	public Page<LoteSerie> findAll(Pageable pageable) {
		return null;
	}

	@Override
	public LoteSerie findById(Integer id) {
		return trazLoteSerieDao.findById(id).orElse(null);
	}

	@Override
	public LoteSerie save(LoteSerie loteSerie) {
		return trazLoteSerieDao.save(loteSerie);
	}

	@Override
	public void delete(Integer id) {
		trazLoteSerieDao.deleteById(id);
	}

	@Override
	public List<LoteSerie> findAllByIsumoFromStock(Integer insumoId) {
		return trazLoteSerieDao.findAllByIsumoFromStock(insumoId);
	}

	@Override
	public List<LoteSerie> findAllByFamiliaFromStock(Integer familiaId) {
		return trazLoteSerieDao.findAllByFamiliaFromStock(familiaId);
	}

}
