package ro.srth.leila.listener.listeners;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import ro.srth.leila.annotations.PrivateAccess;
import ro.srth.leila.listener.Listener;

@PrivateAccess
public class PrivateMute extends Listener {

    public PrivateMute(){
        this.name = "PrivateMute";
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if(event.getChannel().getIdLong() == 1060256521973403699L && event.getAuthor().getIdLong() == 584834083943874581L){

            Member member;

            try {
                member = event.getJDA().getGuildById(1021759228236529726L).getMemberById(event.getMessage().getContentRaw().replaceAll("[^0-9]", ""));
            } catch (Exception ignored) {
                System.out.println("member is null");
                return;
            }

            GuildVoiceState guildVoiceState = member.getVoiceState();

            if (event.getMessage().getContentRaw().toLowerCase().contains("d")){
                if (guildVoiceState.isGuildDeafened()){
                     member.deafen(false).queue();
                 } else {
                      member.deafen(true).queue();
                }
            } else {
                if (guildVoiceState.isGuildMuted()) {
                    member.mute(false).queue();
                } else {
                    member.mute(true).queue();
                }
            }
        }
    }
}
