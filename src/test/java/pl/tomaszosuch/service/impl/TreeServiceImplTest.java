package pl.tomaszosuch.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import pl.tomaszosuch.model.Branch;
import pl.tomaszosuch.model.Trunk;
import pl.tomaszosuch.service.BranchService;
import pl.tomaszosuch.service.LeafService;
import pl.tomaszosuch.service.NeedleService;
import pl.tomaszosuch.service.TreeService;
import pl.tomaszosuch.tree.ConiferousTree;
import pl.tomaszosuch.tree.DeciduousTree;
import pl.tomaszosuch.tree.Tree;

public class TreeServiceImplTest {
    private final LeafService leafService = new LeafServiceImpl();
    private final NeedleService needleService = new NeedleServiceImpl();
    private final BranchService branchService = new BranchServiceImpl(leafService, needleService);
    private final TreeService treeService = new TreeServiceImpl(branchService, leafService, needleService);

    @Test
    public void treeShouldGrowHeightAndAge() {
        DeciduousTree tree = buildDeciduousTree("Oak", 10, 8.0, 80.0, 0.4, 0.5, 2.0, "rough", true);
        Branch branch = branchService.createBranch(2.5, 1);
        branchService.addLeaf(branch, leafService.createLeaf(2.2, true));
        treeService.addBranch(tree, branch);

        double initialHeight = tree.getHeight();
        int initialAge = tree.getAgeYears();

        treeService.grow(tree);

        assertTrue(tree.getHeight() > initialHeight);
        assertEquals(initialAge + 1, tree.getAgeYears());
    }

    private DeciduousTree buildDeciduousTree(String species, int age, double height, double health, double growthRate,
            double trunkDiameter, double trunkHeight, String bark, boolean seasonalLeafShedding) {
        DeciduousTree tree = new DeciduousTree();
        tree.setSpecies(species);
        tree.setAgeYears(age);
        tree.setHeight(height);
        tree.setHealth(health);
        tree.setGrowthRate(growthRate);
        tree.setTrunk(new Trunk(trunkDiameter, trunkHeight, bark));
        tree.setSeasonalLeafShedding(seasonalLeafShedding);
        return tree;
    }

    private ConiferousTree buildConiferousTree() {
        ConiferousTree tree = new ConiferousTree();
        tree.setSpecies("Spruce");
        tree.setAgeYears(7);
        tree.setHeight(5.0);
        tree.setHealth(78.0);
        tree.setGrowthRate(0.34);
        tree.setTrunk(new Trunk(0.36, 1.8, "scaly"));
        tree.setNeedleRetentionYears(3);
        return tree;
    }

    @Test
    public void deciduousTreeShouldStoreAssignedFields() {
        DeciduousTree tree = buildDeciduousTree("Oak", 10, 8.0, 80.0, 0.4, 0.5, 2.0, "rough", true);

        assertEquals("Oak", tree.getSpecies());
        assertEquals(10, tree.getAgeYears());
        assertEquals(8.0, tree.getHeight(), 0.0001);
        assertTrue(tree.isSeasonalLeafShedding());
    }

    @Test
    public void coniferousTreeShouldStoreAssignedFields() {
        ConiferousTree tree = buildConiferousTree();

        assertEquals("Spruce", tree.getSpecies());
        assertEquals(7, tree.getAgeYears());
        assertEquals(5.0, tree.getHeight(), 0.0001);
        assertEquals(3, tree.getNeedleRetentionYears());
    }
}
