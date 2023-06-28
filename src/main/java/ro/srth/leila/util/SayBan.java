package ro.srth.leila.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;

public class SayBan {

    private static Gson gson;

    public JsonArray jArray;
    public SayBan(){
        gson = new GsonBuilder().create();

        jArray = new JsonArray();
    }

    public void writeJson(){
        try (FileWriter writer = new FileWriter("C:\\Users\\SRTH_\\Desktop\\leilabot\\saybanned.json")) {
            gson.toJson(jArray, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public JsonArray readJson() {

        JsonArray ids = null;
        try (Reader reader = new FileReader("C:\\Users\\SRTH_\\Desktop\\leilabot\\saybanned.json")) {

            ids = gson.fromJson(reader, JsonArray.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }

    public boolean isBanned(long id){
        JsonArray arr = readJson();

        return arr.contains(new JsonPrimitive(id));
    }

    public boolean unban(long id){
        if(isBanned(id)){
            JsonArray arr = readJson();
            arr.remove(new JsonPrimitive(id));

            if(isBanned(id)){
                return true;
            }else {
                throw new RuntimeException("something went wrong while unbanning");
            }
        }else{
            return false;
        }
    }
}
