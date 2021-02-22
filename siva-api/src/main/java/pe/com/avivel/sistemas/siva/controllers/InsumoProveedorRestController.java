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
import pe.com.avivel.sistemas.siva.models.dto.InsumoProveedorQueryDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Insumo;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.InsumoPresentacion;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.InsumoProveedor;
import pe.com.avivel.sistemas.siva.models.services.spec.IInsumoPresentacionService;
import pe.com.avivel.sistemas.siva.models.services.spec.IInsumoProveedorService;
import pe.com.avivel.sistemas.siva.models.services.spec.IInsumoService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200","*" })
@RestController
@RequestMapping("/api")
public class InsumoProveedorRestController {

	@Autowired
	private IInsumoProveedorService insumoProveedorService;

	@Autowired
	private IInsumoPresentacionService insumoPresentacionService;

	@Autowired
	private IInsumoService insumoService;

	// private final Logger log = LoggerFactory.getLogger(ClienteRestController.class);

	@GetMapping("/insumos-proveedores")
	public List<InsumoProveedor> index() { return insumoProveedorService.findAll();
	}

	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/insumos-proveedores-by-presentacion/{insumoPresentacionId}")
	public List<InsumoProveedor> findAllByInsumoPresentacion(@PathVariable Integer insumoPresentacionId) {
		return insumoProveedorService.findAllByInsumoPresentacion(insumoPresentacionId);
	}


	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/insumos-proveedores-by-moneda/{monedaId}")
	public List<InsumoProveedor> findAllByInsumoPresentacionByMoneda(@PathVariable Integer monedaId) {
		return insumoProveedorService.findAllByInsumoPresentacionByMoneda(monedaId);
	}

	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/insumos-proveedores/subfamilia/{subFamiliaId}")
	public ResponseEntity<List<InsumoProveedorQueryDTO>> listarBSubFamiliaId(@PathVariable Integer subFamiliaId) {
		return new ResponseEntity<>(insumoProveedorService.findAllBySubFamilia(subFamiliaId),HttpStatus.OK);
	}


	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/insumos-proveedores/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {

		InsumoProveedor insumoProveedor = null;
		Map<String, Object> response = new HashMap<>();

		try {
			insumoProveedor = insumoProveedorService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(insumoProveedor == null) {
			response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<InsumoProveedor>(insumoProveedor, HttpStatus.OK);
	}

	@GetMapping("/insumos-proveedores/page/{page}")
	public Page<InsumoProveedor> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 10);
		return insumoProveedorService.findAll(pageable);
	}

	//@Secured("ROLE_ADMIN")
	@PostMapping("/insumo-proveedor")
	public ResponseEntity<?> create(@Valid @RequestBody InsumoProveedor insumoProveedor, BindingResult result) {

		InsumoProveedor insumoProveedorNew = null;

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
			insumoProveedorNew = insumoProveedorService.save(insumoProveedor);

		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El insumo ha sido creado con éxito!");
		response.put("insumo", insumoProveedorNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	//@Secured("ROLE_ADMIN")
	@PutMapping("/insumo-proveedor/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody InsumoProveedor insumoProveedor, BindingResult result, @PathVariable Long id) {

		InsumoProveedor insumoProveedorActual = insumoProveedorService.findById(id);

		InsumoProveedor insumoProveedorUpdated = null;

		InsumoPresentacion insumoPresentacionActual = insumoPresentacionService.findById(insumoProveedor.getInsumoPresentacion().getId());
		InsumoPresentacion insumoPresentacionUpdated = null;

		Insumo insumoActual = insumoService.findById(insumoProveedor.getInsumoPresentacion().getInsumo().getId());

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (insumoProveedorActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el cliente ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			insumoProveedorActual.setInsumoPresentacion(insumoProveedor.getInsumoPresentacion());
			//Actualizar presentacion
			insumoPresentacionActual.setCodigoRef(insumoProveedor.getInsumoPresentacion().getCodigoRef());
			insumoPresentacionActual.setCodigoSap(insumoProveedor.getInsumoPresentacion().getCodigoSap());
			insumoPresentacionActual.setPresentacion(insumoProveedor.getInsumoPresentacion().getPresentacion());
			insumoPresentacionActual.setUnidadMedida(insumoProveedor.getInsumoPresentacion().getUnidadMedida());
			insumoPresentacionActual.setInsumo(insumoProveedor.getInsumoPresentacion().getInsumo());
			//Actualizar insumo
			insumoActual.setDescripcion(insumoProveedor.getInsumoPresentacion().getInsumo().getDescripcion());
			insumoActual.setSubFamilia(insumoProveedor.getInsumoPresentacion().getInsumo().getSubFamilia());

			insumoPresentacionUpdated = insumoPresentacionService.save(insumoPresentacionActual);

			insumoProveedorActual.setProveedor(insumoProveedor.getProveedor());
			insumoProveedorActual.setPrecio(insumoProveedor.getPrecio());
			insumoProveedorActual.setMoneda(insumoProveedor.getMoneda());
			insumoProveedorActual.setEstado(insumoProveedor.getEstado());


			insumoProveedorUpdated = insumoProveedorService.save(insumoProveedorActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El insumo ha sido actualizado con éxito!");
		response.put("Insumo", insumoProveedorUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
}
