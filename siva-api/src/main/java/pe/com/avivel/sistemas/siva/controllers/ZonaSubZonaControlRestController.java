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
import pe.com.avivel.sistemas.siva.models.entity.roedor.ZonaSubZonaControl;
import pe.com.avivel.sistemas.siva.models.services.spec.IZonaSubZonaControlService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class ZonaSubZonaControlRestController {

	@Autowired
	private IZonaSubZonaControlService zonaSubZonaControlService;
	
	// private final Logger log = LoggerFactory.getLogger(ClienteRestController.class);

	@GetMapping("/zona-subzona-control")
	public List<ZonaSubZonaControl> index() { return zonaSubZonaControlService.findAll();
	}

	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/zona-subzona-control-by-sub-zona/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {


		List<ZonaSubZonaControl> zonaSubZonaControl = null;
		Map<String, Object> response = new HashMap<>();

		try {
			zonaSubZonaControl = zonaSubZonaControlService.findAllBySubZonaControl(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(zonaSubZonaControl == null) {
			response.put("mensaje", "El zona sub zona control ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<ZonaSubZonaControl>>(zonaSubZonaControl, HttpStatus.OK);
	}




    @GetMapping("/zona-subzona-control/page/{page}")
    public Page<ZonaSubZonaControl> index(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return zonaSubZonaControlService.findAll(pageable);
    }


}
