package principal;

import operaciones.FuncionesT;

public class Ejecutar {

    FuncionesT funcionesT = new FuncionesT();

    public static class Resultado {
        public long[] tiempos;
        public String[] outputs;

        public Resultado(long[] tiempos, String[] outputs) {
            this.tiempos = tiempos;
            this.outputs = outputs;
        }
    }

    // Metodo 1
    public void ejecutarMetodo1(String[] cadenas) {
        Resultado r = medirMetodo1(cadenas);

        System.out.println("\n METODO 1");
        for (int i = 0; i < r.outputs.length; i++) {
            System.out.println("Ciclo " + (i+1) + " -> " + r.outputs[i]);
        }

        System.out.println("\nTIEMPOS METODO 1");
        System.out.println("Ciclo ; Tiempo(ns)");
        for (int i = 0; i < r.tiempos.length; i++) {
            System.out.println((i+1) + " ; " + r.tiempos[i]);
        }

        Graficador.graficar(r.tiempos, "Metodo 1 - Tiempos", "metodo1.png");
    }

    // METODO 2
    public void ejecutarMetodo2(String[][] datos) {
        Resultado r = medirMetodo2(datos);

        System.out.println("\n METODO 2");
        for (int i = 0; i < r.outputs.length; i++) {
            System.out.println("Ciclo " + (i+1) + " -> " + r.outputs[i]);
        }

        System.out.println("\nTIEMPOS METODO 2");
        System.out.println("Ciclo ; Tiempo(ns)");
        for (int i = 0; i < r.tiempos.length; i++) {
            System.out.println((i+1) + " ; " + r.tiempos[i]);
        }

        Graficador.graficar(r.tiempos, "Metodo 2 - Tiempos", "metodo2.png");
    }

    public Resultado medirMetodo1(String[] cadenas) {
        if (cadenas == null) return new Resultado(new long[0], new String[0]);
        long[] tiempos = new long[cadenas.length];
        String[] outputs = new String[cadenas.length];

        for (int i = 0; i < cadenas.length; i++) {
            long inicio = System.nanoTime();
            String invertida = funcionesT.metodo1(cadenas[i]);
            long fin = System.nanoTime();
            tiempos[i] = fin - inicio;
            outputs[i] = invertida;
        }
        return new Resultado(tiempos, outputs);
    }

    public Resultado medirMetodo2(String[][] datos) {
        if (datos == null) return new Resultado(new long[0], new String[0]);
        long[] tiempos = new long[datos.length];
        String[] outputs = new String[datos.length];

        for (int i = 0; i < datos.length; i++) {
            String[] copia = datos[i].clone();
            long inicio = System.nanoTime();
            String[] ordenado = funcionesT.metodo2(copia);
            long fin = System.nanoTime();
            tiempos[i] = fin - inicio;
            outputs[i] = "Primer elemento: " + (ordenado.length > 0 ? ordenado[0] : "");
        }
        return new Resultado(tiempos, outputs);
    }
}