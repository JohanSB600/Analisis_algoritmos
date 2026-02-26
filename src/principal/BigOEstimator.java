package principal;

import java.util.ArrayList;
import java.util.List;

public class BigOEstimator {

    public static class Estimacion {
        public final String notacion;
        public final double error; // menor es mejor

        public Estimacion(String notacion, double error) {
            this.notacion = notacion;
            this.error = error;
        }
    }

    /**
     * Estima la notación Big-O que mejor se ajusta a los tiempos observados.
     * Asume n = índice del ciclo (1..N).
     *
     * Importante:
     * - Esto NO "demuestra" la complejidad teórica.
     * - Solo hace un ajuste a datos experimentales (como tu gráfica).
     */
    public static Estimacion estimar(long[] tiempos) {
        if (tiempos == null || tiempos.length < 3) {
            return new Estimacion("O(?)", Double.POSITIVE_INFINITY);
        }

        // Preparar datos: n = 1..N, y = tiempos (double)
        int N = tiempos.length;
        double[] n = new double[N];
        double[] y = new double[N];

        for (int i = 0; i < N; i++) {
            n[i] = i + 1;
            y[i] = Math.max(1.0, (double) tiempos[i]); // evitar ceros
        }

        // Candidatos típicos
        List<Candidato> candidatos = new ArrayList<>();
        candidatos.add(new Candidato("O(1)", nn -> 1.0));
        candidatos.add(new Candidato("O(log n)", nn -> Math.log(nn)));
        candidatos.add(new Candidato("O(n)", nn -> nn));
        candidatos.add(new Candidato("O(n log n)", nn -> nn * Math.log(nn)));
        candidatos.add(new Candidato("O(n^2)", nn -> nn * nn));
        candidatos.add(new Candidato("O(n^3)", nn -> nn * nn * nn));

        Estimacion mejor = new Estimacion("O(?)", Double.POSITIVE_INFINITY);

        for (Candidato c : candidatos) {
            Estimacion e = ajustarYCalcularError(n, y, c);
            if (e.error < mejor.error) {
                mejor = e;
            }
        }

        return mejor;
    }

    // ===== Internals =====

    private interface Funcion {
        double f(double n);
    }

    private static class Candidato {
        final String nombre;
        final Funcion funcion;

        Candidato(String nombre, Funcion funcion) {
            this.nombre = nombre;
            this.funcion = funcion;
        }
    }

    /**
     * Ajuste de escala: y ≈ a * f(n)
     * Elegimos a que minimiza SSE: a = (Σ y f) / (Σ f^2)
     * Luego calculamos error relativo promedio.
     */
    private static Estimacion ajustarYCalcularError(double[] n, double[] y, Candidato c) {
        int N = n.length;

        double sumYF = 0.0;
        double sumF2 = 0.0;

        double[] fvals = new double[N];
        for (int i = 0; i < N; i++) {
            double fv = c.funcion.f(n[i]);
            // Evitar problemas con log(1)=0 en los primeros puntos
            if (!Double.isFinite(fv) || fv <= 0) fv = 1e-9;
            fvals[i] = fv;

            sumYF += y[i] * fv;
            sumF2 += fv * fv;
        }

        if (sumF2 == 0) {
            return new Estimacion(c.nombre, Double.POSITIVE_INFINITY);
        }

        double a = sumYF / sumF2;

        // Error relativo medio: avg( |y - a f| / y )
        double err = 0.0;
        for (int i = 0; i < N; i++) {
            double pred = a * fvals[i];
            double rel = Math.abs(y[i] - pred) / y[i];
            err += rel;
        }
        err /= N;

        return new Estimacion(c.nombre, err);
    }
}