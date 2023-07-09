package ro.srth.leila.commands.cmds;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.annotations.GuildSpecific;
import ro.srth.leila.commands.Command;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

@GuildSpecific(guildIdLong = 696053797755027537L)
public class ConfigWebhook extends Command {

    public ConfigWebhook() {
        this.commandName = "configurewebhook";
        this.description = "Change certian options of the webhook";
        this.type = CommandType.SLASH;
        this.args = new ArrayList<OptionData>();
        args.add(new OptionData(OptionType.STRING, "image", "The link to the image you want the webhook to be", false));
        args.add(new OptionData(OptionType.STRING, "name", "The display name for the webhook", false));
        args.add(new OptionData(OptionType.STRING, "link", "The webhook url", false));
        this.register = true;
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(this.commandName) && !event.isAcknowledged()) {

            BufferedReader namereader;
            BufferedReader pfpreader;
            BufferedReader webhookreader;

            String img;
            String name;
            String link;

            try {
                namereader = new BufferedReader(new FileReader("C:\\Users\\SRTH_\\Desktop\\leilabot\\name.txt"));
                pfpreader = new BufferedReader(new FileReader("C:\\Users\\SRTH_\\Desktop\\leilabot\\pfp.txt"));
                webhookreader = new BufferedReader(new FileReader("C:\\Users\\SRTH_\\Desktop\\leilabot\\webhook.txt"));

                name = event.getOption("name", namereader.readLine(), OptionMapping::getAsString);
                img = event.getOption("image", pfpreader.readLine(), OptionMapping::getAsString);
                link = event.getOption("link", webhookreader.readLine(), OptionMapping::getAsString);

                if(!Objects.equals(img, pfpreader.readLine()) && !(img.endsWith("png")|| img.endsWith("jpg") || img.endsWith("jpeg"))){
                    event.reply("image must be a png or jpeg").setEphemeral(true).queue();
                    namereader.close();
                    pfpreader.close();
                    webhookreader.close();
                    return;
                }

                if(Objects.isNull(img) && Objects.isNull(name) && Objects.isNull(link)) {
                    event.reply("all options cant be blank").setEphemeral(true).queue();
                    namereader.close();
                    pfpreader.close();
                    webhookreader.close();
                    return;
                }


                namereader.close();
                pfpreader.close();
                webhookreader.close();

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
        }
    }
}
