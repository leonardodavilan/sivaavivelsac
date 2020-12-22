package pe.com.avivel.sistemas.siva.models.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.IInsumoProveedorDao;
import pe.com.avivel.sistemas.siva.models.dto.InsumoProveedorQueryDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.InsumoProveedor;
import pe.com.avivel.sistemas.siva.models.services.spec.IInsumoProveedorService;

import java.util.ArrayList;
import java.util.List;

@Service
public class InsumoProveedorServiceImpl implements IInsumoProveedorService {

	@Autowired
	private IInsumoProveedorDao insumoProveedorDao;

	@Override
	@Transactional(readOnly = true)
	public List<InsumoProveedor> findAll() {
		return (List<InsumoProveedor>) insumoProveedorDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<InsumoProveedor> findAll(Pageable pageable) {
		return insumoProveedorDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public InsumoProveedor findById(Long id) { return insumoProveedorDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public InsumoProveedor save(InsumoProveedor insumoProveedor) {
		return insumoProveedorDao.save(insumoProveedor);
	}

	@Override
	public void delete(Long id) {

	}

	@Override
	public List<InsumoProveedor> findAllByInsumoPresentacion(Integer insumoPresentacionId) {
		return insumoProveedorDao.findAllByInsumoPresentacion(insumoPresentacionId);
	}

	@Override
	public List<InsumoProveedor> findAllByInsumoPresentacionByMoneda(Integer monedaId) {
		return insumoProveedorDao.findAllByInsumoPresentacionByMoneda(monedaId);
	}

	@Override
	public List<InsumoProveedorQueryDTO> findAllBySubFamilia(Integer subFamiliaId) {
		List<InsumoProveedorQueryDTO> insumoProveedorQueryDTOS = new ArrayList<>();
		insumoProveedorDao.findAllBySubFamilia(subFamiliaId).forEach(insumoProveedor -> insumoProveedorQueryDTOS.add(InsumoProveedorQueryDTO.getInstance(insumoProveedor)));
		return insumoProveedorQueryDTOS;
	}
}
