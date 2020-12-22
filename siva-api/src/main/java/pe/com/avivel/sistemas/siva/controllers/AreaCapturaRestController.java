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
import pe.com.avivel.sistemas.siva.models.entity.roedor.AreaCaptura;
import pe.com.avivel.sistemas.siva.models.services.spec.IAreaCapturaService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class AreaCapturaRestController {

	@Autowired
	private IAreaCapturaService areaCapturaService;

	@GetMapping("/areascaptura")
	public List<AreaCaptura> index() {
		return areaCapturaService.findAll();
	}
	
	@GetMapping("/areascaptura/page/{page}")
	public Page<AreaCaptura> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return areaCapturaService.findAll(pageable);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/areascaptura/{id}")
	public ResponseEntity<?> show(@PathVariable Integer id) {

		AreaCaptura areaCaptura = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			areaCaptura = areaCapturaService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(areaCaptura == null) {
			response.put("mensaje", "El tipo de cebo ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<AreaCaptura>(areaCaptura, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/areascaptura")
	public ResponseEntity<?> create(@Valid @RequestBody AreaCaptura areaCaptura, BindingResult result) {
		
		AreaCaptura areaCapturaNew = null;
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
			areaCapturaNew = areaCapturaService.save(areaCaptura);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El tipo de cebo ha sido creado con éxito!");
		response.put("areacaptura", areaCapturaNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/areascaptura/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody AreaCaptura areaCaptura, BindingResult result, @PathVariable Integer id) {

		AreaCaptura areaCapturaActual = areaCapturaService.findById(id);

		AreaCaptura areaCapturaUpdated = null;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (areaCapturaActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el tipo de cebo ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			areaCapturaActual.setNombre(areaCaptura.getNombre());
			areaCapturaActual.setEstado(areaCaptura.getEstado());

			areaCapturaUpdated = areaCapturaService.save(areaCaptura);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el tipo cebo en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El area captura ha sido actualizado con éxito!");
		response.put("areacaptura", areaCapturaUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/areascaptura/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			AreaCaptura areaCaptura = areaCapturaService.findById(id);
			areaCapturaService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el tipo cebo de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El tipo cebo eliminado con éxito!");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}


}
