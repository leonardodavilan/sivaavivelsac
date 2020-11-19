package pe.com.avivel.sistemas.siva.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Moneda;
import pe.com.avivel.sistemas.siva.models.services.spec.IMonedaService;

import java.util.List;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class MonedaRestController {

	@Autowired
	private IMonedaService monedaService;

	@GetMapping("/monedas")
	public List<Moneda> index() {
		return monedaService.findAll();
	}
	
	@GetMapping("/monedas/page/{page}")
	public Page<Moneda> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return monedaService.findAll(pageable);
	}

}
