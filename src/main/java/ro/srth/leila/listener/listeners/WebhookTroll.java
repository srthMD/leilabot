package ro.srth.leila.listener.listeners;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import ro.srth.leila.annotations.PrivateAccess;
import ro.srth.leila.listener.Listener;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@PrivateAccess
public class WebhookTroll extends Listener {

    public WebhookTroll(){
        this.name = "WebhookTroll";
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if(event.getChannel().getIdLong() == 1120459667638865950L && (event.getAuthor().getIdLong() == 584834083943874581L || event.getAuthor().getIdLong() == 820081420889751603L)){

            String url = "https://discord.com/api/v10/webhooks/1120452200943058955/C4wc9b_0e-cdazQBv33laTPj64fACeY-Tl4DLl0d7UfNjZL59IDDYBoZtFVvvCprA9fE";

            String testUrl = "https://discord.com/api/webhooks/1120455863145078814/kkK9StONzm2AHv6t-cr4OvhE2OjmLyMP6Dbl2dusUmpPHy3PeRK50UndFtbIRK43zHbk";
            String testUrl2 = "https://discord.com/api/webhooks/1120460475549880492/HrgmNv-poc_ADXXtRb6BOC5QU-HDj48vibCinZMgHNTC4vCxyQPBAYU_p3Mn40aLWZUc";

            WebhookClient client = WebhookClient.withUrl(url);

            WebhookMessageBuilder builder = new WebhookMessageBuilder();
            builder.setUsername("adomi");
            builder.setAvatarUrl("https://cdn.discordapp.com/avatars/820081420889751603/0a263b3c2c940599b03ea66d591b4cd7.webp?size=80"); // use this avatar


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

