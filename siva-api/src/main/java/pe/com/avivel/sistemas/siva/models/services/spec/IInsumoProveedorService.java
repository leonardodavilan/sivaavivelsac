package pe.com.avivel.sistemas.siva.models.services.spec;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.InsumoProveedor;

import java.util.List;

public interface IInsumoProveedorService {

	List<InsumoProveedor> findAll();
	
	Page<InsumoProveedor> findAll(Pageable pageable);
	
	InsumoProveedor findById(Long id);
	
	InsumoProveedor save(InsumoProveedor insumoProveedor);
	
	void delete(Long id);

	List<InsumoProveedor> findAllByInsumoPresentacion(Integer insumoPresentacionId);

}
