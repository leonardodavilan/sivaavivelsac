package pe.com.avivel.sistemas.siva.controllers;

import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.com.avivel.sistemas.siva.models.dto.FiltroConsumoDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Captura;
import pe.com.avivel.sistemas.siva.models.services.spec.ICapturaService;
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

@CrossOrigin(origins = { "http://localhost:4200","*" })
@RestController
@RequestMapping("/api")
public class CapturaRestController {

	private ICapturaService capturaService;
	private final DataSource dataSource;
	private static final Logger logger = LoggerFactory.getLogger(VacunacionRestController.class);

	@Autowired
	public CapturaRestController (ICapturaService capturaService, DataSource dataSource){
		this.capturaService = capturaService;
		this.dataSource = dataSource;
	}
	

	@GetMapping("/capturas")
	public List<Captura> index() {
		return capturaService.findAll();
	}

	@GetMapping("/capturas-by-filtro")
	public ResponseEntity<List<Captura>> listar(@RequestParam("zonasubzonacontrolId") Integer zonasubzonacontrolId,
												@RequestParam("fechaDesde") Long fechaDesde,
												@RequestParam("fechaHasta") Long fechaHasta,
												@RequestParam("prdGranjaId") Integer prdGranjaId) {

		FiltroConsumoDTO filtrocontrolroedDTO = new FiltroConsumoDTO();

		filtrocontrolroedDTO.setFechaDesde(ConverterUtil.toDate(fechaDesde));
		filtrocontrolroedDTO.setFechaHasta(ConverterUtil.toDate(fechaHasta));
		filtrocontrolroedDTO.setPrdGranjaId(prdGranjaId);
		filtrocontrolroedDTO.setZonasubzonacontrolId(zonasubzonacontrolId);

		return new ResponseEntity<>(capturaService.findAllByFiltro(filtrocontrolroedDTO),HttpStatus.OK);
	}
	
	@GetMapping("/capturas/page/{page}")
	public Page<Captura> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return capturaService.findAll(pageable);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/captura/{id}")
	public ResponseEntity<?> show(@PathVariable Integer id) {
		
		Captura captura = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			captura = capturaService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(captura == null) {
			response.put("mensaje", "La captura ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Captura>(captura, HttpStatus.OK);
	}
	
	//@Secured("ROLE_ADMIN")
	@PostMapping("/captura/add")
	public ResponseEntity<?> create(@Valid @RequestBody Captura captura, BindingResult result) {
		
		Captura capturaNew = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			capturaNew = capturaService.save(captura);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La captura ha sido creada con éxito!");
		response.put("captura", capturaNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/captura/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Captura captura, BindingResult result, @PathVariable Integer id) {

		Captura capturaActual = capturaService.findById(id);

		Captura capturaUpdated = null;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (capturaActual == null) {
			response.put("mensaje", "Error: no se pudo editar, la captura ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			capturaActual.setEstado(captura.getEstado());
			capturaActual.setAreaCaptura(captura.getAreaCaptura());
			capturaActual.setEdad(captura.getEdad());
			capturaActual.setNumCapturas(captura.getNumCapturas());
			capturaActual.setSexo(captura.getSexo());
			capturaActual.setConsumo(captura.getConsumo());

			capturaUpdated = capturaService.save(capturaActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar la captura en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La captura ha sido actualizada con éxito!");
		response.put("captura", capturaActual);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/captura/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			Captura captura = capturaService.findById(id);
		    capturaService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar la captura de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Captura eliminada con éxito!");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	//Reporte

	@GetMapping("/captura/reporte")
	public ResponseEntity<byte[]> generateReport(@RequestParam("granjaId") Integer granjaId,
												 @RequestParam("fechaDesde") Long fechaDesde,
												 @RequestParam("fechaHasta") Long fechaHasta,
												 @RequestParam(value = "tipo", defaultValue = "pdf") String tipo) throws FileNotFoundException, JRException {
		byte[] bytes = null;
		String data = null;
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("fechaDesde", ConverterUtil.toDate(fechaDesde));
		parametros.put("fechaHasta", ConverterUtil.toDate(fechaHasta));
		parametros.put("granjaId", granjaId);

		String reporte = null;

		reporte = "rpt_captura.jrxml";

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
