package ro.srth.leila.command;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class LBCommand extends ListenerAdapter implements EventListener {
    public static boolean register = true; // true by default

    public static final List<Permission> permissions = new ArrayList<>();
}
