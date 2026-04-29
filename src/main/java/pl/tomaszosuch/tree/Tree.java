package pl.tomaszosuch.tree;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.tomaszosuch.model.Branch;
import pl.tomaszosuch.model.Trunk;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Tree {
    private String species;
    private int ageYears;
    private double height;
    private double health;
    private double growthRate;
    private Trunk trunk;
    private List<Branch> branches = new ArrayList<Branch>();
}
