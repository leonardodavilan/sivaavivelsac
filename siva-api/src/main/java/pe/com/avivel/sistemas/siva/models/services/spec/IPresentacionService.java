package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Presentacion;

import java.util.List;

public interface IPresentacionService {

	public List<Presentacion> findAll();
	
	public Page<Presentacion> findAll(Pageable pageable);
	
	public Presentacion findById(Integer id);
	
	public Presentacion save(Presentacion presentacion);
	
	public void delete(Integer id);


}
