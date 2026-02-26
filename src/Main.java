import principal.Ejecutar;

public class Main {

    public static void main(String[] args) {

        Ejecutar ejecutar = new Ejecutar();

        // Metodo 1 
        String[] palabras = {
            "Sol","Luna","Estrella","Planeta","Cometa",
            "Asteroide","Galaxia","Universo","Nebulosa","Constelacion",
            "Astronomia","Telescopio","Orbitas","Gravedad",
            "Rotacion","Translacion","Materia","Energia",
            "Ciencia","Fisica"
        };

        // Metodo 2
        String[][] datosMetodo2 = new String[20][30];

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 30; j++) {
                datosMetodo2[i][j] = "Dato_" + ((30 - j) + i);
            }
        }

        ejecutar.ejecutarMetodo1(palabras);
        ejecutar.ejecutarMetodo2(datosMetodo2);
    }
}