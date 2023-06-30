package ro.srth.leila.util;

import io.github.techgnious.IVCompressor;
import io.github.techgnious.dto.*;
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

    public File compressVid(File video, Integer bitrate, Integer fps, Integer height, Integer width, Integer audioBitRate, Integer audioSamplingRate) throws Exception{
        IVCompressor compressor = new IVCompressor();
        IVAudioAttributes audio = new IVAudioAttributes();
        IVVideoAttributes video1 = new IVVideoAttributes();

        video1.setBitRate(bitrate);
        video1.setFrameRate(fps);

        IVSize size = new IVSize();
        size.setHeight(height);
        size.setWidth(width);

        video1.setSize(size);

        audio.setBitRate(audioBitRate);
        audio.setChannels(1);
        audio.setSamplingRate(audioSamplingRate);

        File file = new File(video.toURI());

        byte[] data = compressor.encodeVideoWithAttributes(FileUtils.readFileToByteArray(file), VideoFormats.MP4, audio, video1);

        FileUtils.writeByteArrayToFile(new File(file.getAbsolutePath()), data);

        return file;
    }


    public File compressImg(File image, int resizeBefore, int resizeAfter, int quality) throws IOException {

        BufferedImage img = ImageIO.read(image);

        BufferedImage image1 = Scalr.resize(img, resizeBefore);
        BufferedImage image2 = Scalr.resize(image1, resizeAfter);

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
        param.setCompressionQuality((float) quality/100);
        writer.write(null, new IIOImage(image3, null, null), param);

        os.close();
        ios.close();
        writer.dispose();

        return compressed;
    }
}
