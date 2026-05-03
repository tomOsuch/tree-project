package pl.tomaszosuch.service.impl;

import pl.tomaszosuch.exception.InvalidNeedleParameterException;
import pl.tomaszosuch.exception.InvalidSeasonException;
import pl.tomaszosuch.model.Needle;
import pl.tomaszosuch.service.NeedleService;

public class NeedleServiceImpl implements NeedleService {

    @Override
    public Needle createNeedle(double surfaceArea) {
        return createNeedle(surfaceArea, 40);
    }

    @Override
    public Needle createNeedle(double surfaceArea, int lifespanMonths) {
        if (Double.isNaN(surfaceArea) || Double.isInfinite(surfaceArea)) {
            throw new InvalidNeedleParameterException("surfaceArea musi być skończoną liczbą.");
        }
        if (surfaceArea <= 0) {
            throw new InvalidNeedleParameterException("surfaceArea musi być większa od zera.");
        }
        if (lifespanMonths <= 0) {
            throw new InvalidNeedleParameterException("lifespanMonths musi być większy od zera.");
        }
        
        Needle needle = new Needle();
        needle.setAgeMonths(0);
        needle.setSurfaceArea(surfaceArea);
        needle.setLifeSpanMonths(lifespanMonths);
        needle.setColor("green");
        needle.setActivePhotosynthesis(true);
        return needle;
    }

    @Override
    public void reactToSeasonChange(Needle needle, String season) {
        if (needle == null) {
            throw new InvalidNeedleParameterException("Needle nie może być null.");
        }
        if (season == null || season.isBlank()) {
            throw new InvalidSeasonException(season);
        }
        switch (season.toUpperCase()) {
            case "SPRING":
                needle.setActivePhotosynthesis(true);
                needle.setColor("light green");
                break;
            case "SUMMER":
                needle.setActivePhotosynthesis(true);
                needle.setColor("green");
                break;
            case "AUTUMN":
                needle.setActivePhotosynthesis(false);
                needle.setColor("yellow green");
                break;
            case "WINTER":
                needle.setActivePhotosynthesis(false);
                needle.setColor("brown");
                break;
            default:
                throw new InvalidSeasonException(season);
        }
    }

    @Override
    public boolean shouldBeReplaced(Needle needle) {
        if (needle == null) {
            throw new InvalidNeedleParameterException("Needle nie może być null.");
        }
        return needle.getAgeMonths() >= needle.getLifeSpanMonths();
    }

}
