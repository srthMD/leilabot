package ro.srth.leila.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;
import ro.srth.leila.Bot;
import ro.srth.leila.annotations.GuildSpecific;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@SuppressWarnings(value = "unchecked")
public class CmdMan extends ListenerAdapter {

    static ExecutorService ex = Executors.newCachedThreadPool();

    final static Reflections reflections = new Reflections("ro.srth");

    final static Set<Class<? extends SlashCommand>> slashCommandClasses = reflections.getSubTypesOf(SlashCommand.class);
    final static Set<Class<? extends ContextMenu>> ctxMenuClasses = reflections.getSubTypesOf(ContextMenu.class);

    static Map<String, Class<? extends SlashCommand>> slashCommandMap = new HashMap<>();
    static Map<String, Class<? extends ContextMenu>> ctxMenuMap = new HashMap<>();

    private static final Predicate<Class<? extends Command>> pr = Class -> !Class.isInterface();

    static final Set<Class<? extends Command>> allClasses = reflections.getSubTypesOf(Command.class).stream().filter(pr).collect(Collectors.toSet());


    public static void initMaps(){
        allClasses.removeAll(Set.of(ContextMenu.class, SlashCommand.class));

        ex.submit(() -> slashCommandClasses.forEach(clazz -> {
            try {
                Object instance = clazz.getDeclaredConstructor().newInstance();
                String name = (String) clazz.getField("commandName").get(instance);
                slashCommandMap.put(name, clazz);
            } catch (IllegalAccessException | NoSuchFieldException | NoSuchMethodException |
                     InstantiationException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }));

        ex.submit(() -> ctxMenuClasses.forEach(clazz -> {
            try {
                Object instance = clazz.getDeclaredConstructor().newInstance();
                String name = (String) clazz.getField("commandName").get(instance);
                ctxMenuMap.put(name, clazz);
            } catch (IllegalAccessException | NoSuchFieldException | InvocationTargetException |
                     InstantiationException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {

        // register commands here
        List<CommandData> commandData = new ArrayList<>();

        for (Class<? extends Command> clazz : allClasses) {
            boolean isGuildSpecific = clazz.isAnnotationPresent(GuildSpecific.class);

            try{
                Object instance = clazz.getDeclaredConstructor().newInstance();

                if(clazz.getField("register").get(instance) == Boolean.FALSE) continue;

                if(isGuildSpecific){
                    if(!(event.getGuild().getIdLong() == clazz.getAnnotation(GuildSpecific.class).guildIdLong())) continue;
                }

                String name = (String) clazz.getField("commandName").get(instance);

                if(clazz.getSuperclass() == SlashCommand.class){
                    String desc = (String) clazz.getField("description").get(instance);

                    List<Permission> perms = (List<Permission>) clazz.getField("permissions").get(instance);

                    List<OptionData> cmdArgs = (List<OptionData>) clazz.getField("args").get(instance);
                    List<SubcommandData> subCmds = (List<SubcommandData>) clazz.getField("subCmds").get(instance);

                    OptionData[] arr = new OptionData[0];
                    SubcommandData[] arr2 = new SubcommandData[0];
                    commandData.add(Commands.slash(name, desc).setDefaultPermissions(DefaultMemberPermissions.enabledFor(perms)).addOptions(cmdArgs.toArray(arr)).addSubcommands(subCmds.toArray(arr2)));

                } else if(clazz.getSuperclass() == ContextMenu.class){
                    List<Permission> perms = (List<Permission>) clazz.getField("permissions").get(instance);

                    commandData.add(Commands.context(net.dv8tion.jda.api.interactions.commands.Command.Type.MESSAGE, name).setDefaultPermissions(DefaultMemberPermissions.enabledFor(perms)));
                }
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InstantiationException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        event.getGuild().updateCommands().addCommands(commandData).queue();

        Bot.log.info("done registering commands for guild: " + event.getGuild().getName());
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(!event.isAcknowledged()){
            String name = event.getName();

            Class<? extends SlashCommand> clazz = slashCommandMap.get(name);

            try {
                Object instance = clazz.getDeclaredConstructor().newInstance();

                List<Permission> perms = (List<Permission>) clazz.getField("permissions").get(instance);

                Permission[] arr = new Permission[0];

                //double check permissions incase discord is stupi
                if(Objects.requireNonNull(event.getMember()).hasPermission(perms.toArray(arr))){
                    Method m = clazz.getDeclaredMethod("runSlashCommand", SlashCommandInteractionEvent.class);

                    ex.submit(() -> m.invoke(instance, event));
                } else{
                    event.reply("you do not have permissions to run this command").setEphemeral(true).queue();
                }


            } catch (NoSuchMethodException | IllegalAccessException | NoSuchFieldException | InstantiationException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onMessageContextInteraction(@NotNull MessageContextInteractionEvent event) {
        if(!event.isAcknowledged()){
            String name = event.getCommandString();

            Class<? extends ContextMenu> clazz = ctxMenuMap.get(name);

            try {

                Object instance = clazz.getDeclaredConstructor().newInstance();

                List<Permission> perms = (List<Permission>) clazz.getField("permissions").get(instance);

                Permission[] arr = new Permission[0];

                if(Objects.requireNonNull(event.getMember()).hasPermission(perms.toArray(arr))){
                    Method m = clazz.getDeclaredMethod("runContextMenu", MessageContextInteractionEvent.class);


                    ex.submit(() -> m.invoke(instance, event));

                } else{
                    event.reply("you do not have permissions to run this command").setEphemeral(true).queue();
                }


            } catch (NoSuchMethodException | IllegalAccessException | NoSuchFieldException | InstantiationException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
