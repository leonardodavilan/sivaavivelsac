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
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.NumeroProgramacion;
import pe.com.avivel.sistemas.siva.models.services.spec.INumeroProgramacionService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class NumeroProgramacionController {

	@Autowired
	private INumeroProgramacionService numeroProgramacionService;

	@GetMapping("/numeros-programaciones")
	public List<NumeroProgramacion> index() {
		return numeroProgramacionService.findAll();
	}

	@GetMapping("/numeros-programaciones/page/{page}")
	public Page<NumeroProgramacion> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return numeroProgramacionService.findAll(pageable);
	}

	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/numeros-programaciones/{id}")
	public ResponseEntity<?> show(@PathVariable Integer id) {

		NumeroProgramacion  numeroProgramacion = null;
		Map<String, Object> response = new HashMap<>();

		try {
			numeroProgramacion = numeroProgramacionService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(numeroProgramacion == null) {
			response.put("mensaje", "El numero de programación ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<NumeroProgramacion>(numeroProgramacion, HttpStatus.OK);
	}

	//@Secured("ROLE_ADMIN")
	@PostMapping("/numeros-programaciones")
	public ResponseEntity<?> create(@Valid @RequestBody NumeroProgramacion numeroProgramacion, BindingResult result) {

		NumeroProgramacion numeroProgramacionNew = null;
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
			numeroProgramacionNew = numeroProgramacionService.save(numeroProgramacion);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El número de programación ha sido creado con éxito!");
		response.put("tiempo", numeroProgramacionNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured("ROLE_ADMIN")
	@PutMapping("/numeros-programaciones/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody NumeroProgramacion numeroProgramacion, BindingResult result, @PathVariable Integer id) {

		NumeroProgramacion numeroProgramacionActual = numeroProgramacionService.findById(id);

		NumeroProgramacion numeroProgramacionUpdate = null;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (numeroProgramacionActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el número de programación ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			numeroProgramacionActual.setNombre(numeroProgramacion.getNombre());
			numeroProgramacionActual.setEstado(numeroProgramacion.getEstado());

			numeroProgramacionUpdate = numeroProgramacionService.save(numeroProgramacion);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el numero de programación en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El tiempo ha sido actualizado con éxito!");
		response.put("numeroProgramacion", numeroProgramacionUpdate);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured("ROLE_ADMIN")
	@DeleteMapping("/numeros-programaciones/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {

		Map<String, Object> response = new HashMap<>();

		try {
			NumeroProgramacion numeroProgramacion = numeroProgramacionService.findById(id);
			numeroProgramacionService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el tiempo de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El número de programación eliminado con éxito!");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}


}
