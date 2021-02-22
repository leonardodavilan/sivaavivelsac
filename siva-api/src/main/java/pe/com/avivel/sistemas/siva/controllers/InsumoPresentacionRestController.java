package pe.com.avivel.sistemas.siva.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.InsumoPresentacion;
import pe.com.avivel.sistemas.siva.models.services.spec.IInsumoPresentacionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = { "http://localhost:4200","*" })
@RestController
@RequestMapping("/api")
public class InsumoPresentacionRestController {
	@Autowired
	private IInsumoPresentacionService insumoPresentacionService;

	// private final Logger log = LoggerFactory.getLogger(ClienteRestController.class);

	@GetMapping("/insumos-presentaciones")
	public List<InsumoPresentacion> index() { return insumoPresentacionService.findAll();
	}

	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/insumos-presentaciones/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {

		InsumoPresentacion insumoPresentacion = null;
		Map<String, Object> response = new HashMap<>();

		try {
			insumoPresentacion = insumoPresentacionService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(insumoPresentacion == null) {
			response.put("mensaje", "El insumo ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<InsumoPresentacion>(insumoPresentacion, HttpStatus.OK);
	}

	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("stock/granja/{granjaId}/insumos-presentaciones")
	public List<InsumoPresentacion> findAllByGranjaFromStock(@PathVariable Integer granjaId) {
		return insumoPresentacionService.findAllByGranjaFromStock(granjaId);
	}


	@GetMapping("/insumos-presentaciones/page/{page}")
	public Page<InsumoPresentacion> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 10);
		return insumoPresentacionService.findAll(pageable);
	}

}
