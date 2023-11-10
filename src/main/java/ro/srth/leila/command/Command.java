package ro.srth.leila.command;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class Command extends ListenerAdapter implements EventListener {
    public static boolean register = true; // true by default

    public static List<Permission> permissions = new ArrayList<>();

    public Guild guild;

    public Command(Guild guild){;
        this.guild = guild;
    }
}
