package api.common;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Utils {

    private final static Gson gson = new Gson();

    public static String removeField(Object object, String... field) {
        JsonObject jsonObject = gson.fromJson(gson.toJson(object), JsonObject.class);
        for (String string : field) {
            jsonObject.remove(string);
        }
        return jsonObject.toString();
    }

    public static <T> T deepCopy(T object, Class<T> type) {
        return gson.fromJson(gson.toJson(object, type), type);
    }
}
