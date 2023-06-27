package ro.srth.leila.commands;

import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public abstract class Command extends ListenerAdapter implements EventListener {

    public enum CommandType{
        SLASH(0),
        CONTEXT_MENU(1);

        private final int id;

        public int value() {
            return this.id;
        }
        CommandType(int id)
        {
            this.id = id;
        }
    }
    public String commandName;
    public String description;

    public CommandType type;

    public List<OptionData> args;
    public boolean register;

}