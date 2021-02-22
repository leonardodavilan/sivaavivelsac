package pe.com.avivel.sistemas.siva.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.MetodoVacuna;
import pe.com.avivel.sistemas.siva.models.services.spec.IMetodoVacunaService;

import java.util.List;

@CrossOrigin(origins = { "http://localhost:4200","*" })
@RestController
@RequestMapping("/api")
public class MetodoVacunaRestController {

	@Autowired
	private IMetodoVacunaService metodoVacunaService;

	@GetMapping("/metodos/listar")
	public List<MetodoVacuna> index() {
		return metodoVacunaService.findAll();
	}
	
	@GetMapping("/metodos/page/{page}")
	public Page<MetodoVacuna> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return metodoVacunaService.findAll(pageable);
	}

}
