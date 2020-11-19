package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.dto.FiltroProgramacionDTO;
import pe.com.avivel.sistemas.siva.models.dto.ProgramacionFilterDTO;
import pe.com.avivel.sistemas.siva.models.dto.ProgramacionQueryDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.ProgramacionVacuna;

import java.util.List;

public interface IProgramacionService {

	 List<ProgramacionVacuna> findAll();
	
	 Page<ProgramacionVacuna> findAll(Pageable pageable);
	
	 ProgramacionVacuna findById(Integer id);
	
	 ProgramacionVacuna save(ProgramacionVacuna programacionVacuna);
	
	 void delete(Integer id);

	 List<ProgramacionQueryDTO> findByEtapaNum(ProgramacionFilterDTO programacionFilterDTO);

	List<ProgramacionQueryDTO> findByFiltro1(FiltroProgramacionDTO filtroProgramacionDTO);
	List<ProgramacionQueryDTO> findByFiltro2(FiltroProgramacionDTO filtroProgramacionDTO);
	List<ProgramacionQueryDTO> findByFiltro3(FiltroProgramacionDTO filtroProgramacionDTO);
	List<ProgramacionQueryDTO> findByFiltro4(FiltroProgramacionDTO filtroProgramacionDTO);
	List<ProgramacionQueryDTO> findByFiltro5(FiltroProgramacionDTO filtroProgramacionDTO);


}
