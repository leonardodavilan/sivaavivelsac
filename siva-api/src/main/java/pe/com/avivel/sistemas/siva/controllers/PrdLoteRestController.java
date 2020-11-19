package pe.com.avivel.sistemas.siva.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.avivel.sistemas.siva.models.dto.PrdLoteQueryDTO;
import pe.com.avivel.sistemas.siva.models.entity.produccion.PrdLote;
import pe.com.avivel.sistemas.siva.models.services.spec.IPrdLoteService;

import java.util.List;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class PrdLoteRestController {

	@Autowired
	private IPrdLoteService prdLoteService;

	@GetMapping("/lotes/listar")
	public List<PrdLote> index() {
		return prdLoteService.findAll();
	}
	
	@GetMapping("/lotes/page/{page}")
	public Page<PrdLote> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return prdLoteService.findAll(pageable);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/lotes/filtro/listar")
	public ResponseEntity<List<PrdLoteQueryDTO>> getLotes(@RequestParam(value = "filtroLote", defaultValue = "LEVANTE") String filtroLote) {
		return new ResponseEntity<>(prdLoteService.getLotesByFiltro(filtroLote), HttpStatus.OK);
	}

	@GetMapping(value = "{id}/lotes", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<PrdLoteQueryDTO>> getLotes(@PathVariable("id") Integer id) {
		return new ResponseEntity<>(prdLoteService.getLotesByGranjaId(id), HttpStatus.OK);
	}
}
