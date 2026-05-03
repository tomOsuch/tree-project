package pl.tomaszosuch.service.impl;

import org.junit.Test;

import pl.tomaszosuch.exception.InvalidNeedleParameterException;
import pl.tomaszosuch.exception.InvalidSeasonException;
import pl.tomaszosuch.model.Needle;
import pl.tomaszosuch.service.NeedleService;

public class NeedleServiceImplTest {

    private final NeedleService needleService = new NeedleServiceImpl();

    @Test(expected = InvalidNeedleParameterException.class)
    public void testCreateNeedleWithZeroSurfaceArea() {
        needleService.createNeedle(0.0);
    }

    @Test
    public void testCreateNeedleWithNegativeSurfaceArea() {
        try {
            needleService.createNeedle(-1.0);
            assert false; // Powinno rzucić wyjątek
        } catch (InvalidNeedleParameterException e) {
            assert "surfaceArea musi być większa od zera.".equals(e.getMessage());
        }
    }

    @Test
    public void testCreateNeedleWithValidSurfaceArea() {
        Needle needle = needleService.createNeedle(5.0);
        assert needle.getSurfaceArea() == 5.0;
        assert needle.getAgeMonths() == 0;
        assert needle.getLifeSpanMonths() == 40;
        assert "green".equals(needle.getColor());
        assert needle.isActivePhotosynthesis(); 
    }
    
    @Test
    public void testCreateNeedleWithInfiniteSurfaceArea() {
        try {
            needleService.createNeedle(Double.POSITIVE_INFINITY);
            assert false; // Powinno rzucić wyjątek
        } catch (InvalidNeedleParameterException e) {
            assert "surfaceArea musi być skończoną liczbą.".equals(e.getMessage());
        }
    }

    @Test
    public void testCreateNeedleWithValidSurfaceAreaAndLifespan() {
        Needle needle = needleService.createNeedle(5.0, 50);
        assert needle.getSurfaceArea() == 5.0;
        assert needle.getAgeMonths() == 0;
        assert needle.getLifeSpanMonths() == 50;
        assert "green".equals(needle.getColor());
        assert needle.isActivePhotosynthesis(); 
    }   

    @Test
    public void testCreateNeedleWithZeroLifespanMonths() {
        try {
            needleService.createNeedle(5.0, 0);
            assert false; // Powinno rzucić wyjątek
        } catch (InvalidNeedleParameterException e) {
            assert "lifespanMonths musi być większy od zera.".equals(e.getMessage());
        }
    }

    @Test
    public void testCreateNeedleWithNaNSurfaceArea() {
        try {
            needleService.createNeedle(Double.NaN);
            assert false;
        } catch (InvalidNeedleParameterException e) {
            assert "surfaceArea musi być skończoną liczbą.".equals(e.getMessage());
        }
    }

    @Test
    public void testCreateNeedleWithNegativeLifespanMonths() {
        try {
            needleService.createNeedle(5.0, -10);
            assert false;
        } catch (InvalidNeedleParameterException e) {
            assert "lifespanMonths musi być większy od zera.".equals(e.getMessage());
        }
    }

    @Test
    public void testReactToSeasonChangeWithNullNeedle() {
        try {
            needleService.reactToSeasonChange(null, "SPRING");
            assert false;
        } catch (InvalidNeedleParameterException e) {
            assert "Needle nie może być null.".equals(e.getMessage());
        }
    }

    @Test
    public void testReactToSeasonChangeWithNullSeason() {
        Needle needle = needleService.createNeedle(1.0);
        try {
            needleService.reactToSeasonChange(needle, null);
            assert false;
        } catch (InvalidSeasonException e) {
            assert "Nieznana pora roku: null".equals(e.getMessage());
        }
    }

    @Test
    public void testReactToSeasonChangeWithBlankSeason() {
        Needle needle = needleService.createNeedle(1.0);
        try {
            needleService.reactToSeasonChange(needle, "   ");
            assert false;
        } catch (InvalidSeasonException e) {
            assert e != null;
        }
    }

    @Test
    public void testReactToSeasonChangeWithInvalidSeason() {
        Needle needle = needleService.createNeedle(1.0);
        try {
            needleService.reactToSeasonChange(needle, "MONSOON");
            assert false;
        } catch (InvalidSeasonException e) {
            assert "Nieznana pora roku: MONSOON".equals(e.getMessage());
        }
    }

    @Test
    public void testReactToSeasonChangeSpring() {
        Needle needle = needleService.createNeedle(1.0);
        needleService.reactToSeasonChange(needle, "SPRING");
        assert needle.isActivePhotosynthesis();
        assert "light green".equals(needle.getColor());
    }

    @Test
    public void testReactToSeasonChangeSummer() {
        Needle needle = needleService.createNeedle(1.0);
        needleService.reactToSeasonChange(needle, "SUMMER");
        assert needle.isActivePhotosynthesis();
        assert "green".equals(needle.getColor());
    }

    @Test
    public void testReactToSeasonChangeAutumn() {
        Needle needle = needleService.createNeedle(1.0);
        needleService.reactToSeasonChange(needle, "AUTUMN");
        assert !needle.isActivePhotosynthesis();
        assert "yellow green".equals(needle.getColor());
    }

    @Test
    public void testReactToSeasonChangeWinter() {
        Needle needle = needleService.createNeedle(1.0);
        needleService.reactToSeasonChange(needle, "WINTER");
        assert !needle.isActivePhotosynthesis();
        assert "brown".equals(needle.getColor());
    }

    @Test
    public void testReactToSeasonChangeLowercase() {
        Needle needle = needleService.createNeedle(1.0);
        needleService.reactToSeasonChange(needle, "spring");
        assert needle.isActivePhotosynthesis();
        assert "light green".equals(needle.getColor());
    }

    @Test
    public void testShouldBeReplacedWithNullNeedle() {
        try {
            needleService.shouldBeReplaced(null);
            assert false;
        } catch (InvalidNeedleParameterException e) {
            assert "Needle nie może być null.".equals(e.getMessage());
        }
    }

    @Test
    public void testShouldBeReplacedWhenAgeIsLessThanLifespan() {
        Needle needle = needleService.createNeedle(1.0, 36);
        needle.setAgeMonths(10);
        assert !needleService.shouldBeReplaced(needle);
    }

    @Test
    public void testShouldBeReplacedWhenAgeEqualsLifespan() {
        Needle needle = needleService.createNeedle(1.0, 36);
        needle.setAgeMonths(36);
        assert needleService.shouldBeReplaced(needle);
    }

    @Test
    public void testShouldBeReplacedWhenAgeExceedsLifespan() {
        Needle needle = needleService.createNeedle(1.0, 36);
        needle.setAgeMonths(50);
        assert needleService.shouldBeReplaced(needle);
    }

}
