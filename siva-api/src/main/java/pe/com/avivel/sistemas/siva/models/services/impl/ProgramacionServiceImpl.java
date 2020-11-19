package pe.com.avivel.sistemas.siva.models.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.IProgramacionDao;
import pe.com.avivel.sistemas.siva.models.dto.FiltroProgramacionDTO;
import pe.com.avivel.sistemas.siva.models.dto.ProgramacionFilterDTO;
import pe.com.avivel.sistemas.siva.models.dto.ProgramacionQueryDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.ProgramacionVacuna;
import pe.com.avivel.sistemas.siva.models.services.spec.IProgramacionService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProgramacionServiceImpl implements IProgramacionService {

	@Autowired
	private IProgramacionDao programacionDao;

	@Override
	@Transactional(readOnly = true)
	public List<ProgramacionVacuna> findAll() { return (List<ProgramacionVacuna>) programacionDao.findAll();
	}

	@Override
	public Page<ProgramacionVacuna> findAll(Pageable pageable) {
		return null;
	}

	@Override
	public ProgramacionVacuna findById(Integer id) {

		return programacionDao.findById(id).orElse(null);
	}

	@Override
	public ProgramacionVacuna save(ProgramacionVacuna programacionVacuna) {
		return programacionDao.save(programacionVacuna);
	}

	@Override
	public void delete(Integer id) {
			programacionDao.deleteById(id);
	}

	@Override
	public List<ProgramacionQueryDTO> findByEtapaNum(ProgramacionFilterDTO programacionFilterDTO) {
		List<ProgramacionQueryDTO> programacionQueryDTO = new ArrayList<>();
		programacionDao.findByEtapaNum(programacionFilterDTO.getPrdEtapaId(),programacionFilterDTO.getVacNumProg()).forEach(programacionVacuna -> programacionQueryDTO.add(ProgramacionQueryDTO.getInstance(programacionVacuna)));
		return programacionQueryDTO;
	}

	@Override
	public List<ProgramacionQueryDTO> findByFiltro1(FiltroProgramacionDTO filtroProgramacionDTO) {
		List<ProgramacionQueryDTO> programacionQueryDTO = new ArrayList<>();
		programacionDao.findByFiltro1(filtroProgramacionDTO).forEach(programacionVacuna -> programacionQueryDTO.add(ProgramacionQueryDTO.getInstance(programacionVacuna)));
		return programacionQueryDTO;
	}
	@Override
	public List<ProgramacionQueryDTO> findByFiltro2(FiltroProgramacionDTO filtroProgramacionDTO) {
		List<ProgramacionQueryDTO> programacionQueryDTO = new ArrayList<>();
		programacionDao.findByFiltro2(filtroProgramacionDTO).forEach(programacionVacuna -> programacionQueryDTO.add(ProgramacionQueryDTO.getInstance(programacionVacuna)));
		return programacionQueryDTO;
	}
	@Override
	public List<ProgramacionQueryDTO> findByFiltro3(FiltroProgramacionDTO filtroProgramacionDTO) {
		List<ProgramacionQueryDTO> programacionQueryDTO = new ArrayList<>();
		programacionDao.findByFiltro3(filtroProgramacionDTO).forEach(programacionVacuna -> programacionQueryDTO.add(ProgramacionQueryDTO.getInstance(programacionVacuna)));
		return programacionQueryDTO;
	}
	@Override
	public List<ProgramacionQueryDTO> findByFiltro4(FiltroProgramacionDTO filtroProgramacionDTO) {
		List<ProgramacionQueryDTO> programacionQueryDTO = new ArrayList<>();
		programacionDao.findByFiltro4(filtroProgramacionDTO).forEach(programacionVacuna -> programacionQueryDTO.add(ProgramacionQueryDTO.getInstance(programacionVacuna)));
		return programacionQueryDTO;
	}
	@Override
	public List<ProgramacionQueryDTO> findByFiltro5(FiltroProgramacionDTO filtroProgramacionDTO) {
		List<ProgramacionQueryDTO> programacionQueryDTO = new ArrayList<>();
		programacionDao.findByFiltro5(filtroProgramacionDTO).forEach(programacionVacuna -> programacionQueryDTO.add(ProgramacionQueryDTO.getInstance(programacionVacuna)));
		return programacionQueryDTO;
	}


}
