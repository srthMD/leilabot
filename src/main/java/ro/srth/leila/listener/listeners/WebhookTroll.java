package ro.srth.leila.listener.listeners;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import ro.srth.leila.exception.GuildNotFoundException;
import ro.srth.leila.guild.GuildConfiguration;
import ro.srth.leila.guild.GuildReader;
import ro.srth.leila.guild.vars.GuildVariable;
import ro.srth.leila.listener.Listener;

import java.io.File;
import java.util.List;


public class WebhookTroll extends Listener {

    public WebhookTroll(){
        this.name = "WebhookTroll";
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Guild guild = event.getGuild();

        try {
            GuildConfiguration config = GuildReader.get(guild.getIdLong());
            GuildVariable<?> var = config.getVars().get("webhookActive");
            GuildVariable<?> channel = config.getVars().get("webhookChannel");

            //webhookActive is a boolean, no need to check the cast
            if(!((boolean) var.getVar())){
                return;
            }

            if((long) channel.getVar() != event.getChannel().getIdLong()){
                return;
            }
        } catch (GuildNotFoundException e) {
            throw new RuntimeException(e);
        }


        String url, name, pfp;

        try {
            var vars = GuildReader.get(event.getGuild().getIdLong()).getVars();
            url = vars.get("webhookLink").toString();
            pfp = vars.get("webhookPfp").toString();
            name = vars.get("webhookName").toString();
        } catch (GuildNotFoundException e) {
            throw new RuntimeException(e);
        }

        WebhookClient client = WebhookClient.withUrl(url);

        WebhookMessageBuilder builder = new WebhookMessageBuilder();


        builder.setUsername(name);
        builder.setAvatarUrl(pfp);

        if(event.getMessage().getAttachments().isEmpty()){
            builder.setContent(event.getMessage().getContentRaw());
            client.send(builder.build());
        } else{
            List<Message.Attachment> attachmenturl = event.getMessage().getAttachments();
            for (Message.Attachment attach: attachmenturl) {
                builder.addFile(attach.getProxy().downloadToFile(new File("C:\\Users\\SRTH_\\AppData\\Local\\Temp\\" + attach.getFileName())).join());
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

