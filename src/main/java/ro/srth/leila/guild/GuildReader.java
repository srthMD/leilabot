package ro.srth.leila.guild;

import net.dv8tion.jda.api.entities.Guild;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.exception.GuildNotFoundException;
import ro.srth.leila.exception.UnsucessfulReadException;
import ro.srth.leila.guild.vars.AbstractGuildVariableImpl;
import ro.srth.leila.main.Bot;
import ro.srth.leila.main.Config;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;

/**
 * Static utility class for reading guild configurations from disk and cache.
 */

@SuppressWarnings(value = "unchecked")
public class GuildReader {

    private static final String EXTENSION = ".lbgcfg";

    private static final String PATH = Config.ROOT + "\\guild\\";


    /**
     * Reads a guild configuration from disk.
     * @param guildId The id of the guild.
     * @return The GuildConfiguration of the guild.
     */

    public static GuildConfiguration getFromDisk(long guildId) throws UnsucessfulReadException {
        if(!exists(guildId)){
            return GuildConfiguration.NULLCFG;
        }

        List<String> strs = getStrings(guildId);

        GuildConfiguration gc = new GuildConfiguration(guildId);

        strs.forEach((str) -> {
            try {
                String[] split = str.split(";;");
                Class<? extends AbstractGuildVariableImpl<?>> clazz = (Class<? extends AbstractGuildVariableImpl<?>>) Class.forName("ro.srth.leila.guild.vars." + split[0]);

                String[] varSplit = split[1].split("=");

                gc.getVars().put(varSplit[0], clazz.getDeclaredConstructor(String.class, String.class, Guild.class).newInstance(varSplit[1], varSplit[0], Bot.getSman().getGuildById(guildId)));

            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });

        return gc;
    }

    /**
     * Reads a guild configuration from bot cache.
     * @param guildId The id of the guild.
     * @return The GuildConfiguration of the guild.
     */

    public static GuildConfiguration get(long guildId) throws GuildNotFoundException {
        var cache = Bot.instance().getGuildCache();

        if(exists(guildId)){
            return cache.getIfPresent(guildId);
        }else{
            throw new GuildNotFoundException("Id " + guildId + " not found");
        }
    }

    /**
     * Checks if the guild has a config written to disk
     * @param guildId The id of the guild.
     * @return True if it exists.
     */

    public static boolean exists(long guildId){
        File f = new File(PATH + guildId + EXTENSION);
        return f.isFile();
    }

    /**
     * Checks if the guild has a config written to cache.
     * @param guildId The id of the guild.
     * @return True if it exists.
     */

    public static boolean existsInCache(long guildId){return Objects.isNull(Bot.instance().getGuildCache().getIfPresent(guildId));}

    /**
     * Returns a file holding the guild configuration.
     * @param guildId The id of the guild.
     * @return The file containing the guild configuration.
     */

    public static File getRaw(long guildId){return new File(PATH + guildId + EXTENSION);}


    //extracted
    @NotNull
    private static List<String> getStrings(long guildId) throws UnsucessfulReadException {
        File cfg = new File(PATH + guildId + EXTENSION);
        FileInputStream fis;
        try {
            fis = new FileInputStream(cfg);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        List<String> strs;

        try {
            strs = List.of(br.readLine().split("#"));
        } catch (IOException e) {
            throw new UnsucessfulReadException("Failed to read " + cfg.getName());
        }
        return strs;
    }

}
