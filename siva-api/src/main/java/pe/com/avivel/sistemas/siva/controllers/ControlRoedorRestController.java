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
import pe.com.avivel.sistemas.siva.models.dto.FiltroConsumoDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.ControlRoedor;
import pe.com.avivel.sistemas.siva.models.services.spec.IControlRoedorService;
import pe.com.avivel.sistemas.siva.util.ConverterUtil;


import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200","*" })
@RestController
@RequestMapping("/api")
public class ControlRoedorRestController {

	@Autowired
	private IControlRoedorService controlRoedorService;
	
	@GetMapping("/controlroeds")
	public List<ControlRoedor> index() {
		return controlRoedorService.findAll();
	}

	@GetMapping("/controlroeds-by-filtro")
	public ResponseEntity<List<ControlRoedor>> listar(@RequestParam("zonasubzonacontrolId") Integer zonasubzonacontrolId,
													  @RequestParam("fechaDesde") Long fechaDesde,
													  @RequestParam("fechaHasta") Long fechaHasta,
													  @RequestParam("prdGranjaId") Integer prdGranjaId) {

		FiltroConsumoDTO filtrocontrolroedDTO = new FiltroConsumoDTO();

		filtrocontrolroedDTO.setFechaDesde(ConverterUtil.toDate(fechaDesde));
		filtrocontrolroedDTO.setFechaHasta(ConverterUtil.toDate(fechaHasta));
		filtrocontrolroedDTO.setPrdGranjaId(prdGranjaId);
		filtrocontrolroedDTO.setZonasubzonacontrolId(zonasubzonacontrolId);

		return new ResponseEntity<>(controlRoedorService.findAllByFiltro(filtrocontrolroedDTO),HttpStatus.OK);
	}
	
	
	@GetMapping("/controlroeds/page/{page}")
	public Page<ControlRoedor> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return controlRoedorService.findAll(pageable);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/controlroeds/{id}")
	public ResponseEntity<?> show(@PathVariable Integer id) {
		
		ControlRoedor controlRoedor = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			controlRoedor = controlRoedorService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(controlRoedor == null) {
			response.put("mensaje", "El controlroed ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<ControlRoedor>(controlRoedor, HttpStatus.OK);
	}
	
	//@Secured("ROLE_ADMIN")
	@PostMapping("/controlroed/add")
	public ResponseEntity<?> create(@Valid @RequestBody ControlRoedor controlRoedor, BindingResult result) {

		ControlRoedor controlRoedorNew = null;
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
			controlRoedorNew = controlRoedorService.save(controlRoedor);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El controlroed ha sido creado con éxito!");
		response.put("controlroedor", controlRoedorNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/controlroed/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody ControlRoedor controlRoedor, BindingResult result, @PathVariable Integer id) {

		ControlRoedor controlRoedorActual = controlRoedorService.findById(id);

		ControlRoedor controlRoedorUpdated = null;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (controlRoedorActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el controlroed ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			controlRoedorActual.setFecha(controlRoedor.getFecha());
			controlRoedorActual.setPrdGranja(controlRoedor.getPrdGranja());
			controlRoedorActual.setZonaSubZonaControl(controlRoedor.getZonaSubZonaControl());
			controlRoedorActual.setConsumos(controlRoedor.getConsumos());
			
			controlRoedorUpdated = controlRoedorService.save(controlRoedorActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el controlroed en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El controlroed ha sido actualizado con éxito!");
		response.put("controlroedor", controlRoedorUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/controlroed/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			ControlRoedor controlroed = controlRoedorService.findById(id);
		    controlRoedorService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el control roedor de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El control roedor eliminado con éxito!");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
