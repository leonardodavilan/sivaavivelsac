package pe.com.avivel.sistemas.siva.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pe.com.avivel.sistemas.siva.models.entity.produccion.PrdGranja;
import pe.com.avivel.sistemas.siva.models.services.spec.IPrdGranjaService;

import java.util.List;

@CrossOrigin(origins = { "http://localhost:4200","*" })
@RestController
@RequestMapping("/api")
public class PrdGranjaRestController {

	@Autowired
	private IPrdGranjaService prdGranjaService;

	@GetMapping("/granjas/listar")
	public List<PrdGranja> index() {
		return prdGranjaService.findAll();
	}
	
	@GetMapping("/granjas/page/{page}")
	public Page<PrdGranja> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return prdGranjaService.findAll(pageable);
	}

}
