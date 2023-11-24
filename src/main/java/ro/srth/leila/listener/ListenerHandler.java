package ro.srth.leila.listener;

import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;
import ro.srth.leila.main.Bot;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public final class ListenerHandler extends ListenerAdapter {
    @Override
    public void onReady(@NotNull ReadyEvent event) {

        Reflections reflections = new Reflections("ro.srth");
        Set<Class<? extends LBListener>> classes =  reflections.getSubTypesOf(LBListener.class);

        ShardManager sman = Bot.getSman();

        for (Class<?> clazz: classes) {
            try {
                sman.addEventListener(clazz.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        Bot.log.info("done registering listeners");
    }
}
