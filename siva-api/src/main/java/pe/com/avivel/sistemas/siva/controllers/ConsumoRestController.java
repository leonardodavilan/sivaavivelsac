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
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Consumo;
import pe.com.avivel.sistemas.siva.models.services.spec.IConsumoService;
import pe.com.avivel.sistemas.siva.util.ConverterUtil;


import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200","*" })
@RestController
@RequestMapping("/api")
public class ConsumoRestController {

	@Autowired
	private IConsumoService consumoService;

	@GetMapping("/consumos-by-filtro")
	public ResponseEntity<List<Consumo>> listar(@RequestParam("zonasubzonacontrolId") Integer zonasubzonacontrolId,
													  @RequestParam("fechaDesde") Long fechaDesde,
													  @RequestParam("fechaHasta") Long fechaHasta,
													  @RequestParam("prdGranjaId") Integer prdGranjaId) {

		FiltroConsumoDTO filtrocontrolroedDTO = new FiltroConsumoDTO();

		filtrocontrolroedDTO.setFechaDesde(ConverterUtil.toDate(fechaDesde));
		filtrocontrolroedDTO.setFechaHasta(ConverterUtil.toDate(fechaHasta));
		filtrocontrolroedDTO.setPrdGranjaId(prdGranjaId);
		filtrocontrolroedDTO.setZonasubzonacontrolId(zonasubzonacontrolId);

		return new ResponseEntity<>(consumoService.findAllByFiltro(filtrocontrolroedDTO),HttpStatus.OK);
	}

	@GetMapping("/consumos")
	public List<Consumo> index() {
		return consumoService.findAll();
	}


	@GetMapping("/consumos/page/{page}")
	public Page<Consumo> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return consumoService.findAll(pageable);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/consumos/{id}")
	public ResponseEntity<?> show(@PathVariable Integer id) {
		
		Consumo consumo = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			consumo = consumoService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(consumo == null) {
			response.put("mensaje", "El consumo ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Consumo>(consumo, HttpStatus.OK);
	}
	
	//@Secured("ROLE_ADMIN")
	@PostMapping("/consumo/add")
	public ResponseEntity<?> create(@Valid @RequestBody Consumo consumo, BindingResult result) {
		
		Consumo consumoNew = null;
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
			consumoNew = consumoService.save(consumo);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El consumo ha sido creado con éxito!");
		response.put("consumo", consumoNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/consumo/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Consumo consumo, BindingResult result, @PathVariable Integer id) {

		Consumo consumoActual = consumoService.findById(id);

		Consumo consumoUpdated = null;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (consumoActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el consumo ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			consumoActual.setInsumoMaterial(consumo.getInsumoMaterial());
			consumoActual.setInsumoRodenticida(consumo.getInsumoRodenticida());
			consumoActual.setCantidadUsoRodenticida(consumo.getCantidadUsoRodenticida());
			consumoActual.setUnidadMedida(consumo.getUnidadMedida());
			consumoActual.setNumZonaControl(consumo.getNumZonaControl());
			consumoActual.setTotalCodsMateriales(consumo.getTotalCodsMateriales());
			consumoActual.setNumsInoperativos(consumo.getNumsInoperativos());
			consumoActual.setNumMaterial(consumo.getNumMaterial());
			consumoActual.setTipoCebo(consumo.getTipoCebo());
			consumoActual.setControlRoedor(consumo.getControlRoedor());
			consumoActual.setCapturas(consumo.getCapturas());
			consumoActual.setEstado(consumo.getEstado());
			consumoUpdated = consumoService.save(consumoActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el consumo en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El consumo ha sido actualizado con éxito!");
		response.put("consumo", consumoUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/consumo/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {

		Consumo consumoEliminado = consumoService.findById(id);
		Map<String, Object> response = new HashMap<>();
		
		try {
		    consumoService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el consumo de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El consumo eliminado con éxito!");
		response.put("consumo", consumoEliminado);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
