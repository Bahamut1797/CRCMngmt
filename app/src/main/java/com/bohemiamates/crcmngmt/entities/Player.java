package com.bohemiamates.crcmngmt.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "players",
        indices = {@Index(value = "clanTag")},
        foreignKeys = @ForeignKey(entity = Clan.class,
                parentColumns = "tag",
                childColumns = "clanTag"))
public class Player {

    @SuppressWarnings("NullableProblems")
    @PrimaryKey
    @NonNull
    private String tag;
    private String name;
    private int rank;
    private String clanTag;
    private String role;
    private int expLevel;
    private int trophies;
    private int donations;
    private int donationsReceived;
    private int donationsDelta;
    private double donationsPercent;
    private int clanFails;
    private String clanBadgeUri;

    @NonNull
    public String getTag() {
        return tag;
    }

    public void setTag(@NonNull String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getClanFails() {
        return clanFails;
    }

    public void setClanFails(int clanFails) {
        this.clanFails = clanFails;
    }

    public String getClanBadgeUri() {
        return clanBadgeUri;
    }

    public void setClanBadgeUri(String clanBadgeUri) {
        this.clanBadgeUri = clanBadgeUri;
    }
}
