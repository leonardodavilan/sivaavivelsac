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
import pe.com.avivel.sistemas.siva.models.dto.FiltroVacunacionDTO;
import pe.com.avivel.sistemas.siva.models.dto.VacunacionDTO;
import pe.com.avivel.sistemas.siva.models.dto.VacunacionQueryDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.*;
import pe.com.avivel.sistemas.siva.models.services.spec.IVacunacionService;
import pe.com.avivel.sistemas.siva.util.ConverterUtil;
import pe.com.avivel.sistemas.siva.util.JasperReportUtil;

import javax.servlet.ServletContext;
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
public class VacunacionRestController {

	private static final Logger logger = LoggerFactory.getLogger(VacunacionRestController.class);
	private final ServletContext servletContext;
	private final DataSource dataSource;
	private final IVacunacionService vacunacionService;


	@Autowired
	public VacunacionRestController(ServletContext servletContext, DataSource dataSource,IVacunacionService vacunacionService) {
		this.servletContext = servletContext;
		this.dataSource = dataSource;
		this.vacunacionService =vacunacionService;
	}

	// private final Logger log = LoggerFactory.getLogger(ClienteRestController.class);

	@GetMapping("/vacunaciones/v1/listar")
	public List<Vacunacion> index() { return vacunacionService.findAll();
	}
	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/vacunaciones/{id}")
	public ResponseEntity<?> show(@PathVariable Integer id) {

		Vacunacion vacunacion = null;
		Map<String, Object> response = new HashMap<>();

		try {
			vacunacion = vacunacionService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(vacunacion == null) {
			response.put("mensaje", "La vacunacion ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Vacunacion>(vacunacion, HttpStatus.OK);
	}

	@GetMapping("/vacunaciones")
	public ResponseEntity<List<VacunacionQueryDTO>> listar(@RequestParam("prdLoteId") Integer prdEtapaId,
										   @RequestParam("fechaDesde") Long fechaDesde,
										   @RequestParam("fechaHasta") Long fechaHasta,
										   @RequestParam("prdEtapa") String prdEtapa) {

		FiltroVacunacionDTO filtroVacunacionDTO = new FiltroVacunacionDTO();
		filtroVacunacionDTO.setFechaDesde(ConverterUtil.toDate(fechaDesde));
		filtroVacunacionDTO.setFechaHasta(ConverterUtil.toDate(fechaHasta));
		filtroVacunacionDTO.setPrdEtapa(prdEtapa);
		filtroVacunacionDTO.setPrdLoteId(prdEtapaId);

		return new ResponseEntity<>(vacunacionService.findAllByFiltro(filtroVacunacionDTO),HttpStatus.OK);
	}


	@PostMapping("/vacunacion/add")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> saveVacunacion(@Valid @RequestBody VacunacionDTO vacunacionDTO, BindingResult result) {

		Vacunacion newVacunacion;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		try{
			newVacunacion =  vacunacionService.save(vacunacionDTO);
		}catch (DataAccessException e){
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El registro de vacunación ha sido creado con éxito!");
		response.put("VacunacionId", newVacunacion);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}


	@Secured("ROLE_ADMIN")
	@PutMapping("/vacunacion/update/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Vacunacion vacunacion, BindingResult result, @PathVariable Integer id) {

		Vacunacion vacunacionActual = vacunacionService.findById(id);

		Vacunacion vacunacionUpdated = null;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (vacunacionActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el registro de vacunación con ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			vacunacionActual.setCodigo(vacunacion.getCodigo());
			vacunacionActual.setFecha(vacunacion.getFecha());
			vacunacionActual.setObservacion(vacunacion.getObservacion());
			vacunacionActual.setProgramacionVacuna(vacunacion.getProgramacionVacuna());
			vacunacionActual.setLote(vacunacion.getLote());
			vacunacionActual.setProveedor(vacunacion.getProveedor());
			vacunacionActual.setMetodoVacuna(vacunacion.getMetodoVacuna());
			vacunacionActual.setEmpleado(vacunacion.getEmpleado());
			vacunacionActual.setLoteSerie(vacunacion.getLoteSerie());
			vacunacionActual.setEstado(vacunacion.getEstado());

			vacunacionUpdated = vacunacionService.saveUpdate(vacunacionActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el registro de vacunación en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El registro de vacunación ha sido actualizado con éxito!");
		response.put("Insumo", vacunacionUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}


	@Secured("ROLE_ADMIN")
	@DeleteMapping("/vacunacion/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {

		Map<String, Object> response = new HashMap<>();

		try {
			Vacunacion vacunacion = vacunacionService.findById(id);
			vacunacionService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el registro de vacunación de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El registro de vacunación eliminado con éxito!");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}


	//REPORTES

	@GetMapping("/report/pdf")
	public String generateReportPdf(@RequestParam("prdLoteId") Integer prdLoteId,
								 @RequestParam("fechaDesde") Long fechaDesde,
								 @RequestParam("fechaHasta") Long fechaHasta,
								 @RequestParam("prdEtapa") String prdEtapa,
								 @RequestParam(value = "tipo", defaultValue = "pdf") String tipo)throws FileNotFoundException, JRException{
		String data = null;
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("fechaDesde", ConverterUtil.toDate(fechaDesde));
		parametros.put("fechaHasta", ConverterUtil.toDate(fechaHasta));
		parametros.put("prdLoteId", prdLoteId);
		parametros.put("prdEtapa", prdEtapa);

		String reporte = null;

		reporte = "rpt_vac_lote.jrxml";


		try (Connection connection = dataSource.getConnection()) {
			return JasperReportUtil.exportReportPdf(reporte,connection,parametros);
		} catch (SQLException | FileNotFoundException | JRException e) {
			logger.error("### error al generar reporte en kardex <- ", e);
		}
		return data;
	}



	@GetMapping("/vacunaciones/reporte/V2")
	public ResponseEntity<byte[]> generateReport(@RequestParam("prdLoteId") Integer prdLoteId,
													  @RequestParam("fechaDesde") Long fechaDesde,
													  @RequestParam("fechaHasta") Long fechaHasta,
													  @RequestParam("prdEtapa") String prdEtapa,
													  @RequestParam(value = "tipo", defaultValue = "pdf") String tipo)throws FileNotFoundException, JRException{
		byte[] bytes = null;
		String data = null;
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("fechaDesde", ConverterUtil.toDate(fechaDesde));
		parametros.put("fechaHasta", ConverterUtil.toDate(fechaHasta));
		parametros.put("prdLoteId", prdLoteId);
		parametros.put("prdEtapa", prdEtapa);

		String reporte = null;

		reporte = "rpt_vac_lote.jrxml";

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



	@GetMapping("/vacunaciones/reporte")
	public ResponseEntity<byte[]> reporte(@RequestParam("prdLoteId") Integer prdLoteId,
										  @RequestParam("fechaDesde") Long fechaDesde,
										  @RequestParam("fechaHasta") Long fechaHasta,
										  @RequestParam("prdEtapa") String prdEtapa,
										  @RequestParam(value = "tipo", defaultValue = "pdf") String tipo) throws FileNotFoundException, JRException {

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("fechaDesde", ConverterUtil.toDate(fechaDesde));
		parametros.put("fechaHasta", ConverterUtil.toDate(fechaHasta));
		parametros.put("prdLoteId", prdLoteId);
		parametros.put("prdEtapa", prdEtapa);

		//String carpeta = servletContext.getRealPath("");
		byte[] bytes = null;
		String reporte = null;

		reporte = "/rpt_vac_lote.jasper";
		String carpeta1 ="C:\\Users\\leonardo.davila\\Downloads\\backend\\siva-apirest\\src\\main\\resources\\reports\\";

		try (Connection connection = dataSource.getConnection()) {
			if (tipo.equalsIgnoreCase("pdf")) {
				bytes = JasperReportUtil.exportReportToPdf(connection, carpeta1 + reporte , parametros);
			} else if (tipo.equalsIgnoreCase("xlsx")) {
				bytes = JasperReportUtil.exportReportToXlsx(connection,  carpeta1 + reporte , parametros);
			}
		} catch (SQLException e) {
			logger.error("### error al generar reporte en kardex <- ", e);
		}

		return new ResponseEntity<>(bytes, HttpStatus.OK);
	}


	@GetMapping("/vacunaciones/reportebygranja")
	public ResponseEntity<byte[]> generateReportByGranja(@RequestParam("prdGranjaId") Integer prdGranjaId,
													  @RequestParam("fechaDesde") Long fechaDesde,
													  @RequestParam("fechaHasta") Long fechaHasta,
													  @RequestParam("prdGranjaNombre") String prdGranjaNombre,
													  @RequestParam(value = "tipo", defaultValue = "pdf") String tipo)throws FileNotFoundException, JRException{
		byte[] bytes = null;
		String data = null;
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("fechaDesde", ConverterUtil.toDate(fechaDesde));
		parametros.put("fechaHasta", ConverterUtil.toDate(fechaHasta));
		parametros.put("prdGranjaId", prdGranjaId);
		parametros.put("prdGranjaNombre", prdGranjaNombre);

		String reporte = null;

		reporte = "rpt_vac_granja.jrxml";

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