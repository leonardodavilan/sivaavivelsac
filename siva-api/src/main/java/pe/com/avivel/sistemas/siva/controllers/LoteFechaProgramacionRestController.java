package pe.com.avivel.sistemas.siva.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pe.com.avivel.sistemas.siva.models.dto.FiltroVacunacionDTO;
import pe.com.avivel.sistemas.siva.models.entity.produccion.LoteFechaProgramacion;
import pe.com.avivel.sistemas.siva.models.services.spec.ILoteFechaProgramacionService;
import pe.com.avivel.sistemas.siva.util.ConverterUtil;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class LoteFechaProgramacionRestController {
	
	private final ILoteFechaProgramacionService loteFechaProgramacionService;


	@Autowired
	public LoteFechaProgramacionRestController(ILoteFechaProgramacionService loteFechaProgramacionService) {
		this.loteFechaProgramacionService =loteFechaProgramacionService;
	}

	// private final Logger log = LoggerFactory.getLogger(ClienteRestController.class);

	@GetMapping("/lotesfechasprogramadas/v1/listar")
	public List<LoteFechaProgramacion> index() { return loteFechaProgramacionService.findAll();
	}
	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/lotesfechasprogramadas/{id}")
	public ResponseEntity<?> show(@PathVariable BigDecimal id) {

		LoteFechaProgramacion loteFechaProgramacion = null;
		Map<String, Object> response = new HashMap<>();

		try {
			loteFechaProgramacion = loteFechaProgramacionService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(loteFechaProgramacion == null) {
			response.put("mensaje", "La pe.com.avivel.sistemas.siva.models.entity.vacunacion ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<LoteFechaProgramacion>(loteFechaProgramacion, HttpStatus.OK);
	}


	@GetMapping("/lotesfechasprogramadas-by-programacion")
	public ResponseEntity<?> listar(@RequestParam("prdLoteId") Integer prdEtapaId,
									@RequestParam("numeroProgramacionId") Integer numeroProgramacionId) {

		FiltroVacunacionDTO filtroVacunacionDTO = new FiltroVacunacionDTO();
		filtroVacunacionDTO.setNumeroProgramacionId(numeroProgramacionId);
		filtroVacunacionDTO.setPrdLoteId(prdEtapaId);

		return new ResponseEntity<>(loteFechaProgramacionService.findAllByFiltroLote(filtroVacunacionDTO),HttpStatus.OK);
	}




	@GetMapping("/lotesfechasprogramadas/programacion")
	public ResponseEntity<List<LoteFechaProgramacion>> findAllByFullFiltroLote(@RequestParam("prdLoteId") Integer prdLoteId,
																			   @RequestParam("fechaDesde") Long fechaDesde,
																			   @RequestParam("fechaHasta") Long fechaHasta,
																			   @RequestParam("numeroProgramacionId") Integer numeroProgramacionId) {

		FiltroVacunacionDTO filtroVacunacionDTO = new FiltroVacunacionDTO();
		filtroVacunacionDTO.setNumeroProgramacionId(numeroProgramacionId);
		filtroVacunacionDTO.setPrdLoteId(prdLoteId);
		filtroVacunacionDTO.setFechaDesde(ConverterUtil.toDate(fechaDesde));
		filtroVacunacionDTO.setFechaHasta(ConverterUtil.toDate(fechaHasta));

		return new ResponseEntity<List<LoteFechaProgramacion>>(loteFechaProgramacionService.findAllByFullFiltroLote(filtroVacunacionDTO),HttpStatus.OK);
	}




	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/lotesfechas-by-programacion/{prdLoteId}")
	public ResponseEntity<?> findAllByFiltroFechaProgramada(@PathVariable String prdLoteId) {

		List<LoteFechaProgramacion> loteFechaProgramacions = null;
		Map<String, Object> response = new HashMap<>();

		try {
			loteFechaProgramacions = loteFechaProgramacionService.findAllByFiltroFechaProgramada(prdLoteId);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(loteFechaProgramacions == null) {
			response.put("mensaje", "El movimiento de insumo ID: ".concat(prdLoteId.concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<LoteFechaProgramacion>>(loteFechaProgramacions, HttpStatus.OK);
	}




}