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
import pe.com.avivel.sistemas.siva.models.dto.FiltroSolicitudDTO;
import pe.com.avivel.sistemas.siva.models.dto.SolicitudItemQueryDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Movimiento;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.SolicitudItem;
import pe.com.avivel.sistemas.siva.models.services.spec.ISolicitudItemService;
import pe.com.avivel.sistemas.siva.util.ConverterUtil;
import pe.com.avivel.sistemas.siva.util.JasperReportUtil;

import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class SolicitudItemRestController {

    private static final Logger logger = LoggerFactory.getLogger(SolicitudRestController.class);
    private final ServletContext servletContext;
    private final DataSource dataSource;
    private final ISolicitudItemService solicitudItemService;

    @Autowired
    public SolicitudItemRestController(ServletContext servletContext, DataSource dataSource, ISolicitudItemService solicitudItemService) {
        this.servletContext = servletContext;
        this.dataSource = dataSource;
        this.solicitudItemService =solicitudItemService;
    }

    @Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
    @GetMapping("/solicitudesItem/listar")
    public List<SolicitudItem> index() { return solicitudItemService.findAll();
    }
    @Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
    @GetMapping("/solicitudesItem/v2/listar")
    public List<SolicitudItemQueryDTO> findAllDTO() {
        return solicitudItemService.findAllDTO();
    }

    @Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
    @GetMapping("/solicitudesItem/{id}")
    public ResponseEntity<?> show(@PathVariable Integer id) {

        SolicitudItem solicitudItem = null;
        Map<String, Object> response = new HashMap<>();

        try {
            solicitudItem = solicitudItemService.findById(id);
        } catch(DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(solicitudItem == null) {
            response.put("mensaje", "La solicitud ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<SolicitudItem>(solicitudItem, HttpStatus.OK);
    }

    @GetMapping("/solicitudItem/filtro")
    public ResponseEntity<List<SolicitudItem>> findAllByFiltroSi(@RequestParam("codsoli") Integer codigoSolicitud,
                                                                 @RequestParam("fechaDesde") Long fechaDesde,
                                                                 @RequestParam("fechaHasta") Long fechaHasta) {

        FiltroSolicitudDTO filtroVacunacionDTO = new FiltroSolicitudDTO();

        filtroVacunacionDTO.setCodigoSolicitud(codigoSolicitud);
        filtroVacunacionDTO.setFechaDesde(ConverterUtil.toDate(fechaDesde));
        filtroVacunacionDTO.setFechaHasta(ConverterUtil.toDate(fechaHasta));

        return new ResponseEntity<>(solicitudItemService.findAllByFiltroSi(filtroVacunacionDTO), HttpStatus.OK);
    }

    @GetMapping("/solicitudItem/v2/filtro")
    public ResponseEntity<List<SolicitudItemQueryDTO>> findAllByFiltroSiDTO(@RequestParam("codsoli") Integer codigoSolicitud,
                                                                 @RequestParam("fechaDesde") Long fechaDesde,
                                                                 @RequestParam("fechaHasta") Long fechaHasta) {

        FiltroSolicitudDTO filtroVacunacionDTO = new FiltroSolicitudDTO();

        filtroVacunacionDTO.setCodigoSolicitud(codigoSolicitud);
        filtroVacunacionDTO.setFechaDesde(ConverterUtil.toDate(fechaDesde));
        filtroVacunacionDTO.setFechaHasta(ConverterUtil.toDate(fechaHasta));

        return new ResponseEntity<>(solicitudItemService.findAllByFiltroSiDTO(filtroVacunacionDTO), HttpStatus.OK);
    }


    @GetMapping("/solicitudItem/codigo")
    public ResponseEntity<List<SolicitudItem>> findAllByCodigoSi(@RequestParam("codsoli") Integer codigoSolicitud) {
        return new ResponseEntity<>(solicitudItemService.findAllByCodigoSi(codigoSolicitud),HttpStatus.OK);
    }

    @GetMapping("/solicitudItem/v2/codigo")
    public ResponseEntity<List<SolicitudItemQueryDTO>> findAllByCodigoSiDTO(@RequestParam("codsoli") Integer codigoSolicitud) {
        return new ResponseEntity<>(solicitudItemService.findAllByCodigoSiDTO(codigoSolicitud),HttpStatus.OK);
    }


    @Secured({"ROLE_ADMIN", "ROLE_MODIFICAR"})
    @DeleteMapping("/solicitudItem/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {

        Map<String, Object> response = new HashMap<>();
        try {
            SolicitudItem solicitudItem = solicitudItemService.findById(id);
            solicitudItemService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el movimiento de insumo de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El item pedido eliminado con éxito!");

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    //REPORTES

    @GetMapping("/solicitudItem/report")
    public ResponseEntity<byte[]> generateReportByGranja(@RequestParam("codigo") Integer codigo,
                                                         @RequestParam("fechaDesde") Long fechaDesde,
                                                         @RequestParam("fechaHasta") Long fechaHasta,
                                                         @RequestParam("usuario") String usuario,
                                                         @RequestParam(value = "tipo", defaultValue = "pdf") String tipo) throws FileNotFoundException, JRException {
        byte[] bytes = null;
        String data = null;
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("codigo", codigo);
        parametros.put("fechaDesde",ConverterUtil.toDate(fechaDesde));
        parametros.put("fechaHasta",ConverterUtil.toDate(fechaHasta));
        parametros.put("usuario",usuario);
        parametros.put("tipo",tipo);

        String reporte = null;

        reporte = "rpt_pedidos.jrxml";

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
