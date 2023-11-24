package ro.srth.leila.command;

import net.dv8tion.jda.api.entities.Guild;

//currently does not serve a purpose
public abstract class LBLocalSlashCommand extends LBSlashCommand{

    private final Guild guild;

    public LBLocalSlashCommand(Guild guild) {
        this.guild = guild;
    }
}
