package ro.srth.leila.commands.cmds.slash;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.main.Bot;
import ro.srth.leila.commands.SlashCommand;
import ro.srth.leila.listener.listeners.GenericMentionHandler;

import java.awt.*;

public class BotInfoSlashCmd extends SlashCommand {

    public BotInfoSlashCmd() {
        super();
        this.commandName = "botinfo";
        this.description = "Sends information about the bot.";
        this.register = true;
    }

    @Override
    public void runSlashCommand(@NotNull SlashCommandInteractionEvent event) {
        Bot.log.info(event.getInteraction().getUser().getName() + " Fired BotInfoSlashCmd");

        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Bot Information", null);

        eb.setColor(Color.white);

        eb.setThumbnail("https://cdn.discordapp.com/attachments/888842500847071303/1055202567891722290/Snapchat-195496909.jpg");

        eb.addField("Github:", "https://github.com/srthMD/leilabot", false);
        eb.addField("Lastest commit:", "https://github.com/srthMD/leilabot/commit/master", false);
        eb.addField("Total amt of Leila pictures: ", String.valueOf(PetPicture.getNumberOfLeilaPictures()), false);
        eb.addField("Total amt of Octavious pictures: ", String.valueOf(PetPicture.getNumberOfOctaviousPictures()), false);
        eb.addField("Total amt of Simon pictures: ", String.valueOf(PetPicture.getNumberOfSimonPictures()), false);
        eb.addField("Total amt of Chucky pictures: ", String.valueOf(PetPicture.getNumberOfChuckyPictures()), false);
        eb.addField("Total Amt of birthday art pictures: ", String.valueOf(GenericMentionHandler.getNumberOfBdayArtPctures()), false);
        eb.addField("Random Messages Toggled: ", String.valueOf(Toggle.getMessageToggle()), false);
        eb.addField("Random Reactions Toggled: ", String.valueOf(Toggle.getReactionToggle()), false);
        eb.addField("Random Text Reactions Toggled: ", String.valueOf(Toggle.getTextToggle()), false);
        eb.setFooter("Written in Java by srth#2668 ", "https://avatars.githubusercontent.com/u/94727593?v=4");

        try {
            event.getInteraction().replyEmbeds(eb.build()).queue();
        } catch (Exception e) {
            Bot.log.error(e.getMessage());
        }
    }
}