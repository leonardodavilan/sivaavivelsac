package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Proveedor;

import java.util.List;

public interface IProveedorService {

	List<Proveedor> findAll();
	
	Page<Proveedor> findAll(Pageable pageable);
	
	Proveedor findById(Integer id);
	
	Proveedor save(Proveedor proveedor);
	
	void delete(Integer id);


}
