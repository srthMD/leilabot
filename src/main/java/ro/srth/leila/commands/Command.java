package ro.srth.leila.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class Command extends ListenerAdapter implements EventListener {
    
    public String commandName;

    public boolean register = true; // true by default

    public final List<Permission> permissions;

    public Guild guild;

    public Command(Guild guild){
        permissions = new ArrayList<>();
        this.guild = guild;
    }

    public Command(){
        permissions = new ArrayList<>();
    }
}
