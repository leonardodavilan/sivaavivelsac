package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Empleado;

import java.util.List;

public interface IEmpleadoService {

	List<Empleado> findAll();
	
	Page<Empleado> findAll(Pageable pageable);

	Empleado findById(Integer id);

	Empleado save(Empleado empleado);
	
	void delete(Integer id);


}
