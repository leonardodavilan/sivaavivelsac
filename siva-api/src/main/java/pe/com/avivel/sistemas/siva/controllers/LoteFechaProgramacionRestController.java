package pe.com.avivel.sistemas.siva.controllers;

import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import pe.com.avivel.sistemas.siva.util.JasperReportUtil;


import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class LoteFechaProgramacionRestController {
	
	private final ILoteFechaProgramacionService loteFechaProgramacionService;
	private final DataSource dataSource;
	private static final Logger logger = LoggerFactory.getLogger(VacunacionRestController.class);


	@Autowired
	public LoteFechaProgramacionRestController(ILoteFechaProgramacionService loteFechaProgramacionService, DataSource dataSource) {
		this.loteFechaProgramacionService =loteFechaProgramacionService;
		this.dataSource = dataSource;
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


	@GetMapping("/fechasprogramadasvacunasbylote")
	public ResponseEntity<?> listar(@RequestParam("prdLoteId") Integer prdLoteId,
									@RequestParam("prdEtapa") String prdEtapa,
									@RequestParam("numeroProgramacionId") Integer numeroProgramacionId) {

		FiltroVacunacionDTO filtroVacunacionDTO = new FiltroVacunacionDTO();
		filtroVacunacionDTO.setNumeroProgramacionId(numeroProgramacionId);
		filtroVacunacionDTO.setPrdEtapa(prdEtapa);
		filtroVacunacionDTO.setPrdLoteId(prdLoteId);

		return new ResponseEntity<>(loteFechaProgramacionService.findAllByFiltroLote(filtroVacunacionDTO),HttpStatus.OK);
	}

	@GetMapping("/fechasvacunasbylote")
	public ResponseEntity<?> listarOnlyFechaVac(@RequestParam("prdLoteId") Integer prdLoteId,
												@RequestParam("prdEtapa") String prdEtapa,
												@RequestParam("numeroProgramacionId") Integer numeroProgramacionId) {

		FiltroVacunacionDTO filtroVacunacionDTO = new FiltroVacunacionDTO();
		filtroVacunacionDTO.setNumeroProgramacionId(numeroProgramacionId);
		filtroVacunacionDTO.setPrdEtapa(prdEtapa);
		filtroVacunacionDTO.setPrdLoteId(prdLoteId);

		return new ResponseEntity<>(loteFechaProgramacionService.findAllByFiltroLoteOnlyFechVac(filtroVacunacionDTO),HttpStatus.OK);
	}





	@GetMapping("/fechasvacunasbyfiltrofechavac")
	public ResponseEntity<List<LoteFechaProgramacion>> findAllByFullFiltroFechaVac(@RequestParam("prdLoteId") Integer prdLoteId,
																				   @RequestParam("fechaDesde") Long fechaDesde,
																				   @RequestParam("fechaHasta") Long fechaHasta,
																				   @RequestParam("prdEtapa") String prdEtapa,
																				   @RequestParam("numeroProgramacionId") Integer numeroProgramacionId) {

		FiltroVacunacionDTO filtroVacunacionDTO = new FiltroVacunacionDTO();
		filtroVacunacionDTO.setNumeroProgramacionId(numeroProgramacionId);
		filtroVacunacionDTO.setPrdLoteId(prdLoteId);
		filtroVacunacionDTO.setPrdEtapa(prdEtapa);
		filtroVacunacionDTO.setFechaDesde(ConverterUtil.toDate(fechaDesde));
		filtroVacunacionDTO.setFechaHasta(ConverterUtil.toDate(fechaHasta));

		return new ResponseEntity<List<LoteFechaProgramacion>>(loteFechaProgramacionService.findAllByFullFiltroFechaVac(filtroVacunacionDTO),HttpStatus.OK);
	}

	@GetMapping("/fechasvacunasbyfiltrofechaprog")
	public ResponseEntity<List<LoteFechaProgramacion>> findAllByFullFiltroFechaProg(@RequestParam("prdLoteId") Integer prdLoteId,
																				   	@RequestParam("fechaDesde") Long fechaDesde,
																				   	@RequestParam("fechaHasta") Long fechaHasta,
																				   	@RequestParam("prdEtapa") String prdEtapa,
																					@RequestParam("numeroProgramacionId") Integer numeroProgramacionId) {

		FiltroVacunacionDTO filtroVacunacionDTO = new FiltroVacunacionDTO();
		filtroVacunacionDTO.setNumeroProgramacionId(numeroProgramacionId);
		filtroVacunacionDTO.setPrdLoteId(prdLoteId);
		filtroVacunacionDTO.setPrdEtapa(prdEtapa);
		filtroVacunacionDTO.setFechaDesde(ConverterUtil.toDate(fechaDesde));
		filtroVacunacionDTO.setFechaHasta(ConverterUtil.toDate(fechaHasta));

		return new ResponseEntity<List<LoteFechaProgramacion>>(loteFechaProgramacionService.findAllByFullFiltroFechaProg(filtroVacunacionDTO),HttpStatus.OK);
	}

	@GetMapping("/fechasvacunas/reporte")
	public ResponseEntity<byte[]> generateReport(@RequestParam("prdLoteId") Integer prdLoteId,
												 @RequestParam("fechaDesde") Long fechaDesde,
												 @RequestParam("fechaHasta") Long fechaHasta,
												 @RequestParam("prdEtapa") String prdEtapa,
												 @RequestParam("rbFecha") String rbFecha,
												 @RequestParam("numeroProgramacionId") Integer numeroProgramacionId,
												 @RequestParam(value = "tipo", defaultValue = "pdf") String tipo)throws FileNotFoundException, JRException {
		byte[] bytes = null;
		String data = null;
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("fechaDesde", ConverterUtil.toDate(fechaDesde));
		parametros.put("fechaHasta", ConverterUtil.toDate(fechaHasta));
		parametros.put("prdLoteId", prdLoteId);
		parametros.put("prdEtapa", prdEtapa);
		parametros.put("rbFecha", rbFecha);
		parametros.put("numeroProgramacionId", numeroProgramacionId);


		String reporte = null;

		reporte = "rpt_vacuna_programada.jrxml";

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

	@GetMapping("/fechasvacunas/reportebygranja")
	public ResponseEntity<byte[]> generateReportByGranja(@RequestParam("prdGranjaId") Integer prdGranjaId,
														 @RequestParam("fechaDesde") Long fechaDesde,
														 @RequestParam("fechaHasta") Long fechaHasta,
														 @RequestParam("prdGranjaNombre") String prdGranjaNombre,
														 @RequestParam(value = "tipo", defaultValue = "pdf") String tipo)throws FileNotFoundException, JRException{
		byte[] bytes = null;
		String data = null;
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("fechaDesde", ConverterUtil.toDate(fechaDesde));
		parametros.put("fechaHasta", ConverterUtil.toDate(fechaHasta));
		parametros.put("prdGranjaId", prdGranjaId);
		parametros.put("prdGranjaNombre", prdGranjaNombre);

		String reporte = null;

		reporte = "rpt_vacuna_granja.jrxml";

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