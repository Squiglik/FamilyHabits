package com.example.familyhabits;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class HabitStore {

    private static final String PREF = "habits_pref";
    private static final String KEY  = "habits_json";

    public static List<Habit> load(Context ctx) {
        List<Habit> list = new ArrayList<>();
        SharedPreferences sp = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        String json = sp.getString(KEY, "[]");
        try {
            JSONArray arr = new JSONArray(json);
            long today = todayIndex();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                boolean done = o.getBoolean("done");
                long last = o.getLong("last");
                if (last != today) done = false;
                list.add(new Habit(
                        o.getString("id"),
                        o.getString("member"),
                        o.getString("name"),
                        done,
                        last
                ));
            }
        } catch (Exception ignored) {}
        return list;
    }

    public static void save(Context ctx, List<Habit> list) {
        JSONArray arr = new JSONArray();
        try {
            for (Habit h : list) {
                JSONObject o = new JSONObject();
                o.put("id", h.id);
                o.put("member", h.member);
                o.put("name", h.name);
                o.put("done", h.doneToday);
                o.put("last", h.lastDate);
                arr.put(o);
            }
        } catch (Exception ignored) {}
        SharedPreferences sp = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        sp.edit().putString(KEY, arr.toString()).apply();
    }

    public static long todayIndex() {
        return System.currentTimeMillis() / (1000L * 60 * 60 * 24);
    }
}