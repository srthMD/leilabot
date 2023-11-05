package ro.srth.leila.commands.cmds.slash;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.commands.SlashCommand;
import ro.srth.leila.main.Bot;

import java.awt.*;

public class Cache extends SlashCommand {

    public Cache(){
        this.commandName = "guildcache";
        this.description = "Shows the cached variables of the current guild.";
        permissions.add(Permission.ADMINISTRATOR);
    }
    @Override
    public void runSlashCommand(@NotNull SlashCommandInteractionEvent event) {
        var cache = Bot.instance().getGuildCache();

        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Current Cache", null);

        eb.setColor(Color.white);

        eb.setThumbnail("https://cdn.discordapp.com/attachments/888842500847071303/1055202567891722290/Snapchat-195496909.jpg");
        eb.setFooter("Written in Java by srth ", "https://avatars.githubusercontent.com/u/94727593?v=4");

        cache.getIfPresent(event.getGuild().getIdLong()).getVars().forEach((name, var) -> {
            eb.addField(var.getClass().getSimpleName() + " " + name +  ":", var.toString(), false);
        });

        try {
            event.getInteraction().replyEmbeds(eb.build()).queue();
        } catch (IllegalStateException e) {
            Bot.log.error(e.getMessage());
        }
    }
}
