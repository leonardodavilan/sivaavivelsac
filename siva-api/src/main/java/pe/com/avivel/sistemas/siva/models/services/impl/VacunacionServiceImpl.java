package pe.com.avivel.sistemas.siva.models.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.IMetodoVacunaDao;
import pe.com.avivel.sistemas.siva.models.dao.IVacunacionDao;
import pe.com.avivel.sistemas.siva.models.dto.FiltroVacunacionDTO;
import pe.com.avivel.sistemas.siva.models.dto.VacunacionDTO;
import pe.com.avivel.sistemas.siva.models.dto.VacunacionQueryDTO;
import pe.com.avivel.sistemas.siva.models.entity.produccion.PrdLote;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.LoteSerie;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.*;
import pe.com.avivel.sistemas.siva.models.services.spec.IVacunacionService;

import java.util.ArrayList;
import java.util.List;

@Service
public class VacunacionServiceImpl implements IVacunacionService {

	@Autowired
	private IVacunacionDao vacunacionDao;

	@Autowired
	private IMetodoVacunaDao metodoVacunaDao;

	@Override
	@Transactional(readOnly = true)
	public List<Vacunacion> findAll() { return (List<Vacunacion>) vacunacionDao.findAll();
	}

	@Override
	public Page<Vacunacion> findAll(Pageable pageable) {
		return null;
	}


	@Override
	@Transactional(readOnly = true)
	public Vacunacion findById(Integer id) {
		return vacunacionDao.findById(id).orElse(null);
	}


	@Override
	@Transactional
	public Vacunacion saveUpdate(Vacunacion vacunacion) {
		return vacunacionDao.save(vacunacion);
	}


	@Override
	@Transactional
	public Vacunacion save(VacunacionDTO vacunacionDTO) {
		Vacunacion newVacunacion = new Vacunacion();

		newVacunacion.setCodigo(vacunacionDTO.getCodigo());
		newVacunacion.setFecha(vacunacionDTO.getFecha());
		newVacunacion.setObservacion(vacunacionDTO.getObservacion());

		if(vacunacionDTO.getMetodoVacunaId() == null){
			newVacunacion.setMetodoVacuna(null);
		}else{
			newVacunacion.setMetodoVacuna(new MetodoVacuna().setId(vacunacionDTO.getMetodoVacunaId()));
		}
		if(vacunacionDTO.getEmpleadoId() == null){
			newVacunacion.setEmpleado(null);
		}else{
			newVacunacion.setEmpleado(new Empleado().setId(vacunacionDTO.getEmpleadoId()));
		}
		newVacunacion.setLote(new PrdLote().setId(vacunacionDTO.getLoteId()));

		if (vacunacionDTO.getLoteSerieId()==null){
			newVacunacion.setLoteSerie(null);
		}else{
			newVacunacion.setLoteSerie(new LoteSerie().setId(vacunacionDTO.getLoteSerieId()));
		}

		newVacunacion.setProgramacionVacuna(new ProgramacionVacuna().setId(vacunacionDTO.getProgramacionVacunaId()));
		if(vacunacionDTO.getProveedorId()==null){
			newVacunacion.setProveedor(null);
		}else{
			newVacunacion.setProveedor(new Proveedor().setId(vacunacionDTO.getProveedorId()));
		}

		return vacunacionDao.save(newVacunacion);
	}

	@Override
	public void delete(Integer id) {
		vacunacionDao.deleteById(id);
	}

	@Override
	public List<VacunacionQueryDTO> findAllByFiltro(FiltroVacunacionDTO filtroVacunacionDTO) {
		List<VacunacionQueryDTO> vacunacionQueryDTO = new ArrayList<>();
		vacunacionDao.findAllByFiltro(filtroVacunacionDTO).forEach(vacunacion -> vacunacionQueryDTO.add(VacunacionQueryDTO.getInstance(vacunacion)));
		return vacunacionQueryDTO;
	}
}
