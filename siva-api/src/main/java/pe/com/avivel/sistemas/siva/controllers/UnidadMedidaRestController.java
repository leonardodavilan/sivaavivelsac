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
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.UnidadMedida;
import pe.com.avivel.sistemas.siva.models.services.spec.IUnidadMedidaService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200","*" })
@RestController
@RequestMapping("/api")
public class UnidadMedidaRestController {

	@Autowired
	private IUnidadMedidaService unidadMedidaService;

	@GetMapping("/unidadesMedidas")
	public List<UnidadMedida> index() {
		return unidadMedidaService.findAll();
	}

	@GetMapping("/unidadesMedidas/page/{page}")
	public Page<UnidadMedida> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return unidadMedidaService.findAll(pageable);
	}

	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/undiadMedida/{id}")
	public ResponseEntity<?> show(@PathVariable Integer id) {

		UnidadMedida unidadMedida = null;
		Map<String, Object> response = new HashMap<>();

		try {
			unidadMedida = unidadMedidaService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(unidadMedida == null) {
			response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<UnidadMedida>(unidadMedida, HttpStatus.OK);
	}

	//@Secured("ROLE_ADMIN")
	@PostMapping("/unidadMedida")
	public ResponseEntity<?> create(@Valid @RequestBody UnidadMedida unidadMedida, BindingResult result) {

		UnidadMedida unidadMedidaNew = null;
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
			unidadMedidaNew = unidadMedidaService.save(unidadMedida);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La unidad de medida ha sido creado con éxito!");
		response.put("unidad de medida", unidadMedidaNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured("ROLE_ADMIN")
	@PutMapping("/undidadMedida/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody UnidadMedida unidadMedida, BindingResult result, @PathVariable Integer id) {

		UnidadMedida unidadMedidaActual = unidadMedidaService.findById(id);

		UnidadMedida unidadMedidaUpdate = null;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (unidadMedidaActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el cliente ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			unidadMedidaActual.setCodigo(unidadMedida.getCodigo());
			unidadMedidaActual.setNombre(unidadMedida.getNombre());
			unidadMedidaActual.setSimbolo(unidadMedida.getSimbolo());
			unidadMedidaActual.setEstado(unidadMedida.getEstado());


			unidadMedidaUpdate = unidadMedidaService.save(unidadMedidaActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La unidad de medida ha sido actualizado con éxito!");
		response.put("Unidad de Medida", unidadMedidaUpdate);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured("ROLE_ADMIN")
	@DeleteMapping("/unidadMedida/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {

		Map<String, Object> response = new HashMap<>();

		try {
			UnidadMedida unidadMedida = unidadMedidaService.findById(id);
			unidadMedidaService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el cliente de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La unidad de medida ha sido eliminado con éxito!");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
