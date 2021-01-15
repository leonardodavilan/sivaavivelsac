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
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.TipoCebo;
import pe.com.avivel.sistemas.siva.models.services.spec.ITipoCeboService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class TipoCeboRestController {

	@Autowired
	private ITipoCeboService tipoCeboService;

	@GetMapping("/tipos-cebo")
	public List<TipoCebo> index() {
		return tipoCeboService.findAll();
	}
	
	@GetMapping("/tipos-cebo/page/{page}")
	public Page<TipoCebo> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return tipoCeboService.findAll(pageable);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/tipos-cebo/{id}")
	public ResponseEntity<?> show(@PathVariable Integer id) {

		TipoCebo tipoCebo = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			tipoCebo = tipoCeboService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(tipoCebo == null) {
			response.put("mensaje", "El tipo de cebo ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<TipoCebo>(tipoCebo, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/tipos-cebo")
	public ResponseEntity<?> create(@Valid @RequestBody TipoCebo tipoCebo, BindingResult result) {
		
		TipoCebo tipoCeboNew = null;
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
			tipoCeboNew = tipoCeboService.save(tipoCebo);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El tipo de cebo ha sido creado con éxito!");
		response.put("TipoCebo", tipoCeboNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/tipos-cebo/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody TipoCebo tipoCebo, BindingResult result, @PathVariable Integer id) {

		TipoCebo tipoCeboActual = tipoCeboService.findById(id);

		TipoCebo tipoCeboUpdated = null;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (tipoCeboActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el tipo de cebo ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			tipoCeboActual.setNombre(tipoCebo.getNombre());
			tipoCeboActual.setEstado(tipoCebo.getEstado());


			tipoCeboUpdated = tipoCeboService.save(tipoCebo);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el tipo cebo en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El cliente ha sido actualizado con éxito!");
		response.put("tipoCebo", tipoCeboUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/tipos-cebo/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			TipoCebo tipoCebo = tipoCeboService.findById(id);
			tipoCeboService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el tipo cebo de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El tipo cebo eliminado con éxito!");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}


}
