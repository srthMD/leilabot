package ro.srth.leila.commands;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;
import ro.srth.leila.Bot;
import ro.srth.leila.annotations.GuildSpecific;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@SuppressWarnings(value = "unchecked")
public class CmdMan extends ListenerAdapter {

    Reflections reflections = new Reflections("ro.srth");
    Set<Class<? extends Command>> classes = reflections.getSubTypesOf(ro.srth.leila.commands.Command.class);

    int i = 0;

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        ShardManager sman = Bot.getSman();

        // register commands here
        List<CommandData> commandData = new ArrayList<>();

        for (Class<?> clazz: classes) {

            if(clazz.getName().equals("template")){continue;}

            ro.srth.leila.commands.Command.CommandType cmdType;
            String cmdName;
            String cmdDesc;
            List<OptionData> cmdArgs;

            try {
                Object instance = clazz.getDeclaredConstructor().newInstance();
                if(!(boolean) clazz.getField("register").get(instance)){continue;}

                if(i==0){sman.addEventListener(instance);}

                cmdName = (String) clazz.getField("commandName").get(instance);
                cmdDesc = (String) clazz.getField("description").get(instance);
                cmdArgs = (List<OptionData>) clazz.getField("args").get(instance);
                cmdType = (ro.srth.leila.commands.Command.CommandType) clazz.getField("type").get(instance);
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InstantiationException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }

            if (cmdType == ro.srth.leila.commands.Command.CommandType.SLASH){
                if (cmdArgs.size() == 0) {
                    if(clazz.isAnnotationPresent(GuildSpecific.class)){
                        if(event.getGuild().getIdLong() == clazz.getAnnotation(GuildSpecific.class).guildIdLong()){
                            commandData.add(Commands.slash(cmdName, cmdDesc));
                        }
                    } else{
                        commandData.add(Commands.slash(cmdName, cmdDesc));
                    }
                } else {
                    if(clazz.isAnnotationPresent(GuildSpecific.class)) {
                        if (event.getGuild().getIdLong() == clazz.getAnnotation(GuildSpecific.class).guildIdLong()) {
                            SlashCommandData data = Commands.slash(cmdName, cmdDesc);

                            cmdArgs.forEach(data::addOptions);
                            commandData.add(data);
                        }
                    } else {
                        SlashCommandData data = Commands.slash(cmdName, cmdDesc);

                        cmdArgs.forEach(data::addOptions);
                        commandData.add(data);
                    }
                }
            } else{
                if(i==0){
                    try {
                        sman.addEventListener(clazz.getDeclaredConstructor().newInstance());
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                             NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                }
                commandData.add(Commands.context(net.dv8tion.jda.api.interactions.commands.Command.Type.MESSAGE, cmdName));
            }
        }
        event.getGuild().updateCommands().addCommands(commandData).queue();
        i++;
        Bot.log.info("done registering commands for guild: " + event.getGuild().getName());
    }
}
