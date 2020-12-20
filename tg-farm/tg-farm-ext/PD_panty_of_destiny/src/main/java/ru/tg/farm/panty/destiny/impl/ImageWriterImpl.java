package ru.tg.farm.panty.destiny.impl;

import ru.tg.farm.panty.destiny.pool.NamesPool;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class ImageWriterImpl implements ru.tg.farm.panty.destiny.service.ImageWriter {

    @Autowired
    private NamesPool namesPool;

    @SneakyThrows
    @Override
    public String write(String url) {
        final BufferedImage image = ImageIO.read(new URL(url));

        InputStream fontStream = getClass().getResourceAsStream("/impact.ttf");

        Font font = Font.createFont(Font.TRUETYPE_FONT, fontStream);
        font = font.deriveFont(Float.valueOf(getFontSize(image)));

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);

        Graphics g = image.getGraphics();
        String caption = namesPool.getRandomName();

        FontMetrics metrics = g.getFontMetrics(font);
        g.setFont(font);

        int x = (image.getWidth() - metrics.stringWidth(caption)) / 2;
        int y = image.getHeight() - getFontSize(image);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        AffineTransform transform = g2d.getTransform();
        transform.translate(x, y);
        g2d.transform(transform);
        g2d.setColor(Color.black);
        FontRenderContext frc = g2d.getFontRenderContext();
        TextLayout tl = new TextLayout(caption, font, frc);
        Shape shape = tl.getOutline(null);
        g2d.setStroke(new BasicStroke(3f));
        g2d.draw(shape);
        g2d.setColor(Color.white);
        g2d.fill(shape);
        g.setColor(Color.WHITE);

        String pathToImage = "image,jpg";
        OutputStream os = Files.newOutputStream(Paths.get(pathToImage));
        ImageIO.write(image, "jpg", os);
        return pathToImage;
    }

    @SneakyThrows
    public static void main(String[] args) {
        final BufferedImage image = ImageIO.read(new URL("https://cdn3.static1-sima-land.com/items/3735050/0/400.jpg?v=1560241506"));

        ClassLoader classLoader = ImageWriterImpl.class.getClassLoader();
        File fontFile = new File(classLoader.getResource("impact.ttf").getFile());

        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        font = font.deriveFont(Float.valueOf(getFontSize(image)));

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);

        Graphics g = image.getGraphics();
        String caption = "Джумана КАБИДОЕВНА".toUpperCase();

        FontMetrics metrics = g.getFontMetrics(font);
        g.setFont(font);

        int x = (image.getWidth() - metrics.stringWidth(caption)) / 2;
        int y = image.getHeight() - getFontSize(image);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        AffineTransform transform = g2d.getTransform();
        transform.translate(x, y);
        g2d.transform(transform);
        g2d.setColor(Color.black);
        FontRenderContext frc = g2d.getFontRenderContext();
        TextLayout tl = new TextLayout(caption, font, frc);
        Shape shape = tl.getOutline(null);
        g2d.setStroke(new BasicStroke(3f));
        g2d.draw(shape);
        g2d.setColor(Color.white);
        g2d.fill(shape);
        g.setColor(Color.WHITE);

        ImageIO.write(image, "jpg", new File("D:\\panty-of-desteny-tg-bot\\src\\main\\resources\\image.jpg"));


    }

    private static Integer getFontSize(BufferedImage image) {
        Integer size = 0;
        if (image.getWidth() > 1000) {
            size = 57;
        }
        if (image.getWidth() >= 700 && image.getWidth() < 1000) {
            size = 37;
        }
        if (image.getWidth() >= 500 && image.getWidth() < 700) {
            size = 33;
        }
        if (image.getWidth() >= 300 && image.getWidth() < 500) {
            size = 27;
        }
        if (image.getWidth() < 350) {
            size = 24;
        }
        return size;
    }


}
