package com.bohemiamates.crcmngmt.models;

public class Participant {
    private String tag;
    private String name;
    private int battlesPlayed;
    private int wins;
    private int collectionDayBattlesPlayed;

    public Participant() {

    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBattlesPlayed() {
        return battlesPlayed;
    }

    public void setBattlesPlayed(int battlesPlayed) {
        this.battlesPlayed = battlesPlayed;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getCollectionDayBattlesPlayed() {
        return collectionDayBattlesPlayed;
    }

    public void setCollectionDayBattlesPlayed(int collectionDayBattlesPlayed) {
        this.collectionDayBattlesPlayed = collectionDayBattlesPlayed;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "tag='" + tag + '\'' +
                ", name='" + name + '\'' +
                ", battlesPlayed=" + battlesPlayed +
                ", wins=" + wins +
                ", collectionDayBattlesPlayed=" + collectionDayBattlesPlayed +
                '}';
    }
}
