package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.dto.FiltroVacunacionDTO;
import pe.com.avivel.sistemas.siva.models.dto.VacunacionDTO;
import pe.com.avivel.sistemas.siva.models.dto.VacunacionQueryDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Vacunacion;

import java.util.List;

public interface IVacunacionService {

	 List<Vacunacion> findAll();
	
	 Page<Vacunacion> findAll(Pageable pageable);

	 Vacunacion findById(Integer id);

	 Vacunacion save(VacunacionDTO vacunacionDTO);

	 Vacunacion saveUpdate(Vacunacion vacunacion);
	
	 void delete(Integer id);

	 List<VacunacionQueryDTO> findAllByFiltro(FiltroVacunacionDTO filtroVacunacionDTO);


}
