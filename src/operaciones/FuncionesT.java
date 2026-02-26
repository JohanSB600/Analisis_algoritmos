package operaciones;

import java.util.Arrays;

public class FuncionesT {

    // Metodo 1 
    public String metodo1(String cadena) {

        if (cadena == null)
            return cadena;

        String cadena2 = "";

        for (int i = cadena.length() - 1; i >= 0; i--) {
            cadena2 = cadena2 + cadena.charAt(i);
        }
        return cadena2;
    }

    // Metodo 2 
    public String[] metodo2(String[] datos) {
        Arrays.sort(datos);
        return datos;
    }
}