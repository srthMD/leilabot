package ro.srth.leila.listener.listeners;

import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.listener.Listener;

public class CupIsARetard extends Listener {

    public CupIsARetard(){
        this.name = "CupIsARetard";
    }

    @Override
    public void onGuildMemberRoleRemove(@NotNull GuildMemberRoleRemoveEvent event) {
        if(event.getMember().getIdLong() == 584834083943874581L && event.getGuild().getIdLong() == 1021759228236529726L && event.getRoles().contains(event.getMember().getGuild().getRoleById(1022408230237900800L))){
            event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(1022408230237900800L)).queue();
        }
    }
}
