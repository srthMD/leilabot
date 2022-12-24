package ro.srth.leila.listener;

import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MsgOnKick extends ListenerAdapter {
    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
        String user = event.getUser().getAsTag();

        String message = "Goodtbye " + user + " You Are Stupid Cunt";

        if(event.getGuild().getId().equals("1021759228236529726")){
            event.getGuild().getTextChannelById("1021759229452898356").sendMessage(message).queue();
        } else {
            event.getGuild().getDefaultChannel().asTextChannel().sendMessage(message).queue();
        }
    }
}
