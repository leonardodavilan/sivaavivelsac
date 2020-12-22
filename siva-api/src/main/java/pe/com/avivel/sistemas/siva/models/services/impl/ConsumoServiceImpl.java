package pe.com.avivel.sistemas.siva.models.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.IConsumoDao;
import pe.com.avivel.sistemas.siva.models.dto.FiltroConsumoDTO;
import pe.com.avivel.sistemas.siva.models.entity.roedor.Consumo;
import pe.com.avivel.sistemas.siva.models.services.spec.IConsumoService;

import java.util.List;

@Service
public class ConsumoServiceImpl implements IConsumoService {

	@Autowired
	private IConsumoDao consumoDao;

	@Override
	@Transactional(readOnly = true)
	public List<Consumo> findAll() {
		return (List<Consumo>) consumoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Consumo> findAll(Pageable pageable) {
		return consumoDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Consumo findById(Integer id) {
		return consumoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Consumo save(Consumo proveedor) {

		return consumoDao.save(proveedor);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		consumoDao.deleteById(id);
	}

	@Override
	public List<Consumo> findAllByFiltro(FiltroConsumoDTO filtroConsumoDTO) {
		return consumoDao.findAllByFiltro(filtroConsumoDTO);
	}

}
