package ro.srth.leila.listener.listeners;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.listener.Listener;

public class RoleOnJoin extends Listener {

    public RoleOnJoin(){
        this.name = "roleonjoin";
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(1127390470750867526L)).queue();
    }
}
