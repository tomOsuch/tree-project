package pl.tomaszosuch.service.impl;

import org.junit.Test;
import pl.tomaszosuch.exception.InvalidBranchParameterException;
import pl.tomaszosuch.model.Branch;
import pl.tomaszosuch.model.Leaf;
import pl.tomaszosuch.model.Needle;
import pl.tomaszosuch.service.BranchService;
import pl.tomaszosuch.service.LeafService;
import pl.tomaszosuch.service.NeedleService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BranchServiceImplTest {

    private final NeedleService needleService = new NeedleServiceImpl();
    private final LeafService leafService = new LeafServiceImpl();
    private final BranchService branchService = new BranchServiceImpl(leafService, needleService);

    @Test
    public void createBranchShouldInitializeBranch() {
        Branch branch = branchService.createBranch(2.0, 1);

        assertEquals(2.0, branch.getLength(), 0.0001);
        assertEquals(1, branch.getBranchingLevel());
        assertTrue(branch.isAlive());
        assertTrue(branch.getLeaves().isEmpty());
        assertTrue(branch.getNeedles().isEmpty());
    }

    @Test
    public void createBranchShouldThrowWhenLengthIsNaN() {
        assertInvalidBranchParam(
                () -> branchService.createBranch(Double.NaN, 1),
                "length musi być skończoną liczbą."
        );
    }

    @Test
    public void createBranchShouldThrowWhenLengthIsZero() {
        assertInvalidBranchParam(
                () -> branchService.createBranch(0.0, 1),
                "length musi być większa od zera."
        );
    }

    @Test
    public void createBranchShouldThrowWhenLengthIsNegative() {
        assertInvalidBranchParam(
                () -> branchService.createBranch(-1.0, 1),
                "length musi być większa od zera."
        );
    }

    @Test
    public void createBranchShouldThrowWhenBranchingLevelIsNegative() {
        assertInvalidBranchParam(
                () -> branchService.createBranch(1.0, -1),
                "branchingLevel nie może być ujemny."
        );
    }

    @Test
    public void addLeafShouldAddLeafToBranch() {
        Branch branch = newBranch();
        Leaf leaf = newLeaf(true);

        branchService.addLeaf(branch, leaf);

        assertEquals(1, branch.getLeaves().size());
        assertTrue(branch.getLeaves().contains(leaf));
    }

    @Test(expected = InvalidBranchParameterException.class)
    public void addLeafShouldThrowWhenBranchIsNull() {
        branchService.addLeaf(null, newLeaf(true));
    }

    @Test
    public void addNeedleShouldAddNeedleToBranch() {
        Branch branch = newBranch();
        Needle needle = newNeedle(true);

        branchService.addNeedle(branch, needle);

        assertEquals(1, branch.getNeedles().size());
        assertTrue(branch.getNeedles().contains(needle));
    }

    @Test
    public void growShouldIncreaseBranchLength() {
        Branch branch = newBranch();

        branchService.grow(branch, 0.5);

        assertEquals(2.5, branch.getLength(), 0.0001);
    }

    @Test(expected = InvalidBranchParameterException.class)
    public void growShouldThrowWhenDeltaIsNegative() {
        Branch branch = newBranch();
        branchService.grow(branch, -0.1);
    }

    @Test(expected = InvalidBranchParameterException.class)
    public void reactToSeasonChangeShouldThrowWhenSeasonIsBlank() {
        Branch branch = newBranch();
        branchService.reactToSeasonChange(branch, "   ");
    }

    @Test
    public void deactivateAllOrgansShouldSetPhotosynthesisFalseForAll() {
        Branch branch = newBranch();
        Leaf leaf = newLeaf(true);
        Needle needle = newNeedle(true);
        branchService.addLeaf(branch, leaf);
        branchService.addNeedle(branch, needle);

        branchService.deactivateAllOrgans(branch);

        assertFalse(branch.getLeaves().get(0).isActivePhotosynthesis());
        assertFalse(branch.getNeedles().get(0).isActivePhotosynthesis());
    }

    @Test
    public void countActivateOrgansShouldReturnCorrectCount() {
        Branch branch = newBranch();
        branchService.addLeaf(branch, newLeaf(true));
        branchService.addLeaf(branch, newLeaf(false));
        branchService.addNeedle(branch, newNeedle(true));

        int activeCount = branchService.countActivateOrgans(branch);

        assertEquals(2, activeCount);
    }

    @Test
    public void hasAnyLeafShouldReturnTrueWhenLeafExists() {
        Branch branch = newBranch();
        branchService.addLeaf(branch, newLeaf(true));

        assertTrue(branchService.hasAnyLeaf(branch));
    }

    @Test
    public void hasAnyNeedleShouldReturnFalseWhenNoNeedles() {
        Branch branch = newBranch();

        assertFalse(branchService.hasAnyNeedle(branch));
    }

    private Branch newBranch() {
        return branchService.createBranch(2.0, 1);
    }

    private Leaf newLeaf(boolean activePhotosynthesis) {
        return new Leaf(1.0, true, "green", activePhotosynthesis);
    }

    private Needle newNeedle(boolean activePhotosynthesis) {
        return new Needle(2, 24, 0.8, "green", activePhotosynthesis);
    }

    private void assertInvalidBranchParam(Runnable action, String expectedMessage) {
        try {
            action.run();
            assertTrue("Expected InvalidBranchParameterException", false);
        } catch (InvalidBranchParameterException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

}
