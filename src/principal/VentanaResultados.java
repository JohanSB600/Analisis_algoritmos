package principal;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Random;

public class VentanaResultados {

    public void mostrar() {
        SwingUtilities.invokeLater(this::buildAndShow);
    }

    private void buildAndShow() {
        Ejecutar ejecutar = new Ejecutar();

        // ===== Metodo 1 (ALEATORIO): palabras aleatorias para invertir =====
        String[] palabras = generarPalabrasAleatorias(20, 3, 12);

        // ===== Metodo 2 (IGUAL QUE ANTES): NO CAMBIAR FUNCIONALIDAD =====
        String[][] datosMetodo2 = new String[20][30];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 30; j++) {
                datosMetodo2[i][j] = "Dato_" + ((30 - j) + i);
            }
        }

        // Medir sin imprimir
        Ejecutar.Resultado r1 = ejecutar.medirMetodo1(palabras);
        Ejecutar.Resultado r2 = ejecutar.medirMetodo2(datosMetodo2);

        // Estimar Big-O desde datos (tiempos)
        BigOEstimator.Estimacion e1 = BigOEstimator.estimar(r1.tiempos);
        BigOEstimator.Estimacion e2 = BigOEstimator.estimar(r2.tiempos);

        // Generar graficas
        Graficador.graficar(r1.tiempos, "Metodo 1 - Tiempos", "metodo1.png");
        Graficador.graficar(r2.tiempos, "Metodo 2 - Tiempos", "metodo2.png");

        // Ventana única con pestañas
        JFrame frame = new JFrame("Resultados - Analisis de Algoritmos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 750);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        frame.add(crearPanelBigO(e1, e2), BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Metodo 1 (Invertir)", crearPanelResultados(r1, "metodo1.png"));
        tabs.addTab("Metodo 2 (Ordenar)", crearPanelResultados(r2, "metodo2.png"));

        frame.add(tabs, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JComponent crearPanelBigO(BigOEstimator.Estimacion e1, BigOEstimator.Estimacion e2) {
        JTextArea info = new JTextArea();
        info.setEditable(false);
        info.setLineWrap(true);
        info.setWrapStyleWord(true);

        // Formato solicitado: "big(o) metodo 1: ... metodo 2: ..."
        // (Incluyo el error como dato opcional; si no lo quieres, lo quitamos)
        String texto =
                "Big(O)\n" +
                "Metodo 1: " + e1.notacion + "\n" +
                "Metodo 2: " + e2.notacion + "\n\n";

        info.setText(texto);

        JScrollPane sp = new JScrollPane(info);
        sp.setPreferredSize(new Dimension(1100, 110));
        return sp;
    }

    private JPanel crearPanelResultados(Ejecutar.Resultado r, String imagePath) {
        JPanel panel = new JPanel(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setEditable(false);

        StringBuilder sb = new StringBuilder();
        sb.append("Ciclo ; Valor ; Tiempo(ns)\n");
        for (int i = 0; i < r.tiempos.length; i++) {
            String val = (r.outputs != null && i < r.outputs.length) ? r.outputs[i] : "";
            sb.append(i + 1).append(" ; ").append(val).append(" ; ").append(r.tiempos[i]).append("\n");
        }
        area.setText(sb.toString());

        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(460, 600));

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
        split.setDividerLocation(460);
        panel.add(split, BorderLayout.CENTER);

        return panel;
    }

    // ===== Generador aleatorio para Metodo 1 =====
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
}