package ro.srth.leila.commands;

import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;


public class SaySlashCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("say")) {
            System.out.println("SaySlashCommand Fired");

            OptionMapping content1 = event.getOption("content");
            OptionMapping channel1 = event.getOption("channel");

            String message = content1.getAsString();
            String channel2 = null;
            ChannelType channel5 = null;

            if (channel1 == null){
                channel5 = event.getInteraction().getChannelType();
                channel2 = event.getInteraction().getChannel().getId();
            } else if (channel1.getChannelType() != ChannelType.TEXT){
                event.reply("invalid channel type ya hmar").setEphemeral(true).queue();
            } else{
                channel5 = channel1.getChannelType();
                channel2 = channel1.getAsChannel().asTextChannel().getId();
            }


            if (channel1 != null) {
                assert channel2 != null;
                event.getGuild().getChannelById(TextChannel.class, channel2).sendMessage(message).queue();
                event.reply("Sending content " + '"' + message + '"').setEphemeral(true).queue();

            } else if (channel1 == null) {
                String channel4 = event.getInteraction().getChannel().getId();

                event.getGuild().getChannelById(TextChannel.class, channel4).sendMessage(message).queue();
                event.reply("Sending content " + '"' + message + '"').setEphemeral(true).queue();

            } else if (channel5 != ChannelType.TEXT){
                event.reply("only text channels ya hmar").setEphemeral(true).queue();
            }
            else{
                event.reply("somethig went wrong ya hmar").setEphemeral(true).queue();
            }
        }
    }
}
