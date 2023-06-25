package ro.srth.leila.util;

import io.github.techgnious.IVCompressor;
import io.github.techgnious.dto.IVAudioAttributes;
import io.github.techgnious.dto.IVSize;
import io.github.techgnious.dto.IVVideoAttributes;
import io.github.techgnious.dto.VideoFormats;
import org.apache.commons.io.FileUtils;
import org.imgscalr.Scalr;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;


public class ShitifyHandler {

    public File compressVid(File video) throws Exception{
        IVCompressor compressor = new IVCompressor();
        IVAudioAttributes audio = new IVAudioAttributes();
        IVVideoAttributes video1 = new IVVideoAttributes();

        video1.setBitRate(24000);
        video1.setFrameRate(5);

        IVSize size = new IVSize();
        size.setHeight(250);
        size.setWidth(700);

        video1.setSize(size);

        audio.setBitRate(16000);
        audio.setChannels(1);
        audio.setSamplingRate(16000);

        File file = new File(video.toURI());

        byte[] data = compressor.encodeVideoWithAttributes(FileUtils.readFileToByteArray(file), VideoFormats.MP4, audio, video1);

        FileUtils.writeByteArrayToFile(new File(file.getAbsolutePath()), data);

        return file;
    }


    public File compressImg(File image) throws IOException {
        BufferedImage img = ImageIO.read(image);
        BufferedImage image1 = Scalr.resize(img, 40);

        BufferedImage image2 = Scalr.resize(image1, 400);

        BufferedImage image3 = new BufferedImage(image2.getWidth(), image2.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = image3.createGraphics();
        g2d.drawImage(image2, 0, 0, Color.WHITE, null);
        g2d.dispose();

        File compressed = new File( "C:\\temp\\compressed.png");
        OutputStream os = new FileOutputStream(compressed);

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");
        ImageWriter writer = writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.07f);
        writer.write(null, new IIOImage(image3, null, null), param);

        os.close();
        ios.close();
        writer.dispose();

        return compressed;
    }
}
