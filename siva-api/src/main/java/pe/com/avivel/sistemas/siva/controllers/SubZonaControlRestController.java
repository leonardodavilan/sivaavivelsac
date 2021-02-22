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
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.SubZonaControl;
import pe.com.avivel.sistemas.siva.models.services.spec.ISubZonaControlService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200","*" })
@RestController
@RequestMapping("/api")
public class SubZonaControlRestController {

	@Autowired
	private ISubZonaControlService subZonaControlService;

	@GetMapping("/subzonascontrol")
	public List<SubZonaControl> index() {
		return subZonaControlService.findAll();
	}
	
	@GetMapping("/subzonascontrol/page/{page}")
	public Page<SubZonaControl> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return subZonaControlService.findAll(pageable);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/subzonascontrol/{id}")
	public ResponseEntity<?> show(@PathVariable Integer id) {

		SubZonaControl subZonaControl = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			subZonaControl = subZonaControlService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(subZonaControl == null) {
			response.put("mensaje", "Sub zona control ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<SubZonaControl>(subZonaControl, HttpStatus.OK);
	}
	
	//@Secured("ROLE_ADMIN")
	@PostMapping("/subzonacontrol/add")
	public ResponseEntity<?> create(@Valid @RequestBody SubZonaControl subZonaControl, BindingResult result) {
		
		SubZonaControl subZonaControlNew = null;
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
			subZonaControlNew = subZonaControlService.save(subZonaControl);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La zona control ha sido creado con éxito!");
		response.put("subzonacontrol", subZonaControlNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/subzonascontrol/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody SubZonaControl subZonaControl, BindingResult result, @PathVariable Integer id) {

		SubZonaControl subZonaControlActual = subZonaControlService.findById(id);

		SubZonaControl subZonaControlUpdated = null;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (subZonaControlActual == null) {
			response.put("mensaje", "Error: no se pudo editar, la sub zona control ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			subZonaControlActual.setCodigo(subZonaControl.getCodigo());
			subZonaControlActual.setNombre(subZonaControl.getNombre());
			subZonaControlActual.setEstado(subZonaControl.getEstado());
			subZonaControlActual.setZonaControl(subZonaControl.getZonaControl());

			subZonaControlUpdated = subZonaControlService.save(subZonaControlActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar la sub zona control en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La sub zona control ha sido actualizado con éxito!");
		response.put("subzonacontrol", subZonaControlUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/subzonacontrol/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			SubZonaControl subZonaControl = subZonaControlService.findById(id);
		    subZonaControlService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar la sub zona control de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Sub zona control eliminado con éxito!");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
