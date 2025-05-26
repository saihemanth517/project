package com.demo.dto;

import lombok.Data;

@Data
public class ShareTripRequest {
    private Long tripId;
    private String usernameToShareWith;

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getUsernameToShareWith() {
        return usernameToShareWith;
    }

    public void setUsernameToShareWith(String usernameToShareWith) {
        this.usernameToShareWith = usernameToShareWith;
    }
}

