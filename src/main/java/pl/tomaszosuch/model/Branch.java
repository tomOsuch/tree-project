package pl.tomaszosuch.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Branch {

    private double length;
    private int branchingLevel;
    private boolean alive;
    List<Leaf> leaves = new ArrayList<Leaf>();
    List<Needle> needles = new ArrayList<Needle>();
}
