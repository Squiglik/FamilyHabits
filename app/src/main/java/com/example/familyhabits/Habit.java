package com.example.familyhabits;

public class Habit {
    public String id;
    public String member;
    public String name;
    public boolean doneToday;
    public long lastDate;

    public Habit(String id, String member, String name, boolean doneToday, long lastDate) {
        this.id = id;
        this.member = member;
        this.name = name;
        this.doneToday = doneToday;
        this.lastDate = lastDate;
    }
}
