package pl.tomaszosuch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Leaf {

    private double size;
    private boolean green;
    private String color;
    private boolean activePhotosynthesis;
}
