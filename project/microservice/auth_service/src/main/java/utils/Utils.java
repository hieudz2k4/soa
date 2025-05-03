package utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Utils {
  private static final Gson gson = new Gson();
    public static JsonObject toJson(String jsonString) {
    return JsonParser.parseString(jsonString).getAsJsonObject();
  }
}



