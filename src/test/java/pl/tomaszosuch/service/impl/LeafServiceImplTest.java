package pl.tomaszosuch.service.impl;

import org.junit.Test;

import pl.tomaszosuch.exception.InvalidLeafParameterException;
import pl.tomaszosuch.model.Leaf;
import pl.tomaszosuch.service.LeafService;

public class LeafServiceImplTest {

    private final LeafService leafService = new LeafServiceImpl();

    @Test(expected = InvalidLeafParameterException.class)
    public void testCreateLeafWithNegativeSize() {
        leafService.createLeaf(-1.0, true);
    }

    @Test
    public void testCreateLeafWithValidParameters() {
        Leaf leaf = leafService.createLeaf(5.0, true);
        assert leaf.getSize() == 5.0;
        assert leaf.isGreen();
        assert "green".equals(leaf.getColor());
        assert leaf.isActivePhotosynthesis();
    }   

    @Test
    public void testReactToSeasonChange() {
        Leaf leaf = leafService.createLeaf(5.0, true);
        leafService.reactToSeasonChange(leaf, "AUTUMN");
        assert !leaf.isActivePhotosynthesis();
        assert "yellow".equals(leaf.getColor());
    }

    @Test
    public void testFallOff() {
        Leaf leaf = leafService.createLeaf(5.0, true);
        leafService.fallOff(leaf);
        assert !leaf.isActivePhotosynthesis();
        assert "brown".equals(leaf.getColor());
        assert !leaf.isGreen();
    }

    @Test
    public void testReactToSeasonChangeWithNullLeaf() {
        try {
            leafService.reactToSeasonChange(null, "SPRING");
            assert false; // Powinno rzucić wyjątek
        } catch (InvalidLeafParameterException e) {
            assert "Leaf nie może być null.".equals(e.getMessage());
        }
    }

    @Test
    public void testReactToSeasonChangeWithNullSeason() {
        Leaf leaf = leafService.createLeaf(5.0, true);
        try {
            leafService.reactToSeasonChange(leaf, null);
            assert false; // Powinno rzucić wyjątek
        } catch (Exception e) {
            assert e instanceof pl.tomaszosuch.exception.InvalidSeasonException;
            assert "Nieznana pora roku: null".equals(e.getMessage());       
        }
    }

    @Test
    public void testReactToSeasonChangeWithInvalidSeason() {
        Leaf leaf = leafService.createLeaf(5.0, true);
        try {
            leafService.reactToSeasonChange(leaf, "INVALID_SEASON");
            assert false; // Powinno rzucić wyjątek
        } catch (Exception e) {
            assert e instanceof pl.tomaszosuch.exception.InvalidSeasonException;
            assert "Nieznana pora roku: INVALID_SEASON".equals(e.getMessage());       
        }
    }

    @Test
    public void testFallOffWithNullLeaf() {
        try {
            leafService.fallOff(null);
            assert false; // Powinno rzucić wyjątek     
        } catch (InvalidLeafParameterException e) {
            assert "Leaf nie może być null.".equals(e.getMessage());
        }
    }

    @Test(expected = InvalidLeafParameterException.class)
    public void testCreateLeafWithZeroSize() {
        leafService.createLeaf(0.0, true);
    }

    @Test(expected = InvalidLeafParameterException.class)
    public void testCreateLeafWithNaNSize() {
        leafService.createLeaf(Double.NaN, true);
    }

    @Test(expected = InvalidLeafParameterException.class)
    public void testCreateLeafWithInfiniteSize() {
        leafService.createLeaf(Double.POSITIVE_INFINITY, true);
    }

    @Test
    public void testCreateLeafWithGreenFalse() {
        Leaf leaf = leafService.createLeaf(5.0, false);
        assert leaf.getSize() == 5.0;
        assert !leaf.isGreen();
        assert "brown".equals(leaf.getColor());
        assert !leaf.isActivePhotosynthesis();
    }

    @Test
    public void testReactToSeasonChangeWithBlankSeason() {
        Leaf leaf = leafService.createLeaf(5.0, true);
        try {
            leafService.reactToSeasonChange(leaf, "   ");
            assert false; // Powinno rzucić wyjątek
        } catch (Exception e) {
            assert e instanceof pl.tomaszosuch.exception.InvalidSeasonException;
        }
    }

    @Test
    public void testReactToSeasonChangeSpring() {
        Leaf leaf = leafService.createLeaf(5.0, true);
        leafService.reactToSeasonChange(leaf, "SPRING");
        assert leaf.isActivePhotosynthesis();
        assert "light green".equals(leaf.getColor());
    }

    @Test
    public void testReactToSeasonChangeSummer() {
        Leaf leaf = leafService.createLeaf(5.0, true);
        leafService.reactToSeasonChange(leaf, "SUMMER");
        assert leaf.isActivePhotosynthesis();
        assert "green".equals(leaf.getColor());
    }

    @Test
    public void testReactToSeasonChangeWinter() {
        Leaf leaf = leafService.createLeaf(5.0, true);
        leafService.reactToSeasonChange(leaf, "WINTER");
        assert !leaf.isActivePhotosynthesis();
        assert "brown".equals(leaf.getColor());
    }
}
