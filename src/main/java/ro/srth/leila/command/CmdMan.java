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
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;
import ro.srth.leila.main.Bot;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;
import java.util.stream.Collectors;


//
@SuppressWarnings(value = "unchecked")
public final class CmdMan extends ListenerAdapter {

    static final ExecutorService ex = Executors.newCachedThreadPool(); //default args are ok

    final static Reflections reflections = new Reflections("ro.srth");

    private static final Predicate<Class<? extends Command>> pr = Class -> !Class.isInterface();

    static final Set<Class<? extends Command>> allClasses = reflections.getSubTypesOf(Command.class).stream().filter(pr).collect(Collectors.toSet());

    private static List<CommandData> getData(){
        // register commands here
        List<CommandData> commandData = new ArrayList<>();

        for (Class<? extends Command> clazz : allClasses) {
            try{
                Class.forName(clazz.getName());

                if(clazz.getField("register").get(null) == Boolean.FALSE) continue;

                if(clazz.getSuperclass() == SlashCommand.class){
                    String name = clazz.getSimpleName().toLowerCase();

                    String desc = (String) clazz.getField("description").get(null);

                    List<Permission> perms = (List<Permission>) clazz.getField("permissions").get(null);

                    List<OptionData> cmdArgs = (List<OptionData>) clazz.getField("args").get(null);
                    List<SubcommandData> subCmds = (List<SubcommandData>) clazz.getField("subCmds").get(null);

                    OptionData[] arr = new OptionData[0];
                    SubcommandData[] arr2 = new SubcommandData[0];
                    commandData.add(Commands.slash(name, desc).setDefaultPermissions(DefaultMemberPermissions.enabledFor(perms)).addOptions(cmdArgs.toArray(arr)).addSubcommands(subCmds.toArray(arr2)));

                    //idk why i need to do this but if i dont it fucks everything up
                    perms.clear();
                    cmdArgs.clear();
                    subCmds.clear();
                    desc = "";

                } else if(clazz.getSuperclass() == ContextMenu.class){
                    List<Permission> perms = (List<Permission>) clazz.getField("permissions").get(null);

                    String name = (String) clazz.getField("formalName").get(null);

                    commandData.add(Commands.context(net.dv8tion.jda.api.interactions.commands.Command.Type.MESSAGE, name).setDefaultPermissions(DefaultMemberPermissions.enabledFor(perms)));
                }
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

            Class<? extends Command> clazz = allClasses.stream().filter(aClass -> aClass.getSimpleName().equalsIgnoreCase(name)).findFirst().orElseThrow();

            String method;

            if(event instanceof SlashCommandInteractionEvent){
                method = "runSlashCommand";
            } else{
                method = "runContextMenu";
            }

            try {
                Object instance = clazz.getDeclaredConstructor(Guild.class).newInstance(guild);

                List<Permission> perms = (List<Permission>) clazz.getField("permissions").get(instance);

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


    static List<CommandData> data = getData();
    private static void runHandler(@NotNull GenericGuildEvent event) {
        event.getGuild().updateCommands().addCommands(data).queue();
        Bot.log.info("registering commands for guild " + event.getGuild().getName() + " ID: " + event.getGuild().getIdLong());
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        runHandler(event);
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        runHandler(event);
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
