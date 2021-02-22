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
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.TipoMovimiento;
import pe.com.avivel.sistemas.siva.models.services.spec.ITipoMovimientoService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200","*" })
@RestController
@RequestMapping("/api")
public class TipoMovimientoRestController {
	@Autowired
	private ITipoMovimientoService tipoMovimientoService;

	@GetMapping("/tipo-movimiento")
	public List<TipoMovimiento> index() {
		return tipoMovimientoService.findAll();
	}

	@GetMapping("/tipo-movimiento/page/{page}")
	public Page<TipoMovimiento> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return tipoMovimientoService.findAll(pageable);
	}

	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/tipo-movimiento/{id}")
	public ResponseEntity<?> show(@PathVariable Integer id) {

		TipoMovimiento tipoMovimiento = null;
		Map<String, Object> response = new HashMap<>();

		try {
			tipoMovimiento = tipoMovimientoService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(tipoMovimiento == null) {
			response.put("mensaje", "El tipo de movimiento ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<TipoMovimiento>(tipoMovimiento, HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/tipo-movimiento")
	public ResponseEntity<?> create(@Valid @RequestBody TipoMovimiento tipoMovimiento, BindingResult result) {

		TipoMovimiento tipoMovimientoNew = null;
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
			tipoMovimientoNew = tipoMovimientoService.save(tipoMovimiento);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El tipo de movimiento ha sido creado con éxito!");
		response.put("TipoMovimiento", tipoMovimientoNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured("ROLE_ADMIN")
	@PutMapping("/tipo-movimiento/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody TipoMovimiento tipoMovimiento, BindingResult result, @PathVariable Integer id) {

		TipoMovimiento tipoMovimientoActual = tipoMovimientoService.findById(id);

		TipoMovimiento tipoMovimientoUpdate = null;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (tipoMovimientoActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el tipo de movimiento ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			tipoMovimientoActual.setNombre(tipoMovimiento.getNombre());
			tipoMovimientoActual.setEstado(tipoMovimiento.getEstado());


			tipoMovimientoUpdate = tipoMovimientoService.save(tipoMovimientoActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El cliente ha sido actualizado con éxito!");
		response.put("TipoMovimiento", tipoMovimientoUpdate);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured("ROLE_ADMIN")
	@DeleteMapping("/tipo-movimiento/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {

		Map<String, Object> response = new HashMap<>();

		try {
			TipoMovimiento subFamilia = tipoMovimientoService.findById(id);
			tipoMovimientoService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el tipo de movimiento de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El tipo de movimiento eliminado con éxito!");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}


}
