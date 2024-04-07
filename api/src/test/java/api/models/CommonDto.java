package api.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CommonDto {
    @Override
    public String toString() {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
