package pe.com.avivel.sistemas.siva.controllers;


import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.com.avivel.sistemas.siva.models.dto.StockInsumoDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.StockInsumo;
import pe.com.avivel.sistemas.siva.models.services.spec.IProgramacionService;
import pe.com.avivel.sistemas.siva.models.services.spec.IStockInsumoService;
import pe.com.avivel.sistemas.siva.util.JasperReportUtil;

import javax.sql.DataSource;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class StockInsumoRestController {

	@Autowired
	private IStockInsumoService stockInsumoService;

	private final DataSource dataSource;
	private static final Logger logger = LoggerFactory.getLogger(VacunacionRestController.class);

	@Autowired
	public StockInsumoRestController(IStockInsumoService stockInsumoService, DataSource dataSource){
		this.stockInsumoService = stockInsumoService;
		this.dataSource = dataSource;
	}


	@GetMapping("/stockInsumo")
	public List<StockInsumo> index() {
		return stockInsumoService.findAll();
	}
	
	@GetMapping("/stockInsumo/page/{page}")
	public Page<StockInsumo> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return stockInsumoService.findAll(pageable);
	}

	@GetMapping(value = "/sotckinsumos/granja/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<StockInsumoDTO>> getStockInsumos(@PathVariable("id") Integer id) {

		return new ResponseEntity<>(stockInsumoService.findAllByGranjaId(id), HttpStatus.OK);

	}
	
	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/stockInsumo/{id}")
	public ResponseEntity<?> show(@PathVariable Integer id) {

		StockInsumo stockInsumo = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			stockInsumo = stockInsumoService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(stockInsumo == null) {
			response.put("mensaje", "El stock insumo ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<StockInsumo>(stockInsumo, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/stockInsumo")
	public ResponseEntity<?> create(@Valid @RequestBody StockInsumo stockInsumo, BindingResult result) {
		
		StockInsumo stockInsumoNew = null;
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
			stockInsumoNew = stockInsumoService.save(stockInsumo);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El tipo de cebo ha sido creado con éxito!");
		response.put("stockinsumo", stockInsumoNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/stockInsumo/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody StockInsumo stockInsumo, BindingResult result, @PathVariable Integer id) {

		StockInsumo stockInsumoActual = stockInsumoService.findById(id);

		StockInsumo stockInsumoUpdated = null;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (stockInsumoActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el tipo de cebo ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			stockInsumoActual.setCantidad(stockInsumo.getCantidad());
			stockInsumoActual.setInsumoProveedor(stockInsumo.getInsumoProveedor());
			stockInsumoActual.setLoteSerie(stockInsumo.getLoteSerie());
			stockInsumoActual.setPrdGranja(stockInsumo.getPrdGranja());

			stockInsumoUpdated = stockInsumoService.save(stockInsumoActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el tipo cebo en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El stock insumo ha sido actualizado con éxito!");
		response.put("stockinsumoupdate", stockInsumoUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/stockInsumo/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			StockInsumo stockInsumo = stockInsumoService.findById(id);
			stockInsumoService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el stock insumo de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El stock insumo eliminado con éxito!");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}


	@GetMapping("/saldo-report")
	public ResponseEntity<byte[]> generateReportByGranja(@RequestParam("prdGranjaId") Integer prdGranjaId,
														 @RequestParam(value = "tipo", defaultValue = "pdf") String tipo)throws FileNotFoundException, JRException {
		byte[] bytes = null;
		String data = null;
		Map<String, Object> parametros = new HashMap<>();

		parametros.put("prdGranjaId", prdGranjaId);

		String reporte = null;

		reporte = "rpt_saldo_movi.jrxml";

		try (Connection connection = dataSource.getConnection()) {
			if (tipo.equalsIgnoreCase("pdf")) {
				bytes = JasperReportUtil.exportReportToPdfV2(reporte,connection,parametros);
			} else if (tipo.equalsIgnoreCase("xlsx")) {
				bytes = JasperReportUtil.exportReportToXlsxV2(reporte,connection,parametros);
			}
		} catch (SQLException e) {
			logger.error("### error al generar reporte en kardex <- ", e);
		}

		return new ResponseEntity<>(bytes, HttpStatus.OK);
	}



}
