package pe.com.avivel.sistemas.siva.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Util {

    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    public static Date agregarMinutos(Date fecha, int minutos) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.MINUTE, minutos);
        return calendar.getTime();
    }

    public static Date agregarDias(Date fecha, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DATE, dias);
        return calendar.getTime();
    }

    public static String randomUUID() {
        return (UUID.randomUUID().toString().replaceAll("-", ""));
    }

    public static String leerFichero(String pathFile) {
        StringBuilder buffer = new StringBuilder();
        String line = org.apache.commons.lang3.StringUtils.EMPTY;
        FileReader fReader = null;
        BufferedReader bReader;
        try {
            fReader = new FileReader(pathFile);
            bReader = new BufferedReader(fReader);
            while ((line = bReader.readLine()) != null) {
                buffer.append(line);
            }
            bReader.close();
            fReader.close();
            return buffer.toString();
        } catch (IOException e) {
            logger.error("### error en leerFichero: ", e);
        } finally {
            try {
                if (null != fReader) {
                    fReader.close();
                }
            } catch (Exception e2) {
                logger.error("### error en leerFichero: ", e2);
                return null;
            }
        }

        return null;
    }

    public static String getMesNombre(Integer mes) {
        String ret = "";
        switch (mes) {
            case 1:
                ret = "ENERO";
                break;
            case 2:
                ret = "FEBRERO";
                break;
            case 3:
                ret = "MARZO";
                break;
            case 4:
                ret = "ABRIL";
                break;
            case 5:
                ret = "MAYO";
                break;
            case 6:
                ret = "JUNIO";
                break;
            case 7:
                ret = "JULIO";
                break;
            case 8:
                ret = "AGOSTO";
                break;
            case 9:
                ret = "SEPTIEMBRE";
                break;
            case 10:
                ret = "OCTUBRE";
                break;
            case 11:
                ret = "NOVIEMBRE";
                break;
            case 12:
                ret = "DICIEMBRE";
                break;

        }
        return ret;
    }
}
