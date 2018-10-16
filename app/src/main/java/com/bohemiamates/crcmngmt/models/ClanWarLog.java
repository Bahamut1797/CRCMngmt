package com.bohemiamates.crcmngmt.models;

import java.util.List;

public class ClanWarLog {
    private long createDate;
    private List<Participant> participants;

    public ClanWarLog() {

    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
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
                "createDate=" + createDate +
                ", participants=" + participants +
                '}';
    }
}
