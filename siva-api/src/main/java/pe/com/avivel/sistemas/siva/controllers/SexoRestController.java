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
import pe.com.avivel.sistemas.siva.models.entity.roedor.Sexo;
import pe.com.avivel.sistemas.siva.models.services.spec.ISexoService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class SexoRestController {

	@Autowired
	private ISexoService sexoService;

	@GetMapping("/sexos")
	public List<Sexo> index() {
		return sexoService.findAll();
	}
	
	@GetMapping("/sexos/page/{page}")
	public Page<Sexo> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return sexoService.findAll(pageable);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/sexos/{id}")
	public ResponseEntity<?> show(@PathVariable Integer id) {

		Sexo sexo = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			sexo = sexoService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(sexo == null) {
			response.put("mensaje", "El tipo de cebo ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Sexo>(sexo, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/sexos")
	public ResponseEntity<?> create(@Valid @RequestBody Sexo sexo, BindingResult result) {
		
		Sexo sexoNew = null;
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
			sexoNew = sexoService.save(sexo);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El sexo ha sido creado con éxito!");
		response.put("sexo", sexoNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/sexos/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Sexo sexo, BindingResult result, @PathVariable Integer id) {

		Sexo sexoActual = sexoService.findById(id);

		Sexo sexoUpdate = null;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (sexoActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el tipo de cebo ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			sexoActual.setNombre(sexo.getNombre());
			sexoActual.setEstado(sexo.getEstado());

			sexoUpdate = sexoService.save(sexo);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el sexo en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El area captura ha sido actualizado con éxito!");
		response.put("sexo", sexoUpdate);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/sexos/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			Sexo sexo = sexoService.findById(id);
			sexoService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el tipo cebo de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El tipo cebo eliminado con éxito!");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}


}
