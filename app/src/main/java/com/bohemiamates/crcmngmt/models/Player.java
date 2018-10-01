package com.bohemiamates.crcmngmt.models;

public class Player {
    private String name;
    private String tag;
    private int rank;
    private String clanTag;
    private String role;
    private int expLevel;
    private int trophies;
    private int donations;
    private int donationsReceived;
    private int donationsDelta;
    private double donationsPercent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getClanTag() {
        return clanTag;
    }

    public void setClanTag(String clanTag) {
        this.clanTag = clanTag;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getExpLevel() {
        return expLevel;
    }

    public void setExpLevel(int expLevel) {
        this.expLevel = expLevel;
    }

    public int getTrophies() {
        return trophies;
    }

    public void setTrophies(int trophies) {
        this.trophies = trophies;
    }

    public int getDonations() {
        return donations;
    }

    public void setDonations(int donations) {
        this.donations = donations;
    }

    public int getDonationsReceived() {
        return donationsReceived;
    }

    public void setDonationsReceived(int donationsReceived) {
        this.donationsReceived = donationsReceived;
    }

    public int getDonationsDelta() {
        return donationsDelta;
    }

    public void setDonationsDelta(int donationsDelta) {
        this.donationsDelta = donationsDelta;
    }

    public double getDonationsPercent() {
        return donationsPercent;
    }

    public void setDonationsPercent(double donationsPercent) {
        this.donationsPercent = donationsPercent;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", tag='" + tag + '\'' +
                ", rank=" + rank +
                ", clanTag='" + clanTag + '\'' +
                ", role='" + role + '\'' +
                ", expLevel=" + expLevel +
                ", trophies=" + trophies +
                ", donations=" + donations +
                ", donationsReceived=" + donationsReceived +
                ", donationsDelta=" + donationsDelta +
                ", donationsPercent=" + donationsPercent +
                '}';
    }
}
