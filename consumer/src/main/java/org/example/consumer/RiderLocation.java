package org.example.consumer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class RiderLocation {
    private String riderId;
    private double latitude;
    private double longitude;

    public RiderLocation(){

    }
}
