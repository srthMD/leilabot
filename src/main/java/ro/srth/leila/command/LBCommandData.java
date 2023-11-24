package ro.srth.leila.command;

import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;
import org.jetbrains.annotations.NotNull;

public class LBCommandData extends CommandDataImpl implements CommandData {

    private final Class<? extends LBCommand> backendClass;

    public LBCommandData(@NotNull String name, @NotNull String description, Class<? extends LBSlashCommand> backendClass) {
        super(name, description);
        this.backendClass = backendClass;
    }

    public LBCommandData(@NotNull Command.Type type, @NotNull String name, Class<? extends LBCommand> backendClass) {
        super(type, name);
        this.backendClass = backendClass;
    }

    public static LBCommandData slash(@NotNull String name, @NotNull String description, Class<? extends LBSlashCommand> backendClass){
        return new LBCommandData(name, description, backendClass);
    }

    public static LBCommandData context(@NotNull Command.Type type, @NotNull String name, Class<? extends LBCommand> backendClass){
        return new LBCommandData(type, name, backendClass);
    }

    public Class<? extends LBCommand> getBackendClass() {
        return backendClass;
    }
}
