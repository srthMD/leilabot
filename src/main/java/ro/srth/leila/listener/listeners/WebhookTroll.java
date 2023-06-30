package ro.srth.leila.listener.listeners;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import ro.srth.leila.Bot;
import ro.srth.leila.annotations.PrivateAccess;
import ro.srth.leila.listener.Listener;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@PrivateAccess
public class WebhookTroll extends Listener {

    String url;

    public WebhookTroll(){
        this.name = "WebhookTroll";
        url = Bot.env.get("HOOK");

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if(event.getChannel().getIdLong() == 1120459667638865950L && (!event.getAuthor().isBot())){

            WebhookClient client = WebhookClient.withUrl(url);

            WebhookMessageBuilder builder = new WebhookMessageBuilder();
            builder.setUsername("chaotiful");
            builder.setAvatarUrl("https://cdn.discordapp.com/avatars/561067355892088842/bceb4fe183b78b971166ab19eab99838.webp?size=80"); // use this avatar


            if(event.getMessage().getAttachments().isEmpty()){
                builder.setContent(event.getMessage().getContentRaw());
                client.send(builder.build());
            } else{
                List<Message.Attachment> attachmenturl = event.getMessage().getAttachments();
                for (Message.Attachment attach: attachmenturl) {
                    try {
                        try {
                            builder.addFile(attach.getProxy().downloadToFile(File.createTempFile("send", "." + attach.getFileExtension())).get());
                        } catch (InterruptedException | ExecutionException e){
                            throw new RuntimeException(e);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                builder.setContent(event.getMessage().getContentRaw());
                client.send(builder.build());
                builder.reset();
            }
        }
    }
}

