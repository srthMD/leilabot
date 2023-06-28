package ro.srth.leila.commands.cmds;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.Bot;
import ro.srth.leila.commands.Command;
import ro.srth.leila.listener.listeners.GenericMentionHandler;

import java.awt.*;
import java.util.ArrayList;

public class BotInfoSlashCmd extends Command {

    public BotInfoSlashCmd() {
        this.commandName = "botinfo";
        this.description = "Sends information about the bot.";
        this.type = Command.CommandType.SLASH;
        this.args = new ArrayList<OptionData>();
        this.register = true;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals(this.commandName) && !event.isAcknowledged()) {
            Bot.log.info(event.getInteraction().getUser().getName() + " Fired BotInfoSlashCmd");

            EmbedBuilder eb = new EmbedBuilder();

            eb.setTitle("Bot Information", null);

            eb.setColor(Color.white);

            eb.setThumbnail("https://cdn.discordapp.com/attachments/888842500847071303/1055202567891722290/Snapchat-195496909.jpg");

            eb.addField("Github:", "https://github.com/srthMD/leilabot", false);
            eb.addField("Lastest commit:", "https://github.com/srthMD/leilabot/commit/master", false);
            eb.addField("Total amt of Leila pictures: ", String.valueOf(LeilaPicSlashCmd.getNumberOfLeilaPictures()), false);
            eb.addField("Total amt of Octavious pictures: ", String.valueOf(OctaviousPicSlashCmd.getNumberOfOctaviousPictures()), false);
            eb.addField("Total amt of Simon pictures: ", String.valueOf(SimonPicCmd.getNumberOfSimonPictures()), false);
            eb.addField("Total amt of Chucky pictures: ", String.valueOf(ChuckyPicCmd.getNumberOfChuckyPictures()), false);
            eb.addField("Total Amt of birthday art pictures: ", String.valueOf(GenericMentionHandler.getNumberOfBdayArtPctures()), false);
            eb.addField("Random Messages Toggled: ", String.valueOf(ToggleRandomMsg.getToggledStatus()), false);
            eb.addField("Random Reactions Toggled: ", String.valueOf(ToggleRandomReaction.getToggledStatus()), false);
            eb.addField("Random Text Reactions Toggled: ", String.valueOf(ToggleTextReactions.getToggledStatus()), false);
            eb.setFooter("Written in Java by srth#2668 ", "https://avatars.githubusercontent.com/u/94727593?v=4");

            try {
                event.getInteraction().replyEmbeds(eb.build()).queue();
            } catch (Exception e) {
                Bot.log.warn(e.toString());
            }
        }
    }
}