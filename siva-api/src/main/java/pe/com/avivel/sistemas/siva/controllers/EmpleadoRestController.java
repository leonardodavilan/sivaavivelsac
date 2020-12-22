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
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Empleado;
import pe.com.avivel.sistemas.siva.models.services.spec.IEmpleadoService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class EmpleadoRestController {

	@Autowired
	private IEmpleadoService empleadoService;

	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/empleados")
	public List<Empleado> index() {
		return empleadoService.findAll();
	}

	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/empleados/page/{page}")
	public Page<Empleado> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return empleadoService.findAll(pageable);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/empleados/{id}")
	public ResponseEntity<?> show(@PathVariable Integer id) {
		
		Empleado empleado = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			empleado = empleadoService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(empleado == null) {
			response.put("mensaje", "El empleado ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Empleado>(empleado, HttpStatus.OK);
	}
	
	//@Secured("ROLE_ADMIN")
	@PostMapping("/empleados")
	public ResponseEntity<?> create(@Valid @RequestBody Empleado empleado, BindingResult result) {
		
		Empleado empleadoNew = null;
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
			empleadoNew = empleadoService.save(empleado);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El empleado ha sido creado con éxito!");
		response.put("empleado", empleadoNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/empleado/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Empleado empleado, BindingResult result, @PathVariable Integer id) {

		Empleado empleadoActual = empleadoService.findById(id);

		Empleado empleadoUpdated = null;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (empleadoActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el empleado ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			empleadoActual.setNombre(empleado.getNombre());
			empleadoActual.setEstado(empleado.getEstado());
			empleadoActual.setTipoEmpleado(empleado.getTipoEmpleado());


			empleadoUpdated = empleadoService.save(empleadoActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el empleado en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El cliente ha sido actualizado con éxito!");
		response.put("empleado", empleadoUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/empleado/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			Empleado empleado = empleadoService.findById(id);
		    empleadoService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el empleado de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El empleado eliminado con éxito!");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
