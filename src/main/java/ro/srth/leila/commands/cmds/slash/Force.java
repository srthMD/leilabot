package ro.srth.leila.commands.cmds.slash;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.commands.SlashCommand;

public class Force extends SlashCommand {

    private static boolean msgforce;
    private static boolean reactionforce;

    public Force() {
        super();
        this.commandName = "force";
        this.description = "forces certain listeners";
        subCmds.add(new SubcommandData("randommessages", "forces random messages"));
        subCmds.add(new SubcommandData("randomreactions", "forces random reactions"));
        permissions.add(Permission.MESSAGE_MANAGE);
        this.register = true;

        msgforce = false;
        reactionforce = false;
    }

    @Override
    public void runSlashCommand(@NotNull SlashCommandInteractionEvent event) {
        switch (event.getSubcommandName()){
            case ("randommessages"):
                msgforce = !msgforce;
                event.reply("setting random message status to " + (msgforce ? "forced" : "not forced")).queue();
                break;

            case("randomreactions"):
                reactionforce = !reactionforce;
                event.reply("setting random reaction status to " + (reactionforce ? "forced" : "not forced")).queue();
                break;

            default:
                event.reply("something went wrong").setEphemeral(true).queue();
        }
    }

    public static boolean getMessageForced(){return msgforce;}
    public static boolean getReactionForced(){return reactionforce;}
}
