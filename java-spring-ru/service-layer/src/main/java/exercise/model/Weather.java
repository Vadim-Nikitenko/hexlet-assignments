package exercise.model;

import lombok.Data;

@Data
public class Weather {
    private String name;
    private String temperature;
    private String cloudy;
    private String humidity;
    private String wind;
}
