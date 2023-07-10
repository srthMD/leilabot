package ro.srth.leila.commands.cmds.slash;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.commands.Command;

public class Force extends Command{

    private static boolean msgforce;
    private static boolean reactionforce;

    public Force() {
        super();
        this.commandName = "force";
        this.description = "forces certain listeners";
        this.type = Command.CommandType.SLASH;
        subCmds.add(new SubcommandData("randommessages", "forces random messages"));
        subCmds.add(new SubcommandData("randomreactions", "forces random reactions"));
        this.register = true;

        msgforce = false;
        reactionforce = false;
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(this.commandName) && !event.isAcknowledged()) {
            switch (event.getSubcommandName()){
                case ("randommessages"):
                    msgforce = msgforce ? false: true;
                    event.reply("setting random message status to " + (msgforce ? "forced" : "not forced")).queue();
                    break;

                case("randomreactions"):
                    reactionforce = reactionforce ? false: true;
                    event.reply("setting random reaction status to " + (reactionforce ? "forced" : "not forced")).queue();
                    break;

                default:
                    event.reply("something went wrong").setEphemeral(true).queue();
            }
        }
    }

    public static boolean getMessageForced(){return msgforce;}
    public static boolean getReactionForced(){return reactionforce;}
}
