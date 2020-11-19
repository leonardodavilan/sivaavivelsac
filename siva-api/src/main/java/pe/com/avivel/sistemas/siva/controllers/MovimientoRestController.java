package pe.com.avivel.sistemas.siva.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.com.avivel.sistemas.siva.models.dto.FiltroMovimientoDTO;
import pe.com.avivel.sistemas.siva.models.dto.MovimientoQueryDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Movimiento;
import pe.com.avivel.sistemas.siva.models.services.spec.IMovimientoService;
import pe.com.avivel.sistemas.siva.util.ConverterUtil;

import javax.servlet.ServletContext;
import javax.sql.DataSource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class MovimientoRestController {

	private static final Logger logger = LoggerFactory.getLogger(MovimientoRestController.class);
	private final ServletContext servletContext;
	private final DataSource dataSource;
	private final IMovimientoService movimientoService;


	@Autowired
	public MovimientoRestController(ServletContext servletContext, DataSource dataSource, IMovimientoService movimientoService) {
		this.servletContext = servletContext;
		this.dataSource = dataSource;
		this.movimientoService =movimientoService;
	}

	// private final Logger log = LoggerFactory.getLogger(ClienteRestController.class);

	@GetMapping("/movimientos/v1/listar")
	public List<Movimiento> index() { return movimientoService.findAll();
	}


	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/movimientos/{id}")
	public ResponseEntity<?> show(@PathVariable Integer id) {

		Movimiento movimiento = null;
		Map<String, Object> response = new HashMap<>();

		try {
			movimiento = movimientoService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(movimiento == null) {
			response.put("mensaje", "El movimiento de insumo ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Movimiento>(movimiento, HttpStatus.OK);
	}

	@GetMapping("/movimientos")
	public ResponseEntity<List<MovimientoQueryDTO>> listar(@RequestParam("tipoMovimientoId") Integer tipoMovimientoId,
														   @RequestParam("fechaDesde") Long fechaDesde,
														   @RequestParam("fechaHasta") Long fechaHasta,
														   @RequestParam("prdGranjaId") Integer prdGranjaId) {

		FiltroMovimientoDTO filtroMovimientoDTO = new FiltroMovimientoDTO();

		filtroMovimientoDTO.setFechaDesde(ConverterUtil.toDate(fechaDesde));
		filtroMovimientoDTO.setFechaHasta(ConverterUtil.toDate(fechaHasta));
		filtroMovimientoDTO.setPrdGranjaId(prdGranjaId);
		filtroMovimientoDTO.setTipoMovimientoId(tipoMovimientoId);

		return new ResponseEntity<>(movimientoService.findAllByFiltro(filtroMovimientoDTO),HttpStatus.OK);
	}


	@PostMapping("/movimiento/add")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> saveVacunacion(@Valid @RequestBody Movimiento movimiento, BindingResult result) {

		Movimiento newMovimiento;

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
			newMovimiento =  movimientoService.save(movimiento);
		}catch (DataAccessException e){
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El movimiento de insumo ha sido creado con éxito!");
		response.put("Movimiento", newMovimiento);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}


	@Secured("ROLE_ADMIN")
	@PutMapping("/movimiento/update/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Movimiento movimiento, BindingResult result, @PathVariable Integer id) {

		Movimiento movimientoActual = movimientoService.findById(id);

		Movimiento movimientoUpdated = null;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (movimientoActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el registro de vacunación con ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			movimientoActual.setFecha(movimiento.getFecha());
			movimientoActual.setCantidad(movimiento.getCantidad());
			movimientoActual.setTipoMovimiento(movimiento.getTipoMovimiento());
			movimientoActual.setPrdGranja(movimiento.getPrdGranja());
			movimientoActual.setPrdGranja(movimiento.getPrdGranja());
			movimientoActual.setInsumoProveedor(movimiento.getInsumoProveedor());
			movimientoActual.setEstado(movimiento.getEstado());
			movimientoActual.setLoteSerie(movimiento.getLoteSerie());
			movimientoActual.setGuiaFactura(movimiento.getGuiaFactura());

			movimientoUpdated = movimientoService.save(movimientoActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el registro de vacunación en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El registro de vacunación ha sido actualizado con éxito!");
		response.put("Insumo", movimientoUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}


	@Secured("ROLE_ADMIN")
	@DeleteMapping("/movimiento/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {

		Map<String, Object> response = new HashMap<>();

		try {
			Movimiento movimiento = movimientoService.findById(id);
			movimientoService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el movimiento de insumo de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El movimiento de insumo eliminado con éxito!");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}


	//REPORTES

}