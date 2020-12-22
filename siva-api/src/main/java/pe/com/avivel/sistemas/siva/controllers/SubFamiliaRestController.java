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
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.SubFamilia;
import pe.com.avivel.sistemas.siva.models.services.spec.ISubFamiliaService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class SubFamiliaRestController {


	@Autowired
	private ISubFamiliaService subFamiliaService;

	@GetMapping("/sub-familias")
	public List<SubFamilia> index() {
		return subFamiliaService.findAll();
	}

	@GetMapping("/sub-familias/page/{page}")
	public Page<SubFamilia> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return subFamiliaService.findAll(pageable);
	}

	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/sub-familia/{id}")
	public ResponseEntity<?> show(@PathVariable Integer id) {

		SubFamilia subFamilia = null;
		Map<String, Object> response = new HashMap<>();

		try {
			subFamilia = subFamiliaService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(subFamilia == null) {
			response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<SubFamilia>(subFamilia, HttpStatus.OK);
	}

	//@Secured("ROLE_ADMIN")
	@PostMapping("/sub-familia")
	public ResponseEntity<?> create(@Valid @RequestBody SubFamilia subFamilia, BindingResult result) {

		SubFamilia subFamiliaNew = null;
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
			subFamiliaNew = subFamiliaService.save(subFamilia);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El cliente ha sido creado con éxito!");
		response.put("proveedor", subFamiliaNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured("ROLE_ADMIN")
	@PutMapping("/sub-familia/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody SubFamilia subFamilia, BindingResult result, @PathVariable Integer id) {

		SubFamilia subFamiliaActual = subFamiliaService.findById(id);

		SubFamilia subFamiliaUpdate = null;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (subFamiliaActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el cliente ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			subFamiliaActual.setCodigo(subFamilia.getCodigo());
			subFamiliaActual.setDescripcion(subFamilia.getDescripcion());
			subFamiliaActual.setFamilia(subFamilia.getFamilia());


			subFamiliaUpdate = subFamiliaService.save(subFamiliaActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El cliente ha sido actualizado con éxito!");
		response.put("cliente", subFamiliaUpdate);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured("ROLE_ADMIN")
	@DeleteMapping("/sub-familia/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {

		Map<String, Object> response = new HashMap<>();

		try {
			SubFamilia subFamilia = subFamiliaService.findById(id);
			subFamiliaService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el cliente de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El cliente eliminado con éxito!");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}


}
