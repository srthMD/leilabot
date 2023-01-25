package ro.srth.leila.api;

import org.imgscalr.Scalr;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.*;
import java.util.Iterator;


public class ShitifyHandler {
    public File compress(File image) throws IOException {
        BufferedImage img = ImageIO.read(image);
        BufferedImage image1 = Scalr.resize(img, 50);

        BufferedImage image2 = Scalr.resize(image1, 400);

        BufferedImage image3 = new BufferedImage(image2.getWidth(), image2.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = image3.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, image3.getWidth(), image3.getHeight());
        g2d.drawImage(image2, 0, 0, null);
        g2d.dispose();

        File compressed = new File("C:\\Users\\SRTH_\\Desktop\\leilabot\\compressed.png");
        OutputStream os = new FileOutputStream(compressed);

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");
        ImageWriter writer = writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.065f);  // Change the quality value you prefer
        writer.write(null, new IIOImage(image2, null, null), param);

        os.close();
        ios.close();
        writer.dispose();

        return compressed;
    }
}
