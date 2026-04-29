package pl.tomaszosuch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Needle {

    private int ageMonths;
    private int lifeSpanMonths;
    private double surfaceArea;
    private String color;
    private boolean activePhotosynthesis;
}
