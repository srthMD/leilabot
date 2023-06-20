package ro.srth.leila.listener;

import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ro.srth.leila.Bot;
import ro.srth.leila.commands.ToggleTextReactions;


public class PolandGifMention extends ListenerAdapter {
    public static Channel channel;
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(!ToggleTextReactions.getToggledStatus()){return;}
        String match = "https://tenor.com/view/poland-polish-flag-gif-11055231";
        if(event.getMessage().getContentRaw().toLowerCase().contains(match) && !event.getMessage().getAuthor().getId().equals("1054544562841997363")){
            Bot.log.info("PolandGifMention fired by" + event.getAuthor().getAsTag()); // log in case of abuse

            String message = "POLSKA \uD83C\uDDF5\uD83C\uDDF1  \uD83C\uDDF5\uD83C\uDDF1  \uD83C\uDDF5\uD83C\uDDF1  \uD83C\uDDF5\uD83C\uDDF1  \uD83C\uDDF5\uD83C\uDDF1  \uD83C\uDDF5\uD83C\uDDF1  \uD83C\uDDF5\uD83C\uDDF1  BEST KRAJ OF THEM ALL \uD83E\uDEE1\uD83E\uDEE1\uD83E\uDEE1\uD83E\uDEE1\uD83E\uDEE1\uD83E\uDEE1\uD83E\uDEE1\uD83E\uDEE1\uD83E\uDEE1 I WAS DRIVING IN MY CAR ON THE ROAD ITS NOT SO FAR \uD83C\uDDF5\uD83C\uDDF1\uD83D\n BERLIN\uD83C\uDDE9\uD83C\uDDEA  LONDON\uD83C\uDFF4\uDB40\uDC67\uDB40\uDC62\uDB40\uDC65\uDB40\uDC6E\uDB40\uDC67\uDB40\uDC7F   MOSCOW TOO\uD83C\uDDF7\uD83C\uDDFA  ITS MY \uD83D\uDC96  I TELL YOU TRUE \uD83D\uDE4F  WHEN I CAME TO POLSKA \uD83C\uDDF5\uD83C\uDDF1 I SAW MY \uD83D\uDE97\uD83D\uDE97 WAS STOLEN \uD83D\uDE2D  IT WAS MY FAVORITE TRUCK \uD83D\uDEFB\uD83D\uDEFB\uD83D\uDEFB I SAID FUCKING KURWA MAC!! \uD83D\uDE21\uD83D\uDCA2 i lovE POLAND \uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1 I LOVE POLAND \uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1 I HAD A REALLY PRETTY CAR ✨✨\uD83C\uDF1F BABY YOU HAVE ASS SO FAR \uD83C\uDF51\uD83C\uDDF5\uD83C\uDDF1  DONT BE SHY COME WITH US\uD83D\uDE98 WE WILL SHOW AMOROUS THEN I STAY IN POLAND \uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1 HAVE NOT CAR BUT I DONT MIND \uD83E\uDD37\u200D♂  CHICKS IN POLAND ARE SO HOT \uD83D\uDC6F\u200D♀ \uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1 I LOVE KURWA MAC! PPPPPPPPPPPPPOOOOOLLLLLLAND \uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1 KOCHAMY POLSKĘ KOCHAMY POLSKĘ KOCHAMY POLSKĘ KOCHAMY POLSKĘ KOCHAMY POLSKĘ \uD83D\uDC96\uD83D\uDC96\uD83D\uDC96\uD83D\uDC96\uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1\uD83D\uDC96\uD83D\uDC96\uD83D\uDC96\uD83D\uDC96\uD83D\uDC96\uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1";

            channel = event.getChannel();

            event.getChannel().sendMessage(message).queue();

        }
    }
}
