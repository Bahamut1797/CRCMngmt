package com.bohemiamates.crcmngmt.models;

import java.io.Serializable;
import java.util.List;

public class ClanBattle implements Serializable {
    private String type;
    private int utcTime;
    private int winner;
    private int teamCrowns;
    private int opponentCrowns;
    private List<Player> team;
    private List<Player> opponent;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUtcTime() {
        return utcTime;
    }

    public void setUtcTime(int utcTime) {
        this.utcTime = utcTime;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public int getTeamCrowns() {
        return teamCrowns;
    }

    public void setTeamCrowns(int teamCrowns) {
        this.teamCrowns = teamCrowns;
    }

    public int getOpponentCrowns() {
        return opponentCrowns;
    }

    public void setOpponentCrowns(int opponentCrowns) {
        this.opponentCrowns = opponentCrowns;
    }

    public List<Player> getTeam() {
        return team;
    }

    public void setTeam(List<Player> team) {
        this.team = team;
    }

    public List<Player> getOpponent() {
        return opponent;
    }

    public void setOpponent(List<Player> opponent) {
        this.opponent = opponent;
    }

    @Override
    public String toString() {
        return "ClanBattle{" +
                "type='" + type + '\'' +
                ", utcTime=" + utcTime +
                ", winner=" + winner +
                ", teamCrowns=" + teamCrowns +
                ", opponentCrowns=" + opponentCrowns +
                ", team=" + team +
                ", opponent=" + opponent +
                '}';
    }
}
