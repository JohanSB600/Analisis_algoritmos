package principal;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Random;

public class VentanaResultados {

    public void mostrar() {
        SwingUtilities.invokeLater(() -> buildAndShow());
    }

    private void buildAndShow() {
        Ejecutar ejecutar = new Ejecutar();

        // Generar datos aleatorios
        String[] palabras = generarPalabrasAleatorias(20, 3, 12);
        String[][] datosMetodo2 = generarMatrizAleatoria(20, 30, 4, 10);

        // Medir sin imprimir (los métodos devolverán los resultados)
        Ejecutar.Resultado r1 = ejecutar.medirMetodo1(palabras);
        Ejecutar.Resultado r2 = ejecutar.medirMetodo2(datosMetodo2);

        // Generar graficas
        Graficador.graficar(r1.tiempos, "Metodo 1 - Tiempos", "metodo1.png");
        Graficador.graficar(r2.tiempos, "Metodo 2 - Tiempos", "metodo2.png");

        // Abrir una ventana independiente para cada método
        showMethodWindow("Metodo 1 - Resultados", r1, "metodo1.png");
        showMethodWindow("Metodo 2 - Resultados", r2, "metodo2.png");
    }

    private void showMethodWindow(String title, Ejecutar.Resultado r, String imagePath) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().add(crearPanelResultados(r, imagePath), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel crearPanelResultados(Ejecutar.Resultado r, String imagePath) {
        JPanel panel = new JPanel(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setEditable(false);
        StringBuilder sb = new StringBuilder();
        sb.append("Ciclo ; Valor ; Tiempo(ns)\n");
        for (int i = 0; i < r.tiempos.length; i++) {
            String val = (r.outputs != null && i < r.outputs.length) ? r.outputs[i] : "";
            sb.append((i+1)).append(" ; ").append(val).append(" ; ").append(r.tiempos[i]).append("\n");
        }
        area.setText(sb.toString());
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(420, 600));

        JPanel right = new JPanel(new BorderLayout());
        File f = new File(imagePath);
        if (f.exists()) {
            ImageIcon icon = new ImageIcon(imagePath);
            JLabel lbl = new JLabel(icon);
            lbl.setHorizontalAlignment(SwingConstants.CENTER);
            right.add(new JScrollPane(lbl), BorderLayout.CENTER);
        } else {
            right.add(new JLabel("Imagen no encontrada: " + imagePath), BorderLayout.CENTER);
        }

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scroll, right);
        split.setDividerLocation(420);
        panel.add(split, BorderLayout.CENTER);
        return panel;
    }

    private String[] generarPalabrasAleatorias(int n, int minLen, int maxLen) {
        String[] res = new String[n];
        Random rnd = new Random();
        String letters = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < n; i++) {
            int len = rnd.nextInt(maxLen - minLen + 1) + minLen;
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < len; j++) {
                sb.append(letters.charAt(rnd.nextInt(letters.length())));
            }
            res[i] = sb.toString();
        }
        return res;
    }

    private String[][] generarMatrizAleatoria(int rows, int cols, int minLen, int maxLen) {
        String[][] m = new String[rows][cols];
        Random rnd = new Random();
        String letters = "abcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int len = rnd.nextInt(maxLen - minLen + 1) + minLen;
                StringBuilder sb = new StringBuilder();
                for (int k = 0; k < len; k++) {
                    sb.append(letters.charAt(rnd.nextInt(letters.length())));
                }
                m[i][j] = sb.toString();
            }
        }
        return m;
    }
}
