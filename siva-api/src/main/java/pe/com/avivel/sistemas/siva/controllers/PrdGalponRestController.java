package pe.com.avivel.sistemas.siva.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pe.com.avivel.sistemas.siva.models.entity.produccion.PrdGalpon;
import pe.com.avivel.sistemas.siva.models.services.spec.IPrdGalponService;

import java.util.List;

@CrossOrigin(origins = { "http://localhost:4200","*" })
@RestController
@RequestMapping("/api")
public class PrdGalponRestController {

	@Autowired
	private IPrdGalponService prdGalponService;

	@GetMapping("/galpones/listar")
	public List<PrdGalpon> index() {
		return prdGalponService.findAll();
	}
	
	@GetMapping("/galpones/page/{page}")
	public Page<PrdGalpon> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return prdGalponService.findAll(pageable);
	}

}
