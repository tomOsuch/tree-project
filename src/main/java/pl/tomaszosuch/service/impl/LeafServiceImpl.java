package pl.tomaszosuch.service.impl;

import pl.tomaszosuch.exception.InvalidLeafParameterException;
import pl.tomaszosuch.exception.InvalidSeasonException;
import pl.tomaszosuch.model.Leaf;
import pl.tomaszosuch.service.LeafService;

public class LeafServiceImpl implements LeafService {

    @Override
    public Leaf createLeaf(double size, boolean green) {
        if (Double.isNaN(size) || Double.isInfinite(size)) {
            throw new InvalidLeafParameterException("size musi być skończoną liczbą.");
        }
        if (size <= 0) {
            throw new InvalidLeafParameterException("size musi być większy od zera.");
        }
        Leaf leaf = new Leaf();
        leaf.setSize(size);
        leaf.setGreen(green);
        if (green) {
            leaf.setColor("green");
        } else {
            leaf.setColor("brown");
        }
        leaf.setActivePhotosynthesis(green);
        return leaf;
    }

    @Override
    public void reactToSeasonChange(Leaf leaf, String season) {
        if (leaf == null) {
            throw new InvalidLeafParameterException("Leaf nie może być null.");
        }
        if (season == null || season.isBlank()) {
            throw new InvalidSeasonException(season);
        }
        switch (season.toUpperCase()) {
            case "SPRING":
                leaf.setActivePhotosynthesis(true);
                leaf.setColor("light green");
                break;
            case "SUMMER":
                leaf.setActivePhotosynthesis(true);
                leaf.setColor("green");
                break;
            case "AUTUMN":
                leaf.setActivePhotosynthesis(false);
                leaf.setColor("yellow");
                break;
            case "WINTER":
                leaf.setActivePhotosynthesis(false);
                leaf.setColor("brown");
                break;
            default:
                throw new InvalidSeasonException(season);
        }
    }

    @Override
    public void fallOff(Leaf leaf) {
        
        if (leaf == null) {
            throw new InvalidLeafParameterException("Leaf nie może być null.");
        }
        leaf.setActivePhotosynthesis(false);
        leaf.setColor("brown");
        leaf.setGreen(false);
    }

}
