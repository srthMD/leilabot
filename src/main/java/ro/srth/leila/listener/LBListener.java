package ro.srth.leila.listener;

import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public abstract class LBListener extends ListenerAdapter implements EventListener {
    public String name;
}
