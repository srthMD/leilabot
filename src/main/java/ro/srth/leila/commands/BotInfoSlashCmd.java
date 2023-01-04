package ro.srth.leila.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ro.srth.leila.Bot;

import java.awt.*;

public class BotInfoSlashCmd extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("botinfo")){
            Bot.log.info(event.getInteraction().getUser().getAsTag() + " Fired BotInfoSlashCmd");
            EmbedBuilder eb = new EmbedBuilder();

            eb.setTitle("Bot Information", null);

            eb.setColor(Color.white);

            eb.setThumbnail("https://cdn.discordapp.com/attachments/888842500847071303/1055202567891722290/Snapchat-195496909.jpg");

            eb.addField("Github:", "https://github.com/srthMD/leilabot", false);
            eb.addField("Lastest commit:", "https://github.com/srthMD/leilabot/commit/master", false);
            eb.addField("Total Amt Of Leila Pictures:", String.valueOf(LeilaPicSlashCmd.getNumberOfLeilaPictures()), false);
            eb.addField("Total Amt Of Octavious Pictures:", String.valueOf(OctaviousPicSlashCmd.getNumberOfOctaviousPictures()), false);
            eb.addField("Total Amt Of Simon Pictures:", String.valueOf(SimonPicCmd.getNumberOfSimonPictures()), false);
            eb.addField("Total Amt Of Chucky Pictures:", String.valueOf(ChuckyPicCmd.getNumberOfChuckyPictures()), false);
            eb.addField("Random Messages Toggled:", String.valueOf(ToggleRandomMsg.getToggledStatus()), false);
            eb.setFooter("Written in Java by srth#2668",  "https://avatars.githubusercontent.com/u/94727593?v=4");

            try{event.getInteraction().replyEmbeds(eb.build()).queue();} catch (Exception e) {Bot.log.warning(e.toString());}

        }
    }
}
