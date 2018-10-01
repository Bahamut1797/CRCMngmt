package com.bohemiamates.crcmngmt.entities;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.bohemiamates.crcmngmt.models.Badge;

@Entity(tableName = "clans")
public class Clan {

    @PrimaryKey
    @NonNull
    private String tag;
    private String name;
    private String description;
    private String type;
    private int score;
    private int memberCount;
    private int requiredScore;
    private int donations;
    @Embedded
    private Badge badge;

    public Clan() {

    }

    public Clan(com.bohemiamates.crcmngmt.models.Clan clan) {
        this.tag = clan.getTag();
        this.name = clan.getName();
        this.description = clan.getDescription();
        this.type = clan.getType();
        this.score = clan.getScore();
        this.memberCount = clan.getMemberCount();
        this.requiredScore = clan.getRequiredScore();
        this.donations = clan.getDonations();
        this.badge = clan.getBadge();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public int getRequiredScore() {
        return requiredScore;
    }

    public void setRequiredScore(int requiredScore) {
        this.requiredScore = requiredScore;
    }

    public int getDonations() {
        return donations;
    }

    public void setDonations(int donations) {
        this.donations = donations;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    @Override
    public String toString() {
        return "Clan{" +
                "tag='" + tag + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", score=" + score +
                ", memberCount=" + memberCount +
                ", requiredScore=" + requiredScore +
                ", donations=" + donations +
                ", badge=" + badge +
                '}';
    }
}
