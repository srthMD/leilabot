package ro.srth.leila.listener.listeners;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import ro.srth.leila.Bot;
import ro.srth.leila.annotations.PrivateAccess;
import ro.srth.leila.commands.cmds.slash.Webhook;
import ro.srth.leila.listener.Listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@PrivateAccess
public class WebhookTroll extends Listener {

    public WebhookTroll(){
        this.name = "WebhookTroll";
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getChannel().getIdLong() == 1120459667638865950L && (!event.getAuthor().isBot())){
            if(!Webhook.isActive()){return;}

            String url;
            try {
                url = new BufferedReader(new FileReader("C:\\Users\\SRTH_\\Desktop\\leilabot\\webhook.txt")).readLine();
            } catch (IOException e) {
                event.getChannel().sendMessage("something went wrong with the webhook link, it is either invalid or an issue on the bot's side").queue();
                Bot.log.error(e.toString());
                return;
            }

            WebhookClient client = WebhookClient.withUrl(url);

            WebhookMessageBuilder builder = new WebhookMessageBuilder();

            BufferedReader namereader;
            BufferedReader pfpreader;

            try {
                namereader = new BufferedReader(new FileReader("C:\\Users\\SRTH_\\Desktop\\leilabot\\name.txt"));
                pfpreader = new BufferedReader(new FileReader("C:\\Users\\SRTH_\\Desktop\\leilabot\\pfp.txt"));
                builder.setUsername(namereader.readLine());
                builder.setAvatarUrl(pfpreader.readLine());
                namereader.close();
                pfpreader.close();
            } catch (IOException e) {
                event.getChannel().sendMessage("something went wrong reading set name and profile picture").queue();
                Bot.log.error(e.toString());
                builder.reset();
                return;
            }

            if(event.getMessage().getAttachments().isEmpty()){
                builder.setContent(event.getMessage().getContentRaw());
                client.send(builder.build());
            } else{
                List<Message.Attachment> attachmenturl = event.getMessage().getAttachments();
                for (Message.Attachment attach: attachmenturl) {
                    builder.addFile(attach.getProxy().downloadToFile(new File("C:\\Users\\SRTH_\\AppData\\Local\\Temp\\" + attach.getFileName()+"."+attach.getFileExtension())).join());
                }
                builder.setContent(event.getMessage().getContentRaw());
                try{
                    client.send(builder.build());
                }catch(Exception e){
                    event.getChannel().sendMessage("something went wrong, error for debugging: \n\n" + e).queue();
                    builder.reset();
                    return;
                }
                builder.reset();
            }
        }
    }
}

