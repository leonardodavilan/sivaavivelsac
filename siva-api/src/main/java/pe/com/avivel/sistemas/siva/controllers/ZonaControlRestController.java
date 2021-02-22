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
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.ZonaControl;
import pe.com.avivel.sistemas.siva.models.services.spec.IZonaControlService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200","*" })
@RestController
@RequestMapping("/api")
public class ZonaControlRestController {

	@Autowired
	private IZonaControlService zonaControlService;

	@GetMapping("/zonascontrol")
	public List<ZonaControl> index() {
		return zonaControlService.findAll();
	}
	
	@GetMapping("/zonascontrol/page/{page}")
	public Page<ZonaControl> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return zonaControlService.findAll(pageable);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/zonascontrol/{id}")
	public ResponseEntity<?> show(@PathVariable Integer id) {

		ZonaControl zonaControl = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			zonaControl = zonaControlService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(zonaControl == null) {
			response.put("mensaje", "Zona control ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<ZonaControl>(zonaControl, HttpStatus.OK);
	}
	
	//@Secured("ROLE_ADMIN")
	@PostMapping("/zonascontrol")
	public ResponseEntity<?> create(@Valid @RequestBody ZonaControl zonaControl, BindingResult result) {
		
		ZonaControl zonaControlNew = null;
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
			zonaControlNew = zonaControlService.save(zonaControl);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La zona control ha sido creado con éxito!");
		response.put("zonacontrol", zonaControlNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/zonascontrol/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody ZonaControl zonaControl, BindingResult result, @PathVariable Integer id) {

		ZonaControl zonaControlActual = zonaControlService.findById(id);

		ZonaControl zonaControlUpdated = null;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (zonaControlActual == null) {
			response.put("mensaje", "Error: no se pudo editar, la zona control ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			zonaControlActual.setCodigo(zonaControl.getCodigo());
			zonaControlActual.setNombre(zonaControl.getNombre());
			zonaControlActual.setEstado(zonaControl.getEstado());
			zonaControlActual.setSubZonaControl(zonaControl.getSubZonaControl());

			zonaControlUpdated = zonaControlService.save(zonaControlActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar la zona control en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La zona control ha sido actualizado con éxito!");
		response.put("zonacontrol", zonaControlUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/zonacontrol/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			ZonaControl zonaControl = zonaControlService.findById(id);
		    zonaControlService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar zona control de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Zona control eliminado con éxito!");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
