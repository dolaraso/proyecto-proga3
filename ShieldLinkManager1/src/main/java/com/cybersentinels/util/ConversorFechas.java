package com.cybersentinels.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConversorFechas {
    private static final String FORMATO_FECHA = "yyyy-MM-dd";

    public static String convertirFecha(Date fecha) {
        SimpleDateFormat formato = new SimpleDateFormat(FORMATO_FECHA);
        return formato.format(fecha);
    }

    public static Date convertirStringADate(String fecha) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat(FORMATO_FECHA);
        return formato.parse(fecha);
    }

    public static boolean esFechaValida(String fecha) {
        try {
            convertirStringADate(fecha);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
