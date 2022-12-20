package ro.srth.leila.listener;

import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MsgOnKick extends ListenerAdapter {
    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
        String user = event.getUser().getAsTag();

        String message = "Goodtbye " + user + " You Are Stupid Cunt";
        event.getGuild().getDefaultChannel().asTextChannel().sendMessage(message).queue();
    }
}
