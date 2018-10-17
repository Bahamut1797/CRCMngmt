package com.bohemiamates.crcmngmt.models;

import java.util.List;

public class ClanWarLog {
    private long createdDate;
    private List<Participant> participants;

    public ClanWarLog() {

    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
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
                "createdDate=" + createdDate +
                ", participants=" + participants +
                '}';
    }
}
