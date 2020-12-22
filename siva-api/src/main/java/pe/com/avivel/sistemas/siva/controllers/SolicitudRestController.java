package pe.com.avivel.sistemas.siva.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.com.avivel.sistemas.siva.models.dto.FiltroSolicitudDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Solicitud;
import pe.com.avivel.sistemas.siva.models.services.spec.ISolicitudService;
import pe.com.avivel.sistemas.siva.util.ConverterUtil;

import javax.servlet.ServletContext;
import javax.sql.DataSource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class SolicitudRestController {

	private static final Logger logger = LoggerFactory.getLogger(SolicitudRestController.class);
	private final ServletContext servletContext;
	private final DataSource dataSource;
	private final ISolicitudService solicitudService;


	@Autowired
	public SolicitudRestController(ServletContext servletContext, DataSource dataSource, ISolicitudService solicitudService) {
		this.servletContext = servletContext;
		this.dataSource = dataSource;
		this.solicitudService =solicitudService;
	}

	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/solicitudes/v1/listar")
	public List<Solicitud> index() { return solicitudService.findAll();
	}

	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/solicitudes/maxcodigo")
	public Integer findMaxCodSolicitud() { return solicitudService.findMaxCodSolicitud();
	}

	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/solicitudes/{id}")
	public ResponseEntity<?> show(@PathVariable Integer id) {

		Solicitud solicitud = null;
		Map<String, Object> response = new HashMap<>();

		try {
			solicitud = solicitudService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(solicitud == null) {
			response.put("mensaje", "La solicitud ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Solicitud>(solicitud, HttpStatus.OK);
	}

	@GetMapping("/solicitudes")
	public ResponseEntity<List<Solicitud>> listar(@RequestParam("codsoli") Integer codigoSolicitud,
												  @RequestParam("fechaDesde") Long fechaDesde,
												  @RequestParam("fechaHasta") Long fechaHasta) {

		FiltroSolicitudDTO filtroVacunacionDTO = new FiltroSolicitudDTO();

		filtroVacunacionDTO.setCodigoSolicitud(codigoSolicitud);
		filtroVacunacionDTO.setFechaDesde(ConverterUtil.toDate(fechaDesde));
		filtroVacunacionDTO.setFechaHasta(ConverterUtil.toDate(fechaHasta));

		return new ResponseEntity<>(solicitudService.findAllByFiltro(filtroVacunacionDTO),HttpStatus.OK);
	}

	@GetMapping("/solicitudes/codigo")
	public ResponseEntity<List<Solicitud>> listarByCodigo(@RequestParam("codsoli") Integer codigoSolicitud) {

		return new ResponseEntity<>(solicitudService.findAllByCodigo(codigoSolicitud),HttpStatus.OK);
	}



	@PostMapping("/solicitud/add")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> save(@Valid @RequestBody Solicitud solicitud, BindingResult result) {

		Solicitud newSolicitud;

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

			newSolicitud =  solicitudService.save(solicitud);
		}catch (DataAccessException e){
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La solicitud ha sido creado con éxito!");
		response.put("solicitud", newSolicitud);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}


	@Secured("ROLE_ADMIN")
	@PutMapping("/solicitud/update/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Solicitud solicitud, BindingResult result, @PathVariable Integer id) {

		Solicitud solicitudActual = solicitudService.findById(id);

		Solicitud solicitudUpdate = null;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (solicitudActual == null) {
			response.put("mensaje", "Error: no se pudo editar, la solicitud con ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			solicitudActual.setCodigo(solicitud.getCodigo());
			solicitudActual.setFecha(solicitud.getFecha());
			solicitudActual.setCcosto(solicitud.getCcosto());
			solicitudActual.setEstadoSolicitud(solicitud.getEstadoSolicitud());
			solicitudActual.setEmpleado(solicitud.getEmpleado());
			solicitudActual.setItems(solicitud.getItems());

			solicitudUpdate = solicitudService.save(solicitudActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el registro de vacunación en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La solicitud ha sido actualizado con éxito!");
		response.put("Solicitud", solicitudUpdate);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}


	@Secured("ROLE_ADMIN")
	@DeleteMapping("/solicitud/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {

		Map<String, Object> response = new HashMap<>();

		try {
			Solicitud solicitud = solicitudService.findById(id);
			solicitudService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar la solicitud de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La solicitud se ha eliminado con éxito!");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}


	//REPORTES

}