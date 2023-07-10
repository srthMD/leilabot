package ro.srth.leila.commands.cmds;

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
import ro.srth.leila.*;
import ro.srth.leila.commands.Command;
import ro.srth.leila.util.SayBan;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Say extends Command {

    SayBan handler = new SayBan();

    public Say() {
        this.commandName = "say";
        this.description = "Makes the bot say a message";
        this.type = CommandType.SLASH;
        this.args = new ArrayList<OptionData>();
        args.add(new OptionData(OptionType.STRING, "content", "What you want the bot to say.", false));
        args.add(new OptionData(OptionType.STRING, "replyto", "Optional message id of the message you want to reply to.", false));
        args.add(new OptionData(OptionType.ATTACHMENT, "attachment", "Optional attachment to send.", false));
        this.register = true;
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(this.commandName) && !event.isAcknowledged()) {
            try {
                if(handler.isBanned(event.getUser().getIdLong())){
                    Bot.log.info(event.getUser().getName() + "Fired /say but was banned");
                    event.reply("you are banned from this command").setEphemeral(true).queue();
                }
                else{
                    Bot.log.info(event.getInteraction().getUser().getName() + " Fired SaySlashCmd");

                    String message = event.getOption("content", OptionMapping::getAsString);

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
                        upload = attachment != null ? attachment.getProxy().downloadToFile(File.createTempFile("send",  "." + attachment.getFileExtension())).join() : null;
                    } catch (IOException e) {
                        event.getInteraction().reply("something went wrong processing file").setEphemeral(true).queue();
                        throw new RuntimeException(e);
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
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}