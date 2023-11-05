package ro.srth.leila.commands.cmds.slash;

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
import ro.srth.leila.commands.SlashCommand;
import ro.srth.leila.commands.util.SayBan;
import ro.srth.leila.main.Bot;

import java.io.File;

public class Say extends SlashCommand {

    public Say() {
        this.commandName = "say";
        this.description = "Makes the bot say a message";
        args.add(new OptionData(OptionType.STRING, "content", "What you want the bot to say.", false));
        args.add(new OptionData(OptionType.STRING, "replyto", "Optional message id of the message you want to reply to.", false));
        args.add(new OptionData(OptionType.ATTACHMENT, "attachment", "Optional attachment to send.", false));
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
                String sanitized = sanitize(message);
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
            upload = attachment != null ? attachment.getProxy().downloadToFile(new File("C:\\Users\\SRTH_\\AppData\\Local\\Temp\\" + attachment.getFileName())).join() : null;

            if(message == null && upload == null){
                event.reply("you must fill in either the content or attachment options").setEphemeral(true).queue();
                return;
            }

            if (msgId == null) {
                if (message == null){
                    event.getChannel().sendFiles(FileUpload.fromData(upload)).queue();
                    event.reply("sending file").setEphemeral(true).queue();
                } else {
                    MessageCreateAction tmp = event.getChannel().sendMessage(message);

                    if (upload != null) {
                        tmp.addFiles(FileUpload.fromData(upload)).queue();
                    } else {
                        tmp.queue();
                    }
                    event.reply("sending content " + '"' + message + '"').setEphemeral(true).queue();
                }
            } else {
                event.getChannel().retrieveMessageById(msgId).queue((msg) -> {

                    if (message == null){
                        msg.replyFiles(FileUpload.fromData(upload)).queue();
                        event.reply("sending file").setEphemeral(true).queue();
                    } else{
                        MessageCreateAction tmp = msg.reply(message);

                        if(upload != null){
                            tmp.addFiles(FileUpload.fromData(upload)).queue();
                        }
                        else{
                            tmp.queue();
                        }
                        event.reply("sending content " + '"' + message + '"').setEphemeral(true).queue();
                    }

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