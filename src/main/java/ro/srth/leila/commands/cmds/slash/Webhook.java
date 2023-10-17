package ro.srth.leila.commands.cmds.slash;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.main.Bot;
import ro.srth.leila.annotations.GuildSpecific;
import ro.srth.leila.commands.SlashCommand;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@GuildSpecific(guildIdLong = 696053797755027537L)
public class Webhook extends SlashCommand {

    private static boolean active;
    public Webhook() {
        super();
        this.commandName = "webhook";
        this.description = "SlashCommand for webhook functionality";
        subCmds.add(new SubcommandData("info", "Shows set info about the webhook").addOption(OptionType.BOOLEAN, "withlink", "Option to include the webhook link, (will delete message after 10 seconds)", true));
        subCmds.add(new SubcommandData("config", "configure settings about a webhook").addOptions(new OptionData(OptionType.STRING, "image", "The link to the image you want the webhook to be", false), new OptionData(OptionType.STRING, "name", "The display name for the webhook", false), new OptionData(OptionType.STRING, "link", "The webhook url", false)));
        subCmds.add(new SubcommandData("setactive", "Activates or deactivates the listener for the webhook").addOption(OptionType.BOOLEAN, "active", "Whether the webhook listener is active or not", true));
        this.register = true;

        active = true;
    }

    @Override
    public void runSlashCommand(@NotNull SlashCommandInteractionEvent event) {
        switch (event.getSubcommandName()){
            case("info"):
                boolean withLink = event.getOption("withlink", false, OptionMapping::getAsBoolean);

                List<String> elements = read();

                EmbedBuilder eb = getEmbedBuilder(elements, withLink);
                event.getInteraction().replyEmbeds(eb.build()).queue();

                if(withLink){
                    event.getInteraction().getHook().deleteOriginal().queueAfter(10000, TimeUnit.MILLISECONDS);
                }

                break;

            case("config"):
                List<String> c_elements = read();

                String img = c_elements.get(2);
                String name = c_elements.get(1);
                String link = c_elements.get(3);

                name = event.getOption("name", name, OptionMapping::getAsString);
                img = event.getOption("image", img, OptionMapping::getAsString);
                link = event.getOption("link", link, OptionMapping::getAsString);

                Bot.log.info(event.getUser().getName() + " fired webhook config with args \n" + name + "\n" + img + "\n" + "link");

                if(img.contains("?")){
                    int indx = img.indexOf("?");
                    img = img.substring(0, indx);
                }

                ValidationMessage msg = validate(name, img, link);

                if(!msg.successful){
                    Bot.log.warn(msg.message);
                    event.reply(msg.message).setEphemeral(true).queue();
                    return;
                }

                write(name, img, link);

                event.reply(msg.message).setEphemeral(true).queue();

                break;

            case("setactive"):
                active = Boolean.TRUE.equals(event.getOption("active", OptionMapping::getAsBoolean));
                event.reply((active ? "activating ": "deactivating ") + "webhook").queue();
                break;

            default:
                event.reply("something went wrong").setEphemeral(true).queue();
        }
    }

    @NotNull
    private static EmbedBuilder getEmbedBuilder(List<String> elements, boolean withLink) {
        EmbedBuilder eb = new EmbedBuilder();


        eb.setColor(Color.white);
        eb.setThumbnail(elements.get(2));
        eb.addField("Name: ", elements.get(1), false);
        eb.addField("Active: ", String.valueOf(active), false);
        eb.setTitle(withLink ? "Webhook Info, **Will be deleted in 10 seconds**" : "Webhook Info");
        if(withLink){
            eb.addField("Link: ", "|| " + elements.get(3) + " ||", false);
        }
        eb.setFooter("Written in Java by srth#2668 ", "https://avatars.githubusercontent.com/u/94727593?v=4");
        return eb;
    }

    private static void write(String name, String img, String link) {
        try{
            BufferedWriter namewriter = new BufferedWriter(new FileWriter("C:\\Users\\SRTH_\\Desktop\\leilabot\\name.txt"));
            BufferedWriter pfpwriter = new BufferedWriter(new FileWriter("C:\\Users\\SRTH_\\Desktop\\leilabot\\pfp.txt"));
            BufferedWriter webhookwriter = new BufferedWriter(new FileWriter("C:\\Users\\SRTH_\\Desktop\\leilabot\\webhook.txt"));

            namewriter.write(name);
            pfpwriter.write(img);
            webhookwriter.write(link);

            namewriter.flush();
            namewriter.close();
            pfpwriter.flush();
            pfpwriter.close();
            webhookwriter.flush();
            webhookwriter.close();
        }catch (IOException e){
            Bot.log.error(e.getMessage());
        }
    }

    /**
     *
     * @return A list of 3 strings where the first string is the name, second string is the pfp and third is the webhook link.
     */

    private static List<String> read() {
        try{
            BufferedReader cfg_namereader = new BufferedReader(new FileReader("C:\\Users\\SRTH_\\Desktop\\leilabot\\name.txt"));
            BufferedReader cfg_pfpreader = new BufferedReader(new FileReader("C:\\Users\\SRTH_\\Desktop\\leilabot\\pfp.txt"));
            BufferedReader cfg_webhookreader = new BufferedReader(new FileReader("C:\\Users\\SRTH_\\Desktop\\leilabot\\webhook.txt"));

            List<String> toReturn = new ArrayList<>(List.of(cfg_namereader.readLine(), cfg_pfpreader.readLine(), cfg_webhookreader.readLine()));

            cfg_webhookreader.close();
            cfg_namereader.close();
            cfg_pfpreader.close();

            return toReturn;
        }catch (IOException e){
            Bot.log.error(e.getMessage());
            return List.of();
        }
    }

    private static ValidationMessage validate(String name, String pfp, String webhook){
        if(!pfp.endsWith("jpg") || !pfp.endsWith("png") || !pfp.endsWith("jpeg")){
            return new ValidationMessage("profile picture must be a jpeg or png", false);
        }

        if(!webhook.startsWith("https://discord.com/api/webhooks/")){
            return new ValidationMessage("invalid webhook link", false);
        }

        return new ValidationMessage("success", true);
    }

    public static boolean isActive(){return active;}


    private static final class ValidationMessage{

        private final String message;
        private final boolean successful;

        private ValidationMessage(String msg, boolean suc){
            this.successful = suc;
            this.message = msg;
        }
    }
}
