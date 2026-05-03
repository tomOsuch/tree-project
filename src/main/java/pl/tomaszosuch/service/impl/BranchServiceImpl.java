package pl.tomaszosuch.service.impl;

import pl.tomaszosuch.model.Branch;
import pl.tomaszosuch.model.Leaf;
import pl.tomaszosuch.model.Needle;
import pl.tomaszosuch.service.BranchService;
import pl.tomaszosuch.service.LeafService;
import pl.tomaszosuch.service.NeedleService;
import pl.tomaszosuch.exception.InvalidBranchParameterException;

public class BranchServiceImpl implements BranchService {

    private final LeafService leafService;
    private final NeedleService needleService;

    public BranchServiceImpl(LeafService leafService, NeedleService needleService) {
        this.leafService = leafService;
        this.needleService = needleService;
    }

    @Override
    public Branch createBranch(double length, int branchingLevel) {
        if (Double.isNaN(length) || Double.isInfinite(length)) {
            throw new InvalidBranchParameterException("length musi być skończoną liczbą.");
        }
        if (length <= 0) {
            throw new InvalidBranchParameterException("length musi być większa od zera.");
        }
        if (branchingLevel < 0) {
            throw new InvalidBranchParameterException("branchingLevel nie może być ujemny.");
        }
        
        Branch branch = new Branch();
        branch.setLength(length);
        branch.setBranchingLevel(branchingLevel);
        branch.setAlive(true);
        return branch;
    }

    @Override
    public void addLeaf(Branch branch, Leaf leaf) {
        if (branch == null) {
            throw new InvalidBranchParameterException("Branch nie może być null.");
        }
        if (leaf == null) {
            throw new InvalidBranchParameterException("Leaf nie może być null.");
        }
        branch.getLeaves().add(leaf);
    }

    @Override
    public void addNeedle(Branch branch, Needle needle) {
        if (branch == null) {
            throw new InvalidBranchParameterException("Branch nie może być null.");
        }
        if (needle == null) {
            throw new InvalidBranchParameterException("Needle nie może być null.");
        }
        branch.getNeedles().add(needle);
    }

    @Override
    public void grow(Branch branch, double deltaBranchLength) {
        if (branch == null) {
            throw new InvalidBranchParameterException("Branch nie może być null.");
        }
        if (Double.isNaN(deltaBranchLength) || Double.isInfinite(deltaBranchLength)) {
            throw new InvalidBranchParameterException("deltaBranchLength musi być skończoną liczbą.");
        }
        if (deltaBranchLength < 0) {
            throw new InvalidBranchParameterException("deltaBranchLength nie może być ujemna.");
        }
        
        branch.setLength(branch.getLength() + deltaBranchLength);
    }

    @Override
    public void reactToSeasonChange(Branch branch, String season) {
        if (branch == null) {
            throw new InvalidBranchParameterException("Branch nie może być null.");
        }
        if (season == null || season.isBlank()) {
            throw new InvalidBranchParameterException("Season nie może być null ani pusta.");
        }

        for (Leaf leaf : branch.getLeaves()) {
            leafService.reactToSeasonChange(leaf, season);
        }
        for (Needle needle : branch.getNeedles()) {
            needleService.reactToSeasonChange(needle, season);
        }
    }

    @Override
    public void deactivateAllOrgans(Branch branch) {
        if (branch == null) {
            throw new InvalidBranchParameterException("Branch nie może być null.");
        }
        for (Leaf leaf : branch.getLeaves()) {
            leaf.setActivePhotosynthesis(false);
        }
        for (Needle needle : branch.getNeedles()) {
            needle.setActivePhotosynthesis(false);
        }
    }

    @Override
    public int countActivateOrgans(Branch branch) {
        if (branch == null) {
            throw new InvalidBranchParameterException("Branch nie może być null.");
        }
        int count = 0;
        for (Leaf leaf : branch.getLeaves()) {
            if (leaf.isActivePhotosynthesis()) {
                count++;
            }
        }
        for (Needle needle : branch.getNeedles()) {
            if (needle.isActivePhotosynthesis()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean hasAnyLeaf(Branch branch) {
        if (branch == null) {
            throw new InvalidBranchParameterException("Branch nie może być null.");
        }
        return !branch.getLeaves().isEmpty();
    }

    @Override
    public boolean hasAnyNeedle(Branch branch) {
        if (branch == null) {
            throw new InvalidBranchParameterException("Branch nie może być null.");
        }
        return !branch.getNeedles().isEmpty();
    }

}
    