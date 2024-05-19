package io.github.daviddev16.core;

public class Utilitarios {

    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String DATE_FORMAT_TIME = "dd-MM-yyyy HH:mm:ss";

    public static boolean isNullOrBlank(String texto) {
        return texto == null || texto.isBlank();
    }

}
