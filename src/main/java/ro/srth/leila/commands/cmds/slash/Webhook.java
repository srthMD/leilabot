package ro.srth.leila.commands.cmds.slash;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.annotations.Local;
import ro.srth.leila.commands.SlashCommand;
import ro.srth.leila.exception.GuildNotFoundException;
import ro.srth.leila.exception.UnsuccessfulWriteException;
import ro.srth.leila.guild.GuildReader;
import ro.srth.leila.guild.GuildWriter;
import ro.srth.leila.guild.vars.GuildBoolean;
import ro.srth.leila.guild.vars.GuildChannel;
import ro.srth.leila.guild.vars.GuildString;
import ro.srth.leila.main.Bot;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Webhook extends SlashCommand {

    @Local(clazz = GuildBoolean.class)
    public static boolean webhookActive = true;

    @Local(clazz = GuildChannel.class)
    public static final long webhookChannel = 0L;

    @Local(clazz = GuildString.class)
    public static final String webhookLink = "null";

    @Local(clazz = GuildString.class)
    public static final String webhookPfp = "https://cdn.discordapp.com/attachments/1120459667638865950/1126608652384612423/21321313.png";

    @Local(clazz = GuildString.class)
    public static final String webhookName = "ahmad";


    public Webhook(Guild guild) {
        super(guild);
        this.commandName = "webhook";
        this.description = "SlashCommand for webhook functionality";
        subCmds.add(new SubcommandData("info", "Shows set info about the webhook").addOption(OptionType.BOOLEAN, "withlink", "Option to include the webhook link, (will delete message after 10 seconds)", true));
        subCmds.add(new SubcommandData("config", "configure settings about a webhook").addOptions(
                new OptionData(OptionType.STRING, "image", "The link to the image you want the webhook to be", false),
                new OptionData(OptionType.STRING, "name", "The display name for the webhook", false),
                new OptionData(OptionType.STRING, "link", "The webhook url", false),
                new OptionData(OptionType.CHANNEL, "webhookchannel", "The channel to listen for messages", false)
                ));

        subCmds.add(new SubcommandData("setactive", "Activates or deactivates the listener for the webhook").addOption(OptionType.BOOLEAN, "active", "Whether the webhook listener is active or not", true));
        permissions.add(Permission.MANAGE_WEBHOOKS);
    }

    public Webhook() {
        super();
        this.commandName = "webhook";
        this.description = "SlashCommand for webhook functionality";
    }


    @Override
    public void runSlashCommand(@NotNull SlashCommandInteractionEvent event) {
        switch (event.getSubcommandName()){
            case("info"):
                boolean withLink = event.getOption("withlink", false, OptionMapping::getAsBoolean);

                List<String> elements = read(event);

                EmbedBuilder eb = getEmbedBuilder(elements, withLink);
                event.getInteraction().replyEmbeds(eb.build()).queue((suc) -> {
                    if (withLink) {
                        suc.deleteOriginal().delay(5, TimeUnit.SECONDS).queue();
                    }
                });

                break;

            case("config"):
                List<String> c_elements = read(event);

                String name = c_elements.get(0);
                String img = c_elements.get(1);
                String link = c_elements.get(2);
                String webhookChannel = c_elements.get(3);

                name = event.getOption("name", name, OptionMapping::getAsString);
                img = event.getOption("image", img, OptionMapping::getAsString);
                link = event.getOption("link", link, OptionMapping::getAsString);
                webhookChannel = event.getOption("webhookchannel", webhookChannel, (val) -> val.getAsChannel().getId());

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

                write(name, img, link, webhookChannel, event);

                event.reply(msg.message).setEphemeral(true).queue();

                break;

            case("setactive"):
                webhookActive = Boolean.TRUE.equals(event.getOption("active", OptionMapping::getAsBoolean));
                event.reply((webhookActive ? "activating ": "deactivating ") + "webhook").queue();
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
        eb.addField("Active: ", String.valueOf(webhookActive), false);
        eb.setTitle(withLink ? "Webhook Info, **Will be deleted in 10 seconds**" : "Webhook Info");
        if(withLink){
            eb.addField("Link: ", "|| " + elements.get(3) + " ||", false);
        }
        eb.setFooter("Written in Java by srth#2668 ", "https://avatars.githubusercontent.com/u/94727593?v=4");
        return eb;
    }

    private static void write(String name, String img, String link, String webhookChannel, SlashCommandInteractionEvent event) {
        try{
            Guild guild = event.getGuild();

            GuildWriter.write(new GuildString(name, "webhookName", guild));
            GuildWriter.write(new GuildString(img, "webhookPfp", guild));
            GuildWriter.write(new GuildString(link, "webhookLink", guild));
            GuildWriter.write(new GuildChannel(Long.parseLong(webhookChannel), "webhookChannel", guild));
        } catch (UnsuccessfulWriteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return A list of 3 strings where the first string is the name, second string is the pfp and third is the webhook link.
     */

    private static List<String> read(SlashCommandInteractionEvent event) {
        try{
            var vars =  GuildReader.get(event.getGuild().getIdLong()).getVars();

            return List.of(vars.get("webhookName").toString(), vars.get("webhookPfp").toString(), vars.get("webhookLink").toString(), vars.get("webhookChannel").toString());

        }catch (GuildNotFoundException e){
            Bot.log.error(e.getMessage());
            return List.of();
        }
    }

    private static ValidationMessage validate(String name, String pfp, String webhook) {
        if (!(pfp.endsWith("jpg") || pfp.endsWith("png") || pfp.endsWith("jpeg"))) {
            return new ValidationMessage("profile picture must be a jpeg or png", false);
        }


        if (!webhook.equals("null") && !webhook.startsWith("https://discord.com/api/webhooks/")) {
            return new ValidationMessage("invalid webhook link", false);
        }

        return new ValidationMessage("success", true);
    }


    private static final class ValidationMessage{

        private final String message;
        private final boolean successful;

        private ValidationMessage(String msg, boolean suc){
            this.successful = suc;
            this.message = msg;
        }
    }
}
