package pe.com.avivel.sistemas.siva.controllers;

import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.com.avivel.sistemas.siva.models.dto.FiltroProgramacionDTO;
import pe.com.avivel.sistemas.siva.models.dto.ProgramacionFilterDTO;
import pe.com.avivel.sistemas.siva.models.dto.ProgramacionQueryDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.ProgramacionVacuna;
import pe.com.avivel.sistemas.siva.models.services.spec.IProgramacionService;
import pe.com.avivel.sistemas.siva.util.ConverterUtil;
import pe.com.avivel.sistemas.siva.util.JasperReportUtil;

import javax.sql.DataSource;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class ProgramacionRestController {
	@Autowired
	private IProgramacionService programacionService;

	private final DataSource dataSource;
	private static final Logger logger = LoggerFactory.getLogger(VacunacionRestController.class);

	@Autowired
	public ProgramacionRestController(IProgramacionService programacionService,DataSource dataSource){
		this.programacionService = programacionService;
		this.dataSource = dataSource;
	}


	@GetMapping("/v1/programaciones")
	public List<ProgramacionVacuna> index() { return programacionService.findAll();
	}

	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/programaciones/{id}")
	public ResponseEntity<?> show(@PathVariable Integer id) {

		ProgramacionVacuna programacionVacuna = null;
		Map<String, Object> response = new HashMap<>();

		try {
			programacionVacuna = programacionService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(programacionVacuna == null) {
			response.put("mensaje", "La programación ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<ProgramacionVacuna>(programacionVacuna, HttpStatus.OK);
	}


	@Secured("ROLE_ADMIN")
	@PostMapping("/programaciones/add")
	public ResponseEntity<?> create(@Valid @RequestBody ProgramacionVacuna programacionVacuna, BindingResult result){
		ProgramacionVacuna programacionVacunaNew = null;
		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()){
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El camp '" + err.getField() + "' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
		}

		try{
			programacionVacunaNew = programacionService.save(programacionVacuna);
		}catch (DataAccessException e){
			response.put("mensaje","Error al realizar el insert en la base de datos");
			response.put("proveedor", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La programación creada con éxito");
		response.put("programacion", programacionVacunaNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured("ROLE_ADMIN")
	@PutMapping("/programaciones/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody ProgramacionVacuna programacionVacuna, BindingResult result,
									@PathVariable Integer id){
		ProgramacionVacuna programacionVacunaActual = programacionService.findById(id);
		ProgramacionVacuna programacionVacunaUpdated = null;

		Map<String, Object> response = new HashMap<>();
		if(result.hasErrors()){
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
		}
		if(programacionVacunaActual == null){
			response.put("mensaje", "Error: no se pudo editar, la programacion ID: "
					.concat(id.toString().concat("no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try{
			programacionVacunaActual.setDescripcionVacunacion(programacionVacuna.getDescripcionVacunacion());
			programacionVacunaActual.setEdadVacunacion(programacionVacuna.getEdadVacunacion());
			programacionVacunaActual.setInsumo(programacionVacuna.getInsumo());
			programacionVacunaActual.setNumeroProgramacion(programacionVacuna.getNumeroProgramacion());
			programacionVacunaActual.setTiempoProgramacion(programacionVacuna.getTiempoProgramacion());
			programacionVacunaActual.setPrdEtapa(programacionVacuna.getPrdEtapa());
			programacionVacunaActual.setEstado(programacionVacuna.getEstado());

			programacionVacunaUpdated = programacionService.save(programacionVacunaActual);

		}catch (DataAccessException e){
			response.put("mensaje", "Error al actualizar la programación en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La programación ha sido actualizada con éxito!");
		response.put("programación", programacionVacunaUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured("ROLE_ADMIN")
	@PutMapping("/update_estado_programacion/{id}")
	public ResponseEntity<?> updateEstado(@Valid @RequestBody ProgramacionVacuna programacionVacuna, BindingResult result,
									@PathVariable Integer id){
		ProgramacionVacuna programacionVacunaActual = programacionService.findById(id);
		ProgramacionVacuna programacionVacunaUpdated = null;

		Map<String, Object> response = new HashMap<>();
		if(result.hasErrors()){
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
		}
		if(programacionVacunaActual == null){
			response.put("mensaje", "Error: no se pudo editar, la programacion ID: "
					.concat(id.toString().concat("no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try{

			programacionVacunaActual.setEstado(programacionVacuna.getEstado());

			programacionVacunaUpdated = programacionService.save(programacionVacunaActual);

		}catch (DataAccessException e){
			response.put("mensaje", "Error al actualizar la programación en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La programación ha sido actualizada con éxito!");
		response.put("programación", programacionVacunaUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured("ROLE_ADMIN")
	@DeleteMapping("/programaciones/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id){
		Map<String, Object> response = new HashMap<>();
		try{
			ProgramacionVacuna programacionVacuna = programacionService.findById(id);
			programacionService.delete(id);
		}catch (DataAccessException e){
			response.put("mensaje", "Error al eliminar la programación de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La programación eliminada con éxito!");

		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}

	@GetMapping("/programaciones")
	public List<ProgramacionQueryDTO> listar(@RequestParam("prdEtapaId") Integer prdEtapaId,
											 @RequestParam("vacNumProg") Integer vacNumProg) {

		ProgramacionFilterDTO programacionFilterDTO = new ProgramacionFilterDTO();
		programacionFilterDTO.setVacNumProg(vacNumProg);
		programacionFilterDTO.setPrdEtapaId(prdEtapaId);
		return programacionService.findByEtapaNum(programacionFilterDTO);
	}

	@GetMapping("/filtro-1/programaciones")
	public List<ProgramacionQueryDTO> findByFiltro1(@RequestParam(value="prdEtapa" , defaultValue = "0") String prdEtapa) {
		FiltroProgramacionDTO filtroProgramacionDTO = new FiltroProgramacionDTO();
		filtroProgramacionDTO.setPrdEtapa(prdEtapa);
		return programacionService.findByFiltro1(filtroProgramacionDTO);
	}

	@GetMapping("/programacion-report")
	public ResponseEntity<byte[]> generateReportByGranja(@RequestParam("prdEtapaId") Integer prdEtapaId,
														 @RequestParam("vacNumProgId") Integer vacNumProgId,
														 @RequestParam(value = "tipo", defaultValue = "pdf") String tipo)throws FileNotFoundException, JRException {
		byte[] bytes = null;
		String data = null;
		Map<String, Object> parametros = new HashMap<>();

		parametros.put("prdEtapaId", prdEtapaId);
		parametros.put("vacNumProgId", vacNumProgId);

		String reporte = null;

		reporte = "rpt_programacion.jrxml";

		try (Connection connection = dataSource.getConnection()) {
			if (tipo.equalsIgnoreCase("pdf")) {
				bytes = JasperReportUtil.exportReportToPdfV2(reporte,connection,parametros);
			} else if (tipo.equalsIgnoreCase("xlsx")) {
				bytes = JasperReportUtil.exportReportToXlsxV2(reporte,connection,parametros);
			}
		} catch (SQLException e) {
			logger.error("### error al generar reporte en kardex <- ", e);
		}

		return new ResponseEntity<>(bytes, HttpStatus.OK);
	}



}
