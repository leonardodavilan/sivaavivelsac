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
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.TiempoProgramacion;
import pe.com.avivel.sistemas.siva.models.services.spec.ITiempoProgramacionService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class TiempoProgramacionController {

	@Autowired
	private ITiempoProgramacionService tiempoProgramacionService;

	@GetMapping("/tiempos-programaciones")
	public List<TiempoProgramacion> index() {
		return tiempoProgramacionService.findAll();
	}
	
	@GetMapping("/tiempos-programaciones/page/{page}")
	public Page<TiempoProgramacion> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return tiempoProgramacionService.findAll(pageable);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/tiempos-programaciones/{id}")
	public ResponseEntity<?> show(@PathVariable Integer id) {

		TiempoProgramacion  tiempoProgramacion = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			tiempoProgramacion = tiempoProgramacionService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(tiempoProgramacion == null) {
			response.put("mensaje", "El tiempo ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<TiempoProgramacion>(tiempoProgramacion, HttpStatus.OK);
	}
	
	//@Secured("ROLE_ADMIN")
	@PostMapping("/tiempos-programaciones")
	public ResponseEntity<?> create(@Valid @RequestBody TiempoProgramacion tiempoProgramacion, BindingResult result) {
		
		TiempoProgramacion tiempoProgramacionNew = null;
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
			tiempoProgramacionNew = tiempoProgramacionService.save(tiempoProgramacion);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El tiempo ha sido creado con éxito!");
		response.put("tiempo", tiempoProgramacionNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/tiempos-programaciones/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody TiempoProgramacion tiempoProgramacion, BindingResult result, @PathVariable Integer id) {

		TiempoProgramacion tiempoProgramacionAcutal = tiempoProgramacionService.findById(id);

		TiempoProgramacion tiempoProgramacionUpdated = null;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (tiempoProgramacionAcutal == null) {
			response.put("mensaje", "Error: no se pudo editar, el cliente ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			tiempoProgramacionAcutal.setNombre(tiempoProgramacion.getNombre());
			tiempoProgramacionAcutal.setEstado(tiempoProgramacion.getEstado());

			tiempoProgramacionUpdated = tiempoProgramacionService.save(tiempoProgramacion);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El tiempo ha sido actualizado con éxito!");
		response.put("tiempo", tiempoProgramacionUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/tiempos-programaciones/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			TiempoProgramacion tiempoProgramacion = tiempoProgramacionService.findById(id);
			tiempoProgramacionService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el tiempo de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El tiempo eliminado con éxito!");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}


}
