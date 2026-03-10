package com.eastwest.dto.fastSpring.payloads;

import com.eastwest.dto.fastSpring.Subscription;
import lombok.Data;

@Data
public class SubscriptionCharge {
    public String currency;
    public int total;
    public String status;
    public long timestamp;
    public long timestampValue;
    public int timestampInSeconds;
    public String timestampDisplay;
    public int sequence;
    public Integer periods;
    public String quote;
    public Subscription subscription;
}
