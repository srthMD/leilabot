package ro.srth.leila.guild;

import ro.srth.leila.exception.GuildNotFoundException;
import ro.srth.leila.exception.UnsuccessfulWriteException;
import ro.srth.leila.guild.vars.GuildVariable;
import ro.srth.leila.main.Bot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Static utility class for writing guild configurations to disk and cache.
 */
public class GuildWriter {
    private static final String EXTENSION = ".lbgcfg";

    private static final String PATH = "C:\\Users\\SRTH_\\Desktop\\leilabot\\guild\\";

    /**
     * Writes or overwrites a guild configuration to disk.
     * If a guild configuration file doesn't exist, it will create a new one.
     * @param vars The guild configuration to write.
     * @return True if success.
     */
    public static boolean writeToDisk(GuildConfiguration vars){
        File file;
        if(GuildReader.exists(vars.getGuildId())){
            file = GuildReader.getRaw(vars.getGuildId());
            writeDisk(file, vars);
        }else{
            file = new File(PATH + vars.getGuildId() + EXTENSION);
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            writeDisk(file, GuildConfiguration.getDefaultGuildConfiguration());
        }
        return true;
    }

    /**
     * Writes or overwrites a guild configuration to cache
     * @param vars The guild configuration to write.
     * @throws UnsuccessfulWriteException when writing to disk is not successful.
     */
    public static boolean writeToCache(GuildConfiguration vars) throws UnsuccessfulWriteException {
        boolean suc = writeToDisk(vars);

        if(!suc){
            throw new UnsuccessfulWriteException("Unsucessful disk write.");
        }

        var cache = Bot.instance().getGuildCache();

        long id = vars.getGuildId();

        try {
            if(cache.get(id, () -> GuildConfiguration.NULLCFG) == GuildConfiguration.NULLCFG){
                cache.put(id, vars);
            } else{
                cache.invalidate(id);
                cache.put(id, vars);
            }
        } catch (ExecutionException e) {
            Bot.log.error(e.getMessage());
            return false;
        }

        return true;
    }

    public static void write(GuildConfiguration vars) throws UnsuccessfulWriteException {
        boolean s2 = writeToCache(vars);

        if(!(s2)){
            throw new UnsuccessfulWriteException("Unsuccessful write() attempt.");
        }
    }

    public static void write(GuildVariable<?> var, long id) throws UnsuccessfulWriteException {
        boolean s2 = writeCache(var, id);

        if(!s2){
            throw new UnsuccessfulWriteException("Unsuccessful write() attempt.");
        }
    }

    private static boolean writeDisk(File file, GuildConfiguration vars){
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            return false;
        }

        vars.getVars().forEach((name, var) -> {
            try {
                bw.write(name + "=" + var + "#");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        try {
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    //TODO: currently just overwrites bc im too lazy
    private static boolean writeDisk(File file, GuildVariable<?> arg, long id){
        BufferedWriter bw;

        try {
            bw = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            return false;
        }

        GuildConfiguration cfg;

        try {
            cfg = GuildReader.get(id);
        } catch (GuildNotFoundException e) {
            return false;
        }
        cfg.getVars().put(arg.getName(), arg);

        writeDisk(file, cfg);

        return true;
    }

    private static boolean writeCache(GuildVariable<?> arg, long id){
        boolean suc = writeDisk(GuildReader.getRaw(id), arg, id);

        if(!suc){return suc;}

        var cache = Bot.instance().getGuildCache();

        var cfg = cache.getIfPresent(id);

        if(cfg.getVars().containsKey(arg.getName())){
            cfg.getVars().put(arg.getName(), arg);
        } else{
            return false;
        }

        return true;
    }
}
