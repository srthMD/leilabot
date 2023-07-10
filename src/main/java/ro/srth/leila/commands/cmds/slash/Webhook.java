package ro.srth.leila.commands.cmds.slash;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.Bot;
import ro.srth.leila.annotations.GuildSpecific;
import ro.srth.leila.commands.Command;

import java.awt.*;
import java.io.*;
import java.util.Objects;


@GuildSpecific(guildIdLong = 696053797755027537L)
public class Webhook extends Command {

    private static boolean active;
    public Webhook() {
        super();
        this.commandName = "webhook";
        this.description = "Command for webhook functionality";
        this.type = CommandType.SLASH;
        subCmds.add(new SubcommandData("info", "Shows set info about the webhook").addOption(OptionType.BOOLEAN, "withlink", "Option to include the webhook link, (will delete message after 10 seconds)", true));
        subCmds.add(new SubcommandData("config", "configure settings about a webhook").addOptions(new OptionData(OptionType.STRING, "image", "The link to the image you want the webhook to be", false), new OptionData(OptionType.STRING, "name", "The display name for the webhook", false), new OptionData(OptionType.STRING, "link", "The webhook url", false)));
        subCmds.add(new SubcommandData("setactive", "Activates or deactivates the listener for the webhook").addOption(OptionType.BOOLEAN, "active", "Whether the webhook listener is active or not", true));
        this.register = true;

        active = true;
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(this.commandName) && !event.isAcknowledged()) {
            switch (event.getSubcommandName()){
                case("info"):
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
                        eb.addField("Active: ", String.valueOf(active), false);
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

                    break;

                case("config"):


                    BufferedReader cfg_namereader;
                    BufferedReader cfg_pfpreader;
                    BufferedReader cfg_webhookreader;

                    String img;
                    String name;
                    String link;

                    try {
                        cfg_namereader = new BufferedReader(new FileReader("C:\\Users\\SRTH_\\Desktop\\leilabot\\name.txt"));
                        cfg_pfpreader = new BufferedReader(new FileReader("C:\\Users\\SRTH_\\Desktop\\leilabot\\pfp.txt"));
                        cfg_webhookreader = new BufferedReader(new FileReader("C:\\Users\\SRTH_\\Desktop\\leilabot\\webhook.txt"));

                        name = event.getOption("name", cfg_namereader.readLine(), OptionMapping::getAsString);
                        img = event.getOption("image", cfg_pfpreader.readLine(), OptionMapping::getAsString);
                        link = event.getOption("link", cfg_webhookreader.readLine(), OptionMapping::getAsString);

                        Bot.log.info(event.getUser().getName() + " fired webhook config with args \n" + name + "\n" + img + "\n" + "link");

                        if(!Objects.equals(img, cfg_pfpreader.readLine()) && !(img.endsWith("png")|| img.endsWith("jpg") || img.endsWith("jpeg"))){
                            event.reply("image must be a png or jpeg").setEphemeral(true).queue();
                            cfg_namereader.close();
                            cfg_webhookreader.close();
                            cfg_pfpreader.close();
                            return;
                        }

                        if(Objects.isNull(img) && Objects.isNull(name) && Objects.isNull(link)) {
                            event.reply("all options cant be blank").setEphemeral(true).queue();
                            cfg_namereader.close();
                            cfg_webhookreader.close();
                            cfg_pfpreader.close();
                            return;
                        }


                        cfg_pfpreader.close();
                        cfg_namereader.close();
                        cfg_webhookreader.close();

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

                        event.reply("success").setEphemeral(true).queue();
                    } catch (IOException e) {
                        event.reply("something went wrong executing the command").setEphemeral(true).queue();
                    }

                    break;

                case("setactive"):
                    active = event.getOption("active", OptionMapping::getAsBoolean);
                    event.reply((active ? "activating ": "deactivating ") + "webhook").queue();
                    break;

                default:
                    event.reply("something went wrong").setEphemeral(true).queue();
            }
        }
    }

    public static boolean isActive(){return active;}
}
