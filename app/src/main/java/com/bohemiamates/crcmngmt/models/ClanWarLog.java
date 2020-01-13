package com.bohemiamates.crcmngmt.models;

import java.util.List;

public class ClanWarLog {
    private String warEndTime;
    private List<Participant> participants;

    public ClanWarLog() {

    }

    public String getWarEndTime() {
        return warEndTime;
    }

    public void setWarEndTime(String warEndTime) {
        this.warEndTime = warEndTime;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    @Override
    public String toString() {
        return "ClanWarLog{" +
                "warEndTime=" + warEndTime +
                ", participants=" + participants +
                '}';
    }
}
