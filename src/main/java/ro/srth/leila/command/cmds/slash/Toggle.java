package ro.srth.leila.command.cmds.slash;

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

public class Toggle extends LBLocalSlashCommand {

    static {
        description = "toggles certain listeners";
        subCmds.add(new SubcommandData("randommessages", "toggles random messages"));
        subCmds.add(new SubcommandData("randomreactions", "toggles random reactions"));
        subCmds.add(new SubcommandData("textreactions", "toggles preset reactions based on message content"));
    }

    @Local(clazz = GuildBoolean.class)
    public static boolean msgtoggle = true;

    @Local(clazz = GuildBoolean.class)
    public static boolean reactiontoggle = true;

    @Local(clazz = GuildBoolean.class)
    public static boolean txttoggle = true;

    public Toggle(Guild guild) {
        super(guild);
    }

    @Override
    public void runSlashCommand(@NotNull SlashCommandInteractionEvent event) {
        long guildId = event.getGuild().getIdLong();
        switch (event.getSubcommandName()){
            case ("randommessages"):
                boolean t_msgtoggle;
                try {
                    t_msgtoggle = (boolean) GuildReader.get(guildId).getVars().get("msgtoggle").getVar();


                    t_msgtoggle = !t_msgtoggle;
                    event.reply("setting random message status to " + (t_msgtoggle ? "forced" : "not forced")).queue();

                    GuildWriter.write(new GuildBoolean(t_msgtoggle, "msgtoggle", event.getGuild()));
                }catch (UnsuccessfulWriteException | GuildNotFoundException e) {
                    event.reply("something went wrong reading/writing cache, message: " + e.getMessage()).setEphemeral(true).queue();
                }
                break;

            case("randomreactions"):
                boolean t_reactiontoggle;
                try {
                    t_reactiontoggle = (boolean) GuildReader.get(guildId).getVars().get("reactiontoggle").getVar();


                    t_reactiontoggle = !t_reactiontoggle;
                    event.reply("setting random reaction status to " + (t_reactiontoggle ? "forced" : "not forced")).queue();

                    GuildWriter.write(new GuildBoolean(t_reactiontoggle, "reactiontoggle", event.getGuild()));
                }catch (UnsuccessfulWriteException | GuildNotFoundException e) {
                    event.reply("something went wrong reading/writing cache, message: " + e.getMessage()).setEphemeral(true).queue();
                }
                break;

            case("textreactions"):
                boolean t_txttoggle;
                try {
                    t_txttoggle = (boolean) GuildReader.get(guildId).getVars().get("txttoggle").getVar();


                    t_txttoggle = !t_txttoggle;
                    event.reply("setting set text reaction status to " + (t_txttoggle ? "forced" : "not forced")).queue();

                    GuildWriter.write(new GuildBoolean(t_txttoggle, "txttoggle", event.getGuild()));
                }catch (UnsuccessfulWriteException | GuildNotFoundException e) {
                    event.reply("something went wrong reading/writing cache, message: " + e.getMessage()).setEphemeral(true).queue();
                }
                break;

            default:
                event.reply("something went wrong").setEphemeral(true).queue();
        }
    }


    public static boolean getMessageToggle(long id) throws GuildNotFoundException {
        return (boolean) GuildReader.get(id).getVars().get("msgtoggle").getVar();
    }
    public static boolean getReactionToggle(long id) throws GuildNotFoundException {
        return (boolean) GuildReader.get(id).getVars().get("reactiontoggle").getVar();
    }
    public static boolean getTextToggle(long id) throws GuildNotFoundException {
        return (boolean) GuildReader.get(id).getVars().get("txttoggle").getVar();
    }
}
