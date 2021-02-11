package pe.com.avivel.sistemas.siva.models.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.IStockInsumoDao;
import pe.com.avivel.sistemas.siva.models.dto.MovimientoQueryDTO;
import pe.com.avivel.sistemas.siva.models.dto.StockInsumoDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.StockInsumo;
import pe.com.avivel.sistemas.siva.models.services.spec.IStockInsumoService;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockInsumoImpl implements IStockInsumoService {

	@Autowired
	private IStockInsumoDao stockInsumoDao;

	@Override
	@Transactional(readOnly = true)
	public List<StockInsumo> findAll() {
		return (List<StockInsumo>) stockInsumoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<StockInsumo> findAll(Pageable pageable) {
		return stockInsumoDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public StockInsumo findById(Integer id) {
		return stockInsumoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public StockInsumo save(StockInsumo stockInsumo) {
		return stockInsumoDao.save(stockInsumo);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		stockInsumoDao.deleteById(id);
	}

	@Override
	public List<StockInsumoDTO> findAllByGranjaId(Integer granjaId) {
		List<StockInsumoDTO> stockInsumoDTOS = new ArrayList<>();
		stockInsumoDao.findAllByGranjaId(granjaId).forEach(stockInsumo -> stockInsumoDTOS.add(StockInsumoDTO.getInstance(stockInsumo)));
		return stockInsumoDTOS;
	}

}
