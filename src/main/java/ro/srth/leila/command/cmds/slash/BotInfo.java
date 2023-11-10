package ro.srth.leila.command.cmds.slash;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.command.SlashCommand;
import ro.srth.leila.exception.GuildNotFoundException;
import ro.srth.leila.listener.listeners.GenericMentionHandler;
import ro.srth.leila.main.Bot;

import java.awt.*;

public class BotInfo extends SlashCommand {

    static {
        description = "Sends information about the bot.";
    }

    public BotInfo(Guild guild) {
        super(guild);
    }


    @Override
    public void runSlashCommand(@NotNull SlashCommandInteractionEvent event) {
        long guildId = event.getGuild().getIdLong();

        String tt, rt, mt;

        try {
            tt = String.valueOf(Toggle.getTextToggle(guildId));
        } catch (GuildNotFoundException e) {
            tt = "N/A";
        }
        try {
            rt = String.valueOf(Toggle.getReactionToggle(guildId));
        } catch (GuildNotFoundException e) {
            rt = "N/A";
        }
        try {
            mt = String.valueOf(Toggle.getMessageToggle(guildId));
        } catch (GuildNotFoundException e) {
            mt = "N/A";
        }


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
        eb.addField("Random Messages Toggled: ", mt, false);
        eb.addField("Random Reactions Toggled: ", rt, false);
        eb.addField("Random Text Reactions Toggled: ", tt, false);
        eb.setFooter("Written in Java by srth ", "https://avatars.githubusercontent.com/u/94727593?v=4");

        try {
            event.getInteraction().replyEmbeds(eb.build()).queue();
        } catch (IllegalStateException e) {
            Bot.log.error(e.getMessage());
        }
    }
}