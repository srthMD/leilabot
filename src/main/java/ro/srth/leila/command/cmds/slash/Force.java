package ro.srth.leila.command.cmds.slash;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.annotations.Local;
import ro.srth.leila.command.LBLocalSlashCommand;
import ro.srth.leila.exception.GuildNotFoundException;
import ro.srth.leila.exception.UnsuccessfulWriteException;
import ro.srth.leila.guild.GuildReader;
import ro.srth.leila.guild.GuildWriter;
import ro.srth.leila.guild.vars.GuildBoolean;

public class Force extends LBLocalSlashCommand {

    static {
        description = "forces certain listeners";
        subCmds.add(new SubcommandData("randommessages", "forces random messages"));
        subCmds.add(new SubcommandData("randomreactions", "forces random reactions"));
        permissions.add(Permission.MESSAGE_MANAGE);
    }

    @Local(clazz = GuildBoolean.class)
    public static boolean msgforce = false;

    @Local(clazz = GuildBoolean.class)
    public static boolean reactionforce = false;

    public Force(Guild guild) {
        super(guild);
    }


    @Override
    public void runSlashCommand(@NotNull SlashCommandInteractionEvent event) {

        long guildId = event.getGuild().getIdLong();

        switch (event.getSubcommandName()){
            case ("randommessages"):

                boolean t_msgforce;

                try {
                    t_msgforce = (boolean) GuildReader.get(guildId).getVars().get("msgforce").getVar();


                    t_msgforce = !t_msgforce;
                    event.reply("setting random message status to " + (t_msgforce ? "forced" : "not forced")).queue();

                    GuildWriter.write(new GuildBoolean(t_msgforce, "msgforce", event.getGuild()));
                }catch (UnsuccessfulWriteException | GuildNotFoundException e) {
                    event.reply("something went wrong reading/writing cache, message: " + e.getMessage()).setEphemeral(true).queue();
                }
                break;

            case("randomreactions"):
                boolean t_reactionforce;

                try {
                    t_reactionforce = (boolean) GuildReader.get(guildId).getVars().get("reactionforce").getVar();


                    t_reactionforce = !t_reactionforce;
                    event.reply("setting random reaction status to " + (t_reactionforce ? "forced" : "not forced")).queue();

                    GuildWriter.write(new GuildBoolean(t_reactionforce, "reactionforce", event.getGuild()));
                }catch (UnsuccessfulWriteException | GuildNotFoundException e) {
                    event.reply("something went wrong reading/writing cache, message: " + e.getMessage()).setEphemeral(true).queue();
                }
                break;

            default:
                event.reply("something went wrong").setEphemeral(true).queue();
        }
    }

    public static boolean getMessageForced(long id) throws GuildNotFoundException {
        return (boolean) GuildReader.get(id).getVars().get("msgforce").getVar();
    }
    public static boolean getReactionForced(long id) throws GuildNotFoundException {
        return (boolean) GuildReader.get(id).getVars().get("reactionforce").getVar();
    }
}
