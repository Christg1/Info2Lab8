package triangle;

import resizable.ResizableImage;

import java.awt.*;
import java.awt.image.BufferedImage;

import static resizable.Debug.print;

public class Triangle implements ResizableImage {
    private int drawTriangleCount = 0;

    private void drawSierpinski(Graphics2D g, int x, int y, int size, int level) {
        if (level == 0) {
            // Draw the main equilateral triangle
            int[] xPoints = {x, x + size / 2, x - size / 2};
            int[] yPoints = {y, y + (int) (Math.sqrt(3) * size / 2), y + (int) (Math.sqrt(3) * size / 2)};
            g.fillPolygon(xPoints, yPoints, 3);
        } else {
            int halfSize = size / 2;
            drawSierpinski(g, x, y, halfSize, level - 1);
            drawSierpinski(g, x + halfSize / 2, y + (int) (Math.sqrt(3) * halfSize / 2), halfSize, level - 1);
            drawSierpinski(g, x - halfSize / 2, y + (int) (Math.sqrt(3) * halfSize / 2), halfSize, level - 1);
        }
    }

    private BufferedImage drawTriangle(Dimension size) {
        print("drawTriangle: " + ++drawTriangleCount + " size: " + size);
        BufferedImage bufferedImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gBuffer = (Graphics2D) bufferedImage.getGraphics();
        gBuffer.setColor(Color.black);

        int border = 2;
        gBuffer.drawRect(border, border, size.width - 2 * border, size.height - 2 * border);
        gBuffer.setColor(Color.darkGray);
        border = 8;
        gBuffer.drawRect(border, border, size.width - 2 * border, size.height - 2 * border);
        
        // Clear the area
        gBuffer.setColor(Color.white);
        gBuffer.fillRect(border, border, size.width - 2 * border, size.height - 2 * border);
        
        // Draw Sierpinski Triangle
        gBuffer.setColor(Color.black);
        int triangleSize = Math.min(size.width, size.height) - 2 * border;
        drawSierpinski(gBuffer, size.width / 2, border, triangleSize, 5); // 5 is the recursion depth

        return bufferedImage;
    }

    private BufferedImage bufferedImage;
    private Dimension bufferedImageSize;

    @Override
    public Image getImage(Dimension triangleSize) {
        if (triangleSize.equals(bufferedImageSize))
            return bufferedImage;
        bufferedImage = drawTriangle(triangleSize);
        bufferedImageSize = triangleSize;
        return bufferedImage;
    }

    @Override
    public Image getResizeImage(Dimension size) {
        BufferedImage bufferedImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gBuffer = (Graphics2D) bufferedImage.getGraphics();
        gBuffer.setColor(Color.pink);
        int border = 2;
        gBuffer.drawRect(border, border, size.width - 2 * border, size.height - 2 * border);
        return bufferedImage;
    }
}
