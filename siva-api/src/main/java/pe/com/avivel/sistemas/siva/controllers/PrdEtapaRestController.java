package pe.com.avivel.sistemas.siva.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pe.com.avivel.sistemas.siva.models.entity.produccion.PrdEtapa;
import pe.com.avivel.sistemas.siva.models.services.spec.IPrdEtapaService;

import java.util.List;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class PrdEtapaRestController {

	@Autowired
	private IPrdEtapaService prdEtapaService;

	@GetMapping("/etapas/listar")
	public List<PrdEtapa> index() {
		return prdEtapaService.findAll();
	}
	
	@GetMapping("/etapas/page/{page}")
	public Page<PrdEtapa> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return prdEtapaService.findAll(pageable);
	}
}
