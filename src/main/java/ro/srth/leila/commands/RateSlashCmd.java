package ro.srth.leila.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import ro.srth.leila.Bot;
import ro.srth.leila.annotations.NeedsRevamp;

import java.util.Random;

@NeedsRevamp(reason = "unfunny messages")
public class RateSlashCmd extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if(command.equals("rate")){
            OptionMapping usertorate = event.getOption("user_to_rate");
            Bot.log.info(event.getInteraction().getUser().getAsTag() + " fired RateSlashCmd");

            Random random = new Random();
            int rating = random.nextInt(0, 100);
            Bot.log.info(String.valueOf(rating));
            String[] messages = {
                    usertorate.getAsUser().getName() + " is soooo haram " + rating + "/100 :thumbsdown::thumbsdown::thumbsdown::thumbsdown:",
                    usertorate.getAsUser().getName() + " has Problem In Bren " + rating + "/100",
                    usertorate.getAsUser().getName() + " is ok i don see proble in bren " + rating + "/100",
                    usertorate.getAsUser().getName() + " is very coole like simone cat " + rating + "/100 <:simon:1033343266227232768><:simon:1033343266227232768><:simon:1033343266227232768><:simon:1033343266227232768><:simon:1033343266227232768>",
                    usertorate.getAsUser().getName() + " very coolest man " + rating + "/100 :thumbsup::thumbsup::thumbsup:"};
            if(rating <= 19){
                event.reply(messages[0]).queue();
                Bot.log.info("message 0");
            } else if(rating >= 20 && rating <= 39){
                event.reply(messages[1]).queue();
                Bot.log.info("message 1");
            } else if(rating >= 40 && rating <=59){
                event.reply(messages[2]).queue();
                Bot.log.info("message 2");
            } else if(rating >= 60 && rating <= 79){
                event.reply(messages[3]).queue();
                Bot.log.info("message 3");
            } else if(rating >= 80 && rating <= 100){
                event.reply(messages[4]).queue();
                Bot.log.info("message 4");
            }
        }
    }
}
