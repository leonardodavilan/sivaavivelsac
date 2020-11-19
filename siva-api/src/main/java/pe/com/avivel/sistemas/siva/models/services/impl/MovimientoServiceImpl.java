package pe.com.avivel.sistemas.siva.models.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.IMovimientoDao;
import pe.com.avivel.sistemas.siva.models.dto.FiltroMovimientoDTO;
import pe.com.avivel.sistemas.siva.models.dto.MovimientoQueryDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.*;
import pe.com.avivel.sistemas.siva.models.services.spec.IMovimientoService;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovimientoServiceImpl implements IMovimientoService {

	@Autowired
	private IMovimientoDao movimientoDao;

	@Override
	@Transactional(readOnly = true)
	public List<Movimiento> findAll() { return (List<Movimiento>) movimientoDao.findAll();
	}

	@Override
	public Page<Movimiento> findAll(Pageable pageable) {
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Movimiento findById(Integer id) {
		return movimientoDao.findById(id).orElse(null);
	}

	@Override
	public Movimiento save(Movimiento movimiento) {
		return movimientoDao.save(movimiento);
	}

	@Override
	public void delete(Integer id) {
		movimientoDao.deleteById(id);
	}

	@Override
	public List<MovimientoQueryDTO> findAllByFiltro(FiltroMovimientoDTO filtroVacunacionDTO) {
		List<MovimientoQueryDTO> movimientoQueryDTOS = new ArrayList<>();
		movimientoDao.findAllByFiltro(filtroVacunacionDTO).forEach(movimiento -> movimientoQueryDTOS.add(MovimientoQueryDTO.getInstance(movimiento)));
		return movimientoQueryDTOS;
	}

}
