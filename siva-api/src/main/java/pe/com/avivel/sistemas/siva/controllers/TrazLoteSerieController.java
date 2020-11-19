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
import pe.com.avivel.sistemas.siva.models.entity.trazabilidad.LoteSerie;
import pe.com.avivel.sistemas.siva.models.services.spec.ITrazLoteSerieService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class TrazLoteSerieController {

	@Autowired
	private ITrazLoteSerieService trazLoteSerieService;

	@GetMapping("/loteseries/listar")
	public List<LoteSerie> index() {
		return trazLoteSerieService.findAll();
	}
	
	@GetMapping("/lotesseries/page/{page}")
	public Page<LoteSerie> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return trazLoteSerieService.findAll(pageable);
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/loteseries/add")
	public ResponseEntity<?> create(@Valid @RequestBody LoteSerie loteSerie, BindingResult result) {

		LoteSerie loteSerieNew = null;
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
			loteSerieNew = trazLoteSerieService.save(loteSerie);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El lote/serie ha sido creado con éxito!");
		response.put("loteSerie", loteSerieNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
}
