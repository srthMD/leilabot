package ro.srth.leila.util;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
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
import java.util.Map;

public class ShitifyHandler {
    final static Map<String, String> complexFillters = Map.of(
            "mirror", "crop=iw/2:ih:0:0,split[left][tmp];[tmp]hflip[right];[left][right] hstack",
            "flanger", "flanger=delay=10:depth=5:regen=95:speed=1:shape=triangular:width=95",
            "funnymic", "firequalizer=gain_entry='entry(1000,0); entry(1001, -INF); entry(1e6+1000,0)'",
            "colorbake", "curves=strong_contrast, curves=vintage, curves=strong_contrast, curves=darker, curves=strong_contrast, erosion, eq=saturation=3:gamma=2"
    );

    private static final FFmpeg ffmpeg;

    static {
        try {
            ffmpeg = new FFmpeg("C:\\Users\\SRTH_\\Desktop\\leilabot\\ff\\ffmpeg\\bin\\ffmpeg.exe");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File compressVideo(File video, Integer bitrate, Integer fps, Integer audioBitRate, Integer audioSamplingRate, Integer volume, String preset) throws Exception{
        File out = new File("C:\\Users\\SRTH_\\Desktop\\leilabot\\ff\\out\\out" + video.getName().substring(video.getName().lastIndexOf(".")));

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(video.getAbsolutePath())
                .overrideOutputFiles(true)
                .addOutput(out.getAbsolutePath())
                    .disableSubtitle()
                    .setAudioCodec("aac")
                    .setAudioSampleRate(audioSamplingRate)
                    .setAudioBitRate(audioBitRate)

                    .setVideoCodec("libx264")
                    .setVideoFrameRate((double) fps)
                    .setVideoBitRate(bitrate)
                    .addExtraArgs("-filter:a", "volume=" + (volume*35))
                    .done();

        if(!preset.isEmpty()){
           builder.setComplexFilter(complexFillters.get(preset));
        }

        FFmpegExecutor ex = new FFmpegExecutor(ffmpeg);
        ex.createJob(builder).run();

        return out;
    }

    public static File compressAudio(File audio,Integer audioBitRate, Integer audioSamplingRate, Integer volume, Double speed, String preset) throws Exception{
        File out = new File("C:\\Users\\SRTH_\\Desktop\\leilabot\\ff\\out\\out" + audio.getName().substring(audio.getName().lastIndexOf(".")));

        String extension = audio.getName().substring(audio.getName().lastIndexOf(".") + 1);

        FFmpegBuilder builder;

        if(!(extension.equals("ogg"))){
            builder = new FFmpegBuilder()
                    .setInput(audio.getAbsolutePath())
                    .overrideOutputFiles(true)
                    .addOutput(out.getAbsolutePath())
                    .setAudioBitRate(audioBitRate)
                    .setAudioSampleRate(audioSamplingRate)

                    .done();
            } else{
            builder = new FFmpegBuilder()
                    .setInput(audio.getAbsolutePath())
                    .overrideOutputFiles(true)
                    .addOutput(out.getAbsolutePath())
                    .setAudioSampleRate(audioSamplingRate)

                    .done();
            }

        if(!preset.isEmpty()){
            builder.setComplexFilter(complexFillters.get(preset) + ",volume=" + (volume*3) + ",atempo=" + Math.max(0.5, Math.min(10, speed)));
        } else{
            builder.setComplexFilter("volume=" + (volume*3) + ",atempo=" + Math.max(0.5, Math.min(10, speed)));
        }

        FFmpegExecutor ex = new FFmpegExecutor(ffmpeg);
        ex.createJob(builder).run();

        return out;
    }


    public static File compressImg(File image, int resizeBefore, int resizeAfter, int quality) throws IOException {

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
