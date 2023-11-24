package ro.srth.leila.command;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;
import ro.srth.leila.main.Bot;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;
import java.util.stream.Collectors;



@SuppressWarnings(value = "unchecked")
public final class CmdMan extends ListenerAdapter {

    static final ExecutorService ex = Executors.newCachedThreadPool(); //default args are ok

    final static Reflections reflections = new Reflections("ro.srth");

    private static final Predicate<Class<? extends LBCommand>> pr = Class -> !Class.isInterface();

    static final Set<Class<? extends LBCommand>> allClasses = reflections.getSubTypesOf(LBCommand.class).stream().filter(pr).collect(Collectors.toSet());

    static final Map<String, Class<? extends LBCommand>> classMap = new HashMap<>();


    private static List<LBCommandData> getData(){
        // register commands here
        List<LBCommandData> commandData = new ArrayList<>();

        for (Class<? extends LBCommand> clazz : allClasses) {
            try{
                Class.forName(clazz.getName());

                if(clazz.getField("register").get(null) == Boolean.FALSE) continue;

                List<Permission> perms = (List<Permission>) clazz.getField("permissions").get(null);

                if(clazz.getSuperclass() == LBSlashCommand.class || clazz.getSuperclass() == LBLocalSlashCommand.class){
                    String name = clazz.getSimpleName().toLowerCase();

                    String desc = (String) clazz.getField("description").get(null);

                    List<OptionData> cmdArgs = (List<OptionData>) clazz.getField("args").get(null);
                    List<SubcommandData> subCmds = (List<SubcommandData>) clazz.getField("subCmds").get(null);

                    OptionData[] arr = new OptionData[0];
                    SubcommandData[] arr2 = new SubcommandData[0];

                    commandData.add((LBCommandData) LBCommandData.slash(name, desc, (Class<? extends LBSlashCommand>) clazz).setDefaultPermissions(DefaultMemberPermissions.enabledFor(perms)).addOptions(cmdArgs.toArray(arr)).addSubcommands(subCmds.toArray(arr2)));

                    classMap.put(name, clazz);

                    //idk why i need to do this but if i dont it fucks everything up, something something static im too retarded to care
                    cmdArgs.clear();
                    subCmds.clear();
                    desc = "";

                } else if(clazz.getSuperclass() == LBContextMenu.class){
                    String name = (String) clazz.getField("formalName").get(null);

                    commandData.add((LBCommandData) LBCommandData.context(Command.Type.MESSAGE, name, clazz).setDefaultPermissions(DefaultMemberPermissions.enabledFor(perms)));
                    classMap.put(clazz.getSimpleName().toLowerCase(), clazz);
                }
                perms.clear();
            } catch (NoSuchFieldException | IllegalAccessException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return commandData;
    }

    private static <T extends GenericCommandInteractionEvent> void runInteraction(@NotNull T event) {
        if(!event.isAcknowledged()){
            String name = event.getName();
            Guild guild = event.getGuild();

            //idk why i decided on using allclasses and streaming the set to get the class, my brain works differently depending on the day
            Class<? extends LBCommand> clazz = classMap.get(name);

            String method;

            if(event instanceof SlashCommandInteractionEvent){
                method = "runSlashCommand";
            } else{
                method = "runContextMenu";
            }

            try {
                Object instance;
                if (clazz.getSuperclass() == LBLocalSlashCommand.class) {
                    instance = clazz.getDeclaredConstructor(Guild.class).newInstance(guild);
                } else{
                    instance = clazz.getDeclaredConstructor().newInstance();
                }

                List<Permission> perms = (List<Permission>) clazz.getField("permissions").get(null);

                Permission[] arr = new Permission[0];

                //double check permissions incase discord is stupi
                if(Objects.requireNonNull(event.getMember()).hasPermission(perms.toArray(arr))){
                    Method m = clazz.getDeclaredMethod(method, SlashCommandInteractionEvent.class);

                    ex.submit(() -> m.invoke(instance, event));

                    Bot.log.info(event.getUser().getName() + " fired " + event.getFullCommandName());
                } else{
                    event.reply("you do not have permissions to run this command").setEphemeral(true).queue();
                }
            } catch (NoSuchMethodException | IllegalAccessException | NoSuchFieldException | InstantiationException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }


    static List<LBCommandData> data = getData();
    private static void registerCommands(@NotNull GenericGuildEvent event) {
        event.getGuild().updateCommands().addCommands(data).queue();
        Bot.log.info("registering commands for guild " + event.getGuild().getName() + " ID: " + event.getGuild().getIdLong());
    }


    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        registerCommands(event);
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        registerCommands(event);
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        runInteraction(event);
    }

    @Override
    public void onMessageContextInteraction(@NotNull MessageContextInteractionEvent event) {
        runInteraction(event);
    }
}
