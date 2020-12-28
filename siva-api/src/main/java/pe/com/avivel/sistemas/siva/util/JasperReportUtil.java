package pe.com.avivel.sistemas.siva.util;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import pe.com.avivel.sistemas.siva.models.exception.ReportException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class JasperReportUtil {

    private static final Logger LOG = LoggerFactory.getLogger(JasperReportUtil.class);

    public static String exportReportPdf(String url, Connection connection,Map<String, Object> parameters) throws FileNotFoundException, JRException {
        String path = "C:\\Users\\leonardo.davila\\Downloads";

        //load file and compile it
        File file = ResourceUtils.getFile("classpath:" + url);
        System.out.print(file);
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

        JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\reporte.pdf");

        return "report generated in path : " + path;
    }


    public static byte[] exportReportToPdfV2(String url, Connection connection ,Map<String, Object> parameters) {
        try {
            if (parameters == null) parameters = new HashMap<>();

            File file = ResourceUtils.getFile("classpath:" + url);
            System.out.print(file);
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

            JasperReportsContext jasperReportsContext = DefaultJasperReportsContext.getInstance();

            JRPropertiesUtil jrPropertiesUtil = JRPropertiesUtil.getInstance(jasperReportsContext);
            jrPropertiesUtil.setProperty("net.sf.jasperreports.query.executer.factory.plsql", "net.sf.jasperreports.engine.query.PlSqlQueryExecuterFactory");

            JasperPrint jasperPrint = null;

            if (connection != null && !connection.isClosed()) {
                jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
            }
            return JasperExportManager.exportReportToPdf(jasperPrint);


        } catch (Exception e) {
            LOG.error("### Error en exportReportToPdf: {0}", e);
            throw new ReportException(e);
        }
    }


    public static byte[]  exportReportToXlsxV2(String url, Connection connection,Map<String, Object> parameters) throws FileNotFoundException, JRException {

        File file = ResourceUtils.getFile("classpath:"+ url);
        System.out.print(file);
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
            try {

                SimpleXlsxReportConfiguration xlsxReportConfig = new SimpleXlsxReportConfiguration();
                xlsxReportConfig.setRemoveEmptySpaceBetweenColumns(true);
                xlsxReportConfig.setForcePageBreaks(false);
                xlsxReportConfig.setWrapText(false);
                xlsxReportConfig.setCollapseRowSpan(true);
                xlsxReportConfig.setDetectCellType(true);
                xlsxReportConfig.setIgnorePageMargins(true);

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                JRXlsxExporter xlsxExporter = new JRXlsxExporter();
                xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
                xlsxExporter.setConfiguration(xlsxReportConfig);
                xlsxExporter.exportReport();

                byte[] rawBytes = outputStream.toByteArray();

                outputStream.close();
                outputStream.flush();

                return rawBytes;
            } catch (Exception e) {
                LOG.error("### Error en exportReportToXls: {0}", e);
                throw new ReportException(e);
            }

    }

    public static byte[] exportReportToPdf(Connection connection, String urlFile, Map<String, Object> parameters) {
        try {
            if (parameters == null) parameters = new HashMap<>();

            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(urlFile);

            JasperReportsContext jasperReportsContext = DefaultJasperReportsContext.getInstance();

            JRPropertiesUtil jrPropertiesUtil = JRPropertiesUtil.getInstance(jasperReportsContext);
            jrPropertiesUtil.setProperty("net.sf.jasperreports.query.executer.factory.plsql", "net.sf.jasperreports.engine.query.PlSqlQueryExecuterFactory");

            JasperPrint jasperPrint = null;

            if (connection != null && !connection.isClosed()) {
                jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
            }

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            LOG.error("### Error en exportReportToPdf: {0}", e);
            throw new ReportException(e);
        }
    }

    public static byte[] exportReportToXlsx(Connection connection, String urlFile, Map<String, Object> parameters) {
        try {
            if (parameters == null) parameters = new HashMap<>();

            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(urlFile);
            JasperReportsContext jasperReportsContext = DefaultJasperReportsContext.getInstance();

            JRPropertiesUtil jrPropertiesUtil = JRPropertiesUtil.getInstance(jasperReportsContext);
            jrPropertiesUtil.setProperty("net.sf.jasperreports.query.executer.factory.plsql", "net.sf.jasperreports.engine.query.PlSqlQueryExecuterFactory");

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            SimpleXlsxReportConfiguration xlsxReportConfig = new SimpleXlsxReportConfiguration();
            xlsxReportConfig.setRemoveEmptySpaceBetweenColumns(true);
            xlsxReportConfig.setForcePageBreaks(false);
            xlsxReportConfig.setWrapText(false);
            xlsxReportConfig.setCollapseRowSpan(true);
            xlsxReportConfig.setDetectCellType(true);
            xlsxReportConfig.setIgnorePageMargins(true);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            JRXlsxExporter xlsxExporter = new JRXlsxExporter();
            xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            xlsxExporter.setConfiguration(xlsxReportConfig);
            xlsxExporter.exportReport();

            byte[] rawBytes = outputStream.toByteArray();

            outputStream.close();
            outputStream.flush();

            return rawBytes;
        } catch (Exception e) {
            LOG.error("### Error en exportReportToXls: {0}", e);
            throw new ReportException(e);
        }
    }

}
