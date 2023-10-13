package ro.srth.leila.commands.cmds.slash;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.commands.Command;

public class Toggle extends Command{

    private static boolean msgtoggle;
    private static boolean reactiontoggle;
    private static boolean txttoggle;

    public Toggle() {
        super();
        this.commandName = "toggle";
        this.description = "toggles certain listeners";
        this.type = Command.CommandType.SLASH;
        subCmds.add(new SubcommandData("randommessages", "toggles random messages"));
        subCmds.add(new SubcommandData("randomreactions", "toggles random reactions"));
        subCmds.add(new SubcommandData("textreactions", "toggles preset reactions based on message content"));
        this.register = true;

        msgtoggle = true;
        reactiontoggle = true;
        txttoggle = true;
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(this.commandName) && !event.isAcknowledged()) {
            switch (event.getSubcommandName()){
                case ("randommessages"):
                    msgtoggle = !msgtoggle;
                    event.reply("setting random message toggle to " + msgtoggle).queue();
                    break;

                case("randomreactions"):
                    reactiontoggle = !reactiontoggle;
                    event.reply("setting random reaction toggle to " + reactiontoggle).queue();
                    break;

                case("textreactions"):
                    txttoggle = !txttoggle;
                    event.reply("setting text reaction toggle to " + txttoggle).queue();
                    break;

                default:
                    event.reply("something went wrong").setEphemeral(true).queue();
            }
        }
    }

    public static boolean getMessageToggle(){return msgtoggle;}
    public static boolean getReactionToggle(){return reactiontoggle;}
    public static boolean getTextToggle(){return txttoggle;}
}
