package ro.srth.leila.api;

import net.dv8tion.jda.api.entities.channel.Channel;
import ro.srth.leila.listener.*;

public class KeywordMentionHandler{



    public static int returnInt(){

         Channel channel1 = ArmeniaMention.returnChannel();
         Channel channel2 = KurdistanMention.returnChannel();
         Channel channel3 = LeilaEmojiMention.returnChannel();
         Channel channel4 = PolandGifMention.returnChannel();
         Channel channel5 = SimonEmojiMention.returnChannel();

        if(channel1 != null){
            System.out.println(1);
            return 1;
        } else if (channel2 != null){
            System.out.println(2);
            return 2;
        } else if (channel3 != null){
            System.out.println(3);
            return 3;
        } else if (channel4 != null){
            System.out.println(4);
            return 4;
        } else if(channel5 != null){
            System.out.println(5);
            return 5;
        } else{
            return 0;
        }
    }
}
