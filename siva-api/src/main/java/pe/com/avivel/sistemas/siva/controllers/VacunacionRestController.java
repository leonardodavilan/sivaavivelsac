package pe.com.avivel.sistemas.siva.controllers;

import net.sf.jasperreports.engine.JRException;
import org.apache.commons.text.StringSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.com.avivel.sistemas.siva.models.dto.FiltroVacunacionDTO;
import pe.com.avivel.sistemas.siva.models.dto.UsuarioQueryDTO;
import pe.com.avivel.sistemas.siva.models.dto.VacunacionDTO;
import pe.com.avivel.sistemas.siva.models.dto.VacunacionQueryDTO;
import pe.com.avivel.sistemas.siva.models.entity.produccion.PrdLote;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.*;
import pe.com.avivel.sistemas.siva.models.services.spec.IPrdLoteService;
import pe.com.avivel.sistemas.siva.models.services.spec.IUsuarioService;
import pe.com.avivel.sistemas.siva.models.services.spec.IVacunacionService;
import pe.com.avivel.sistemas.siva.util.ConverterUtil;
import pe.com.avivel.sistemas.siva.util.EmailUtil;
import pe.com.avivel.sistemas.siva.util.JasperReportUtil;
import pe.com.avivel.sistemas.siva.util.Util;

import javax.servlet.ServletContext;
import javax.sql.DataSource;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200","*" })
@RestController
@RequestMapping("/api")
public class VacunacionRestController {

	private static final Logger logger = LoggerFactory.getLogger(VacunacionRestController.class);
	private final ServletContext servletContext;
	private final DataSource dataSource;
	private final IVacunacionService vacunacionService;
	private final IPrdLoteService prdLoteService;
	private final EmailUtil emailUtil;
	private final IUsuarioService usuarioService;

	@Autowired
	public VacunacionRestController(ServletContext servletContext, DataSource dataSource,IVacunacionService vacunacionService, EmailUtil emailUtil,IPrdLoteService prdLoteService, IUsuarioService usuarioService) {
		this.servletContext = servletContext;
		this.dataSource = dataSource;
		this.vacunacionService =vacunacionService;
		this.emailUtil = emailUtil;
		this.prdLoteService = prdLoteService;
		this.usuarioService = usuarioService;
	}

	// private final Logger log = LoggerFactory.getLogger(ClienteRestController.class);

	@GetMapping("/vacunaciones/v1/listar")
	public List<Vacunacion> index() { return vacunacionService.findAll();
	}
	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/vacunaciones/{id}")
	public ResponseEntity<?> show(@PathVariable Integer id) {

		Vacunacion vacunacion = null;
		Map<String, Object> response = new HashMap<>();

		try {
			vacunacion = vacunacionService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(vacunacion == null) {
			response.put("mensaje", "La pe.com.avivel.sistemas.siva.models.entity.vacunacion ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Vacunacion>(vacunacion, HttpStatus.OK);
	}

	@GetMapping("/vacunaciones")
	public ResponseEntity<List<VacunacionQueryDTO>> listar(@RequestParam("prdLoteId") Integer prdEtapaId,
														   @RequestParam("fechaDesde") Long fechaDesde,
														   @RequestParam("fechaHasta") Long fechaHasta,
														   @RequestParam("prdEtapa") String prdEtapa) {

		FiltroVacunacionDTO filtroVacunacionDTO = new FiltroVacunacionDTO();
		filtroVacunacionDTO.setFechaDesde(ConverterUtil.toDate(fechaDesde));
		filtroVacunacionDTO.setFechaHasta(ConverterUtil.toDate(fechaHasta));
		filtroVacunacionDTO.setPrdEtapa(prdEtapa);
		filtroVacunacionDTO.setPrdLoteId(prdEtapaId);

		return new ResponseEntity<>(vacunacionService.findAllByFiltro(filtroVacunacionDTO),HttpStatus.OK);
	}



	@PostMapping("/vacunacion/add")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> saveVacunacion(@Valid @RequestBody VacunacionDTO vacunacionDTO, BindingResult result) {

		Vacunacion newVacunacion;
		PrdLote lote;

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
			newVacunacion =  vacunacionService.save(vacunacionDTO);
			lote = prdLoteService.findById(vacunacionDTO.getLoteId());
			vacunacionDTO.setLoteCodigo(lote.getCodigo());

			List<UsuarioQueryDTO> usuariosQueryDTO = usuarioService.findByUsuariosByGrupo("APLICACION_VACUNA");

			String[] emails = new String[usuariosQueryDTO.size()];
			for (int i = 0; i < usuariosQueryDTO.size(); i++) {
				emails[i] = usuariosQueryDTO.get(i).getEmail().toLowerCase();
			}

			//String emails = "leonardo.davila@avivel.com.pe";
			new Thread(() -> this.notificarVacunacion(vacunacionDTO, emails)).start();

		}catch (DataAccessException e){
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El registro de vacunación ha sido creado con éxito!");
		response.put("VacunacionId", newVacunacion);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}


	private void notificarVacunacion(VacunacionDTO vacunacionDTO, String[] emails) {
		Map<String, Object> map = new HashMap<>();
		map.put("loteId", vacunacionDTO.getLoteCodigo());
		map.put("fecha",  ConverterUtil.toDate(vacunacionDTO.getFecha()));
		map.put("url","http://localhost:8080/#/vacunaciones/registros");

		String alias = "SIVA Sanidad";
		String desde = "notificaciones@avivel.com.pe";
		String asunto = "Nueva vacuna registrada";

		String pathTemplate = "C:\\Users\\Soporte\\Downloads\\backend\\siva-backend\\src\\main\\resources\\templates\\registroVacunacion.html";
		String template = Util.leerFichero(pathTemplate);
		String html;

		if (StringUtils.isEmpty(template)) {
			html = "No se encontro el formato de email";
			html += "<br>";
		} else {
			html = StringSubstitutor.replace(template, map);
		}

		emailUtil.sendMail(alias, desde, emails, asunto, html);
	}


	@Secured("ROLE_ADMIN")
	@PutMapping("/vacunacion/update/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Vacunacion vacunacion, BindingResult result, @PathVariable Integer id) {

		Vacunacion vacunacionActual = vacunacionService.findById(id);

		Vacunacion vacunacionUpdated = null;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (vacunacionActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el registro de vacunación con ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			vacunacionActual.setCodigo(vacunacion.getCodigo());
			vacunacionActual.setFecha(vacunacion.getFecha());
			vacunacionActual.setObservacion(vacunacion.getObservacion());
			vacunacionActual.setProgramacionVacuna(vacunacion.getProgramacionVacuna());
			vacunacionActual.setLote(vacunacion.getLote());
			vacunacionActual.setProveedor(vacunacion.getProveedor());
			vacunacionActual.setMetodoVacuna(vacunacion.getMetodoVacuna());
			vacunacionActual.setEmpleado(vacunacion.getEmpleado());
			vacunacionActual.setLoteSerie(vacunacion.getLoteSerie());
			vacunacionActual.setEstado(vacunacion.getEstado());

			vacunacionUpdated = vacunacionService.saveUpdate(vacunacionActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el registro de vacunación en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El registro de vacunación ha sido actualizado con éxito!");
		response.put("Insumo", vacunacionUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}


	@Secured("ROLE_ADMIN")
	@DeleteMapping("/vacunacion/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {

		Map<String, Object> response = new HashMap<>();

		try {
			Vacunacion vacunacion = vacunacionService.findById(id);
			vacunacionService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el registro de vacunación de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El registro de vacunación eliminado con éxito!");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}


	//REPORTES

}