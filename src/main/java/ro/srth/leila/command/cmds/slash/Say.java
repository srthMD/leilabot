package ro.srth.leila.command.cmds.slash;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.exceptions.ErrorHandler;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.ErrorResponse;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.command.SlashCommand;
import ro.srth.leila.command.util.SayBan;
import ro.srth.leila.main.Bot;

import java.io.File;
import java.io.IOException;

public class Say extends SlashCommand {

    static {
        description = "Makes the bot say a message";
        args.add(new OptionData(OptionType.STRING, "content", "What you want the bot to say.", false));
        args.add(new OptionData(OptionType.STRING, "replyto", "Optional message id of the message you want to reply to.", false));
        args.add(new OptionData(OptionType.ATTACHMENT, "attachment", "Optional attachment to send.", false));
    }

    public Say(Guild guild) {
        super(guild);
    }

    @Override
    public void runSlashCommand(@NotNull SlashCommandInteractionEvent event) {
        if(SayBan.isBanned(event.getUser().getIdLong())){
            Bot.log.info(event.getUser().getName() + "Fired /say but was banned");
            event.reply("you are banned from this command").setEphemeral(true).queue();
        }
        else{
            String message = event.getOption("content", OptionMapping::getAsString);

            if (message != null) {
                String sanitized = sanitize(message); // unused rn bc sanitization is shit
            } else{
                event.reply("message is null").setEphemeral(true).queue();
                return;
            }

            Long msgId;

            try {
                msgId = event.getOption("replyto", OptionMapping::getAsLong);
            } catch (NumberFormatException e){
                event.reply("invalid id, should only have numbers").setEphemeral(true).queue();
                return;
            }

            Message.Attachment attachment = event.getOption("attachment", OptionMapping::getAsAttachment);
            File upload;
            try {
                upload = attachment != null ? attachment.getProxy().downloadToFile(File.createTempFile("send", "." + attachment.getFileName())).join() : null;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (msgId == null) {
                MessageCreateAction tmp = event.getChannel().sendMessage(message);

                if (upload != null) {
                    tmp.addFiles(FileUpload.fromData(upload)).queue();
                } else {
                    tmp.queue();
                }
                event.reply("sending content " + '"' + message + '"').setEphemeral(true).queue();
            } else {
                event.getChannel().retrieveMessageById(msgId).queue((msg) -> {

                    MessageCreateAction tmp = msg.reply(message);

                    if(upload != null){
                        tmp.addFiles(FileUpload.fromData(upload)).queue();
                    }
                    else{
                        tmp.queue();
                    }
                    event.reply("sending content " + '"' + message + '"').setEphemeral(true).queue();

                }, new ErrorHandler().handle(ErrorResponse.UNKNOWN_MESSAGE, (e) -> {
                    event.getInteraction().reply("message id is invalid").setEphemeral(true).queue();
                    Bot.log.warn("invalid message id for say command");
                }));
            }
        }
    }
    
    private String sanitize(String str){
        StringBuilder after = new StringBuilder();

        for (char c : str.toCharArray()) {
            after.append(c);

            if(c == '@'){
                after.append('.');
            }
        }

        return after.toString();
    }
}