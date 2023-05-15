package ro.srth.leila.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;
import ro.srth.leila.Bot;

import java.io.File;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class HappyBirthdayMention extends ListenerAdapter {
    static File dir = new File("C:\\Users\\SRTH_\\Desktop\\leilabot\\bday");
    static File[] files = dir.listFiles();;
    static int amt = files.length;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getMessage().getContentRaw().toLowerCase().contains("happy birthday") && !(event.getMessage().getAuthor().getIdLong() == event.getJDA().getSelfUser().getIdLong())){
            Bot.log.info(event.getMessage().getAuthor().getAsTag() + "Fired HappyBirthdayMention");

            Random rand = ThreadLocalRandom.current();

            File file = files[rand.nextInt(files.length)]; // chooses a random file

            FileUpload upload = FileUpload.fromData(file);

            Bot.log.info("filepath:" + file.toString());

            event.getMessage().getChannel().sendFiles(upload).queue();
        }
    }


    public static int getNumberOfBdayArtPctures(){
        File dir = new File("C:\\Users\\SRTH_\\Desktop\\leilabot\\bday");
        File[] files = dir.listFiles();
        return files.length;
    }
}
