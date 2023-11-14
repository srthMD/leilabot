package ro.srth.leila.command.util;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.imgscalr.Scalr;
import ro.srth.leila.main.Config;

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

public final class MediaHandler {
    final static Map<String, String> complexFillters = Map.of(
            "mirror", "crop=iw/2:ih:0:0,split[left][tmp];[tmp]hflip[right];[left][right]hstack",
            "flanger", "flanger=delay=10:depth=0:regen=95:speed=10:shape=triangular:width=95,flanger=delay=10:depth=0:regen=95:speed=8:shape=triangular:width=85,flanger=delay=10:depth=0.1:regen=95:speed=7:shape=triangular:width=90",
            "funnymic", "firequalizer=gain_entry='entry(1000,0); entry(1001, -INF); entry(1e6+1000,0)',asubboost=dry=0.2:wet=1:boost=8:decay=0.3:cutoff=500:feedback=1",
            "colorbake", "curves=strong_contrast,curves=vintage,curves=strong_contrast,curves=darker,curves=strong_contrast,erosion,eq=saturation=3:gamma=2,asubboost=dry=0.2:wet=1:boost=8:decay=0.3:cutoff=500:feedback=1",
            "highpass", "asuperpass=centerf=600:order=10:qfactor=0.76:level=2,asuperpass=centerf=600:order=10:qfactor=0.76:level=1",
            "extremedistort", "flanger=delay=10:depth=0:regen=95:speed=10:shape=triangular:width=95," +
                    "flanger=delay=10:depth=0:regen=95:speed=8:shape=triangular:width=85," +
                    "firequalizer=gain_entry='entry(1000,0); entry(1001, -INF); entry(1e6+1000,0)'," +
                    "asubboost=dry=0.2:wet=1:boost=8:decay=0.3:cutoff=500:feedback=1," +
                    "asuperpass=centerf=600:order=10:qfactor=0.76:level=2," +
                    "asuperpass=centerf=600:order=10:qfactor=0.76:level=1," +
                    "volume=3"
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
        File out = new File(Config.ROOT + "\\ff\\out\\out" + video.getName().substring(video.getName().lastIndexOf(".")));

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

                    .done();

        if(!preset.isEmpty()){
           builder.setComplexFilter(complexFillters.get(preset) + ",volume=" + (volume*35) + ",acrusher=bits=8:samples=2");
        } else{
            builder.setComplexFilter("volume=" + (volume*35) + ",acrusher=bits=8:samples=2");
        }

        FFmpegExecutor ex = new FFmpegExecutor(ffmpeg);
        ex.createJob(builder).run();

        return out;
    }



    public static File compressAudio(File audio,Integer audioBitRate, Integer audioSamplingRate, Integer volume, Double speed, String preset) throws Exception{
        File out = new File(Config.ROOT + "\\ff\\out\\out" + audio.getName().substring(audio.getName().lastIndexOf(".")));

        String extension = audio.getName().substring(audio.getName().lastIndexOf(".") + 1);

        FFmpegBuilder builder;

        if(!(extension.equals("ogg"))){
            builder = new FFmpegBuilder()
                    .setInput(audio.getAbsolutePath())
                    .overrideOutputFiles(true)
                    .addOutput(out.getAbsolutePath())
                    .disableSubtitle()
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
            builder.setComplexFilter(complexFillters.get(preset) + ",volume=" + (volume*3) + ",atempo=" + Math.max(0.5, Math.min(10, speed)) + ",acrusher=bits=8:samples=2");
        } else{
            builder.setComplexFilter("volume=" + (volume*3) + ",atempo=" + Math.max(0.5, Math.min(10, speed)) + ",acrusher=bits=8:samples=2");
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

        File compressed = File.createTempFile("compressed", ".png");
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
