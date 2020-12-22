package pe.com.avivel.sistemas.siva.models.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.IInsumoDao;
import pe.com.avivel.sistemas.siva.models.dto.InsumoDTO;
import pe.com.avivel.sistemas.siva.models.dto.InsumoDetalladoDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Insumo;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.SubFamilia;
import pe.com.avivel.sistemas.siva.models.services.spec.IInsumoService;

import java.util.List;

@Service
public class InsumoServiceImpl implements IInsumoService {

	@Autowired
	private IInsumoDao insumoDao;

	@Override
	@Transactional(readOnly = true)
	public List<Insumo> findAll() { return (List<Insumo>) insumoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Insumo> findAll(Pageable pageable) {
		return insumoDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Insumo findById(Integer id) {
		return insumoDao.findById(id).orElse(null);
	}

	@Override
	public List<Insumo> findAllByTipo(Integer tipoInsumoId) {
		return insumoDao.findAllByTipo(tipoInsumoId);
	}

	@Override
	public List<Insumo> findAllBySubFamilia(Integer subFamiliaId) {
		return insumoDao.findAllBySubFamilia(subFamiliaId);
	}

	@Override
	@Transactional
	public Insumo save(InsumoDTO insumoDTO) {
		Insumo newInsumo = new Insumo();
		newInsumo.setDescripcion(insumoDTO.getInsumo_descripcion());
		newInsumo.setSubFamilia(new SubFamilia().setSubFamiliId(insumoDTO.getSubFamilia_id()));
		return insumoDao.save(newInsumo);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		insumoDao.deleteById(id);
	}

	@Override
	@Transactional
	public Integer saveInsumoMultiple(InsumoDTO insumoDTO) {
		return insumoDao.saveInsumoMultiple(insumoDTO.getInsumo_descripcion(),insumoDTO.getSubFamilia_id());
	}

	@Override
	@Transactional
	public Integer saveInsumoDetallado(InsumoDetalladoDTO insumoDetalladoDTO) {
		return insumoDao.saveInsumoDetallado(insumoDetalladoDTO.getInsumo_descripcion(),insumoDetalladoDTO.getSubFamilia_id(),
				insumoDetalladoDTO.getCodigoRef(),insumoDetalladoDTO.getCodigoSap(),insumoDetalladoDTO.getPresentacionId(),insumoDetalladoDTO.getUnidadMedidaId(),
				insumoDetalladoDTO.getProveedorId(),insumoDetalladoDTO.getPrecio(),insumoDetalladoDTO.getMonedaId());
	}



}
