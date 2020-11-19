package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.InsumoPresentacion;

import java.util.List;

public interface IInsumoPresentacionService {

	 List<InsumoPresentacion> findAll();
	
	 Page<InsumoPresentacion> findAll(Pageable pageable);
	
	 InsumoPresentacion findById(Integer id);
	
	 InsumoPresentacion save(InsumoPresentacion insumoPresentacion);
	
	 void delete(Long id);


}
