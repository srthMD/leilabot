package ro.srth.leila.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ro.srth.leila.*;
import ro.srth.leila.annotations.NeedsRevamp;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
@NeedsRevamp(reason = "old")
public class GetCopypasta {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    Random random = ThreadLocalRandom.current();

    public String getCopypasta(String query) throws Exception{

        String[] triggers = Bot.env.get("COPYPASTASEARCHNSFWTRIGGERS").split(","); // i am not publicly posting this list on github you dont want to see it

        if (query.contains(" ")){
            query = query.replaceAll(" ", "%20");
        }

        String content;
        String title;

        String[] sorts = {
                "top",
                "hot",
                "relevance"
        };

        String[] times = {
                "year",
                "all"
        };

        String sort = sorts[random.nextInt(sorts.length)];
        String time = times[random.nextInt(times.length)];
        int jsonindex = random.nextInt(0,2);


        String url = "https://www.reddit.com/r/copypasta/search.json?q=title:" + query + "&sort=" + sort + "&restrict_sr=true&t=" + time + "&limit=3";

        Bot.log.info(url);

        if(sort.equals("hot")){
            sort = "trending";
        }

        URL url1 = new URL(url);
        URLConnection request = url1.openConnection();
        request.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");

        JsonObject jsonObject = gson.fromJson(new InputStreamReader((InputStream) request.getContent()), JsonObject.class);
        try{
            content = jsonObject.get("data").getAsJsonObject().get("children").getAsJsonArray().get(jsonindex).getAsJsonObject().get("data").getAsJsonObject().get("selftext").getAsString();
            title = "```" + jsonObject.get("data").getAsJsonObject().get("children").getAsJsonArray().get(jsonindex).getAsJsonObject().get("data").getAsJsonObject().get("title").getAsString() + "```";
        } catch (IndexOutOfBoundsException e ){
            return "no results came up for this query \n\n**Query: " + query + "**\n" + "Sorted posts by " + sort + " with time period of " + time;
        }

        boolean flag = true;

        for (String trigger : triggers) {
            if (content.contains(trigger)) {
                flag = true;
                break;
            } else {
                flag = false;
            }
        }
        System.out.println(flag);


        if (query.contains("%20")){
            query = query.replaceAll("%20", " ");
        }

        if (jsonObject.get("data").getAsJsonObject().get("children").getAsJsonArray().get(jsonindex).getAsJsonObject().get("data").getAsJsonObject().get("selftext").getAsString().length() >= 1900) {
            if(flag || jsonObject.get("data").getAsJsonObject().get("children").getAsJsonArray().get(jsonindex).getAsJsonObject().get("data").getAsJsonObject().get("over_18").getAsBoolean()){
                return "cant show this copypasta because it is over 2000 characters, so heres the link: ||https://www.reddit.com" + jsonObject.get("data").getAsJsonObject().get("children").getAsJsonArray().get(jsonindex).getAsJsonObject().get("data").getAsJsonObject().get("permalink").getAsString() + "|| \n\n**Query: " + query + "**" + "\n(content was marked as nsfw by bot)";
            } else {
                return "cant show this copypasta because it is over 2000 characters, so heres the link: https://www.reddit.com" + jsonObject.get("data").getAsJsonObject().get("children").getAsJsonArray().get(jsonindex).getAsJsonObject().get("data").getAsJsonObject().get("permalink").getAsString() + "\n\n**Query: " + query + "**" + "\n(content was not marked by bot as nsfw but still might be nsfw)";
            }
        } else if(jsonObject.get("data").getAsJsonObject().get("children").getAsJsonArray().get(jsonindex).getAsJsonObject().get("data").getAsJsonObject().get("selftext").getAsString().isEmpty()){
            return "no results came up for this query \n\n**Query: " + query + "**\n" + "Sorted posts by " + sort + " with time period of " + time;
        } else if(flag || jsonObject.get("data").getAsJsonObject().get("children").getAsJsonArray().get(jsonindex).getAsJsonObject().get("data").getAsJsonObject().get("over_18").getAsBoolean()){
            return "Post might be nsfw so spoilers added\n\n||" + title + "\n" + content + "||\n\n**Query: " + query + "**";
        }else {
            return title + "\n" + content + "\n\n**Query: " + query + "**";
        } // i hate this but it works
    } 
}
