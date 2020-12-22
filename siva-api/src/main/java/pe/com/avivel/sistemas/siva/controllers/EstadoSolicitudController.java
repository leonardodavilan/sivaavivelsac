package pe.com.avivel.sistemas.siva.controllers;


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
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.EstadoSolicitud;
import pe.com.avivel.sistemas.siva.models.services.spec.IEstadoSolicitudService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class EstadoSolicitudController {


	@Autowired
	private IEstadoSolicitudService estadoSolicitudService;

	@GetMapping("/estado-solicitud")
	public List<EstadoSolicitud> index() {
		return estadoSolicitudService.findAll();
	}

	@GetMapping("/estado-solicitud/page/{page}")
	public Page<EstadoSolicitud> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return estadoSolicitudService.findAll(pageable);
	}

	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/estado-solicitud/{id}")
	public ResponseEntity<?> show(@PathVariable Integer id) {

		EstadoSolicitud estadoSolicitud = null;
		Map<String, Object> response = new HashMap<>();

		try {
			estadoSolicitud = estadoSolicitudService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(estadoSolicitud == null) {
			response.put("mensaje", "El tipo de movimiento ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<EstadoSolicitud>(estadoSolicitud, HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/estado-solicitud")
	public ResponseEntity<?> create(@Valid @RequestBody EstadoSolicitud estadoSolicitud, BindingResult result) {

		EstadoSolicitud estadoSolicitudNew = null;
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
			estadoSolicitudNew = estadoSolicitudService.save(estadoSolicitud);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El estado de solicitud ha sido creado con éxito!");
		response.put("estadoSolicitud", estadoSolicitudNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured("ROLE_ADMIN")
	@PutMapping("/estado-solicitud/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody EstadoSolicitud estadoSolicitud, BindingResult result, @PathVariable Integer id) {

		EstadoSolicitud estadoSolicitudActual = estadoSolicitudService.findById(id);

		EstadoSolicitud estadoSolicitudUpdate = null;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (estadoSolicitudActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el estado de solicitud ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			estadoSolicitudActual.setNombre(estadoSolicitud.getNombre());
			estadoSolicitudActual.setEstado(estadoSolicitud.getEstado());


			estadoSolicitudUpdate = estadoSolicitudService.save(estadoSolicitudActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el estado de solicitud en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El estado solicitud ha sido actualizado con éxito!");
		response.put("estadoSolicitud", estadoSolicitudUpdate);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured("ROLE_ADMIN")
	@DeleteMapping("/estado-solicitud/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {

		Map<String, Object> response = new HashMap<>();

		try {
			EstadoSolicitud estadoSolicitud = estadoSolicitudService.findById(id);
			estadoSolicitudService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el estado de solicitud de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El estado de solicitud eliminado con éxito!");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}


}
