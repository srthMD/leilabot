package ro.srth.leila.commands;

import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import ro.srth.leila.Bot;
import ro.srth.leila.api.SayBan;


public class SaySlashCommand extends ListenerAdapter {
    SayBan handler = new SayBan();
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("say")) {
            if(String.valueOf(handler.readJson()).contains(event.getInteraction().getUser().getId())){
                Bot.log.info(event.getUser().getAsTag() + "Fired /say but was banned");
                event.reply("yuo are banned from this command ya hamr").setEphemeral(true).queue();
            }
            else if(!String.valueOf(handler.readJson()).contains(event.getInteraction().getUser().getId())){
                Bot.log.info(event.getInteraction().getUser().getAsTag() + " Fired SaySlashCmd");

                OptionMapping content1 = event.getOption("content");
                OptionMapping channel1 = event.getOption("channel");

                String message = content1.getAsString();
                String channel2;
                ChannelType channel5;

                if (channel1 == null){
                    channel5 = event.getInteraction().getChannelType();
                    channel2 = event.getInteraction().getChannel().getId();
                    Bot.log.info("channel1 == null");
                } else if (channel1.getChannelType() != ChannelType.TEXT){
                    event.reply("invalid channel type ya hmar").setEphemeral(true).queue();
                    Bot.log.info("channel1 type is not text");

                    return;
                } else{
                    channel5 = channel1.getChannelType();
                    channel2 = channel1.getAsChannel().asTextChannel().getId();
                    Bot.log.info("channel1 is proper type and not null");
                }


                if (channel1 != null) {
                    assert channel2 != null; // idk
                    event.getGuild().getChannelById(TextChannel.class, channel2).sendMessage(message).queue();
                    event.reply("Sending content " + '"' + message + '"').setEphemeral(true).queue();
                    Bot.log.info(event.getInteraction().getUser().getAsTag() + " sent " + message);
                } else if (channel1 == null) {
                    String channel4 = event.getInteraction().getChannel().getId();

                    event.getGuild().getChannelById(TextChannel.class, channel4).sendMessage(message).queue();
                    event.reply("Sending content " + '"' + message + '"').setEphemeral(true).queue();
                    Bot.log.info(event.getInteraction().getUser().getAsTag() + " sent " + message);

                }   else if (channel5 != ChannelType.TEXT /* additional check */){
                    event.reply("only text channels ya hmar").setEphemeral(true).queue();
                }
                else{
                    event.reply("somethig went wrong ya hmar").setEphemeral(true).queue();
                    Bot.log.warning("something went wrong on say slash command");
                }
            }
        }
    }
}
