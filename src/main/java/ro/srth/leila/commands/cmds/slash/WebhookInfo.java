package ro.srth.leila.commands.cmds.slash;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.annotations.GuildSpecific;
import ro.srth.leila.commands.Command;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@GuildSpecific(guildIdLong = 696053797755027537L)
public class WebhookInfo extends Command {

    public WebhookInfo() {
        super();
        this.commandName = "webhookinfo";
        this.description = "Sends an embed with details about the webhook";
        this.type = CommandType.SLASH;
        args.add(new OptionData(OptionType.BOOLEAN, "withlink", "Sends the message with the link, message will be deleted after 10 seconds.", false));
        this.register = true;
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(this.commandName) && !event.isAcknowledged()) {
            boolean withLink = event.getOption("withlink", false, OptionMapping::getAsBoolean);

            BufferedReader namereader;
            BufferedReader pfpreader;
            BufferedReader webhookreader;

            try {
                namereader = new BufferedReader(new FileReader("C:\\Users\\SRTH_\\Desktop\\leilabot\\name.txt"));
                pfpreader = new BufferedReader(new FileReader("C:\\Users\\SRTH_\\Desktop\\leilabot\\pfp.txt"));
                webhookreader = new BufferedReader(new FileReader("C:\\Users\\SRTH_\\Desktop\\leilabot\\webhook.txt"));

            } catch (FileNotFoundException e) {
                event.reply("something went wrong on execution").setEphemeral(true).queue();

                throw new RuntimeException(e);
            }

            EmbedBuilder eb = new EmbedBuilder();

            try {
                eb.setColor(Color.white);
                eb.setThumbnail(pfpreader.readLine());
                eb.addField("Name: ", namereader.readLine(), false);
                eb.setTitle(withLink ? "Webhook Info, **Will be deleted in 10 seconds**" : "Webhook Info");
                if(withLink){
                    eb.addField("Link: ", "|| " + webhookreader.readLine() + " ||", false);
                }
                eb.setFooter("Written in Java by srth#2668 ", "https://avatars.githubusercontent.com/u/94727593?v=4");
                event.getInteraction().replyEmbeds(eb.build()).queue();

                if(withLink){
                    Thread.sleep(10000);
                    event.getInteraction().getHook().deleteOriginal().queue();
                }

            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}