package principal;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Graficador {

    public static void graficar(long[] tiempos, String titulo, String salida) {
        if (tiempos == null || tiempos.length == 0)
            return;

        int width = 800;
        int height = 600;
        int padding = 60;

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        long min = Long.MAX_VALUE, max = Long.MIN_VALUE;
        for (long t : tiempos) {
            if (t < min) min = t;
            if (t > max) max = t;
        }
        if (min == max) min = 0;

        g.setColor(Color.BLACK);
        g.drawLine(padding, height - padding, width - padding, height - padding);
        g.drawLine(padding, padding, padding, height - padding);

        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        int n = tiempos.length;

        double xScale = (double)(width - 2 * padding) / Math.max(1, (n - 1));
        double yRange = Math.max(1, (double)(max - min));

        int ticks = 10;
        for (int i = 0; i <= ticks; i++) {
            int y = padding + (int)((height - 2 * padding) * i / (double)ticks);
            g.setColor(new Color(230, 230, 230));
            g.drawLine(padding, y, width - padding, y);
            long labelVal = max - (long)((max - min) * i / (double)ticks);
            g.setColor(Color.BLACK);
            g.drawString(Long.toString(labelVal), 5, y + 5);
        }

        int xLabelInterval = Math.max(1, n / 10);
        for (int i = 0; i < n; i += xLabelInterval) {
            int x = padding + (int)(i * xScale);
            String lbl = Integer.toString(i + 1);
            g.setColor(Color.BLACK);
            g.drawString(lbl, x - g.getFontMetrics().stringWidth(lbl) / 2, height - padding + 20);
        }

        g.setColor(Color.BLUE);
        for (int i = 0; i < n - 1; i++) {
            int x1 = padding + (int)(i * xScale);
            int y1 = padding + (int)((max - tiempos[i]) * (height - 2 * padding) / yRange);
            int x2 = padding + (int)((i + 1) * xScale);
            int y2 = padding + (int)((max - tiempos[i + 1]) * (height - 2 * padding) / yRange);
            g.drawLine(x1, y1, x2, y2);
        }

        g.setColor(Color.RED);
        for (int i = 0; i < n; i++) {
            int x = padding + (int)(i * xScale);
            int y = padding + (int)((max - tiempos[i]) * (height - 2 * padding) / yRange);
            g.fillOval(x - 3, y - 3, 6, 6);
        }

        String xlabel = "Ciclo";
        String ylabel = "Tiempo (ns)";
        g.setColor(Color.BLACK);
        g.drawString(xlabel, width / 2 - g.getFontMetrics().stringWidth(xlabel) / 2, height - 10);

        AffineTransform orig = g.getTransform();
        g.rotate(-Math.PI / 2);
        g.drawString(ylabel, -height / 2 - g.getFontMetrics().stringWidth(ylabel) / 2, 20);
        g.setTransform(orig);

        g.setFont(new Font("SansSerif", Font.BOLD, 14));
        g.drawString(titulo, width / 2 - g.getFontMetrics().stringWidth(titulo) / 2, 20);

        g.dispose();

        try {
            ImageIO.write(img, "png", new File(salida));
        } catch (IOException e) {
            System.err.println("Error al guardar grafica: " + e.getMessage());
        }
    }
}