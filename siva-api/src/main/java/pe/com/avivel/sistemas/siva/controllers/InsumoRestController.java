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
import pe.com.avivel.sistemas.siva.models.dto.InsumoDTO;
import pe.com.avivel.sistemas.siva.models.dto.InsumoDetalladoDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Insumo;
import pe.com.avivel.sistemas.siva.models.services.spec.IInsumoService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class InsumoRestController {

	@Autowired
	private IInsumoService insumoService;
	
	// private final Logger log = LoggerFactory.getLogger(ClienteRestController.class);

	@GetMapping("/insumos")
	public List<Insumo> index() { return insumoService.findAll();
	}

	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/insumos/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {

		Insumo insumo = null;
		Map<String, Object> response = new HashMap<>();

		try {
			insumo = insumoService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(insumo == null) {
			response.put("mensaje", "El insumo ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Insumo>(insumo, HttpStatus.OK);
	}

	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/insumos/tipos/{tipoInsumoId}")
	public ResponseEntity<?> findAllByTipo(@PathVariable Integer tipoInsumoId) {

		List<Insumo> insumo = null;
		Map<String, Object> response = new HashMap<>();

		try {
			insumo = insumoService.findAllByTipo(tipoInsumoId);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(insumo == null) {
			response.put("mensaje", "Los insumos con familia ID: ".concat(tipoInsumoId.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<Insumo>>(insumo, HttpStatus.OK);
	}



    @GetMapping("/insumos/page/{page}")
    public Page<Insumo> index(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return insumoService.findAll(pageable);
    }

	@PostMapping("/insumos-add/v1")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Integer> saveInsumoMultiple(@Valid @RequestBody InsumoDTO insumoDTO) {
		return new ResponseEntity<>(insumoService.saveInsumoMultiple(insumoDTO), HttpStatus.CREATED);
	}
	@PostMapping("/insumos-add")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> saveInsumoDetallado(@Valid @RequestBody InsumoDetalladoDTO insumoDetalladoDTO, BindingResult result) {
		Integer numberSave = null;
		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
			try{
				numberSave= insumoService.saveInsumoDetallado(insumoDetalladoDTO);
			}catch (DataAccessException e){
				response.put("mensaje", "Error al realizar el insert en la base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El cliente ha sido creado con éxito!");
			response.put("InsumoDetalladoId", numberSave);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		//return new ResponseEntity<>(insumoService.saveInsumoDetallado(insumoDetalladoDTO), HttpStatus.CREATED);
	}



}
