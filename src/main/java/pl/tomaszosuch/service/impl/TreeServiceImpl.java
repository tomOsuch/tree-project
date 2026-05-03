package pl.tomaszosuch.service.impl;

import pl.tomaszosuch.model.Branch;
import pl.tomaszosuch.service.BranchService;
import pl.tomaszosuch.service.LeafService;
import pl.tomaszosuch.service.NeedleService;
import pl.tomaszosuch.service.TreeService;
import pl.tomaszosuch.exception.InvalidBranchException;
import pl.tomaszosuch.exception.InvalidSeasonException;
import pl.tomaszosuch.exception.InvalidTreeException;
import pl.tomaszosuch.enums.Season;

import pl.tomaszosuch.tree.Tree;

public class TreeServiceImpl implements TreeService {

    private final BranchService branchService;
    private final LeafService leafService;
    private final NeedleService needleService;

    public TreeServiceImpl(BranchService branchService, LeafService leafService, NeedleService needleService) {
        this.branchService = branchService;
        this.leafService = leafService;
        this.needleService = needleService;
    }

    @Override
    public void addBranch(Tree tree, Branch branch) {
        if (branch == null) {
            throw new InvalidBranchException("Branch nie może być null.");
        }
        tree.getBranches().add(branch);
    }

    @Override
    public void grow(Tree tree) {
        if (tree == null) {
            throw new InvalidTreeException("Tree nie może być null.");
        }
        absorbWaterAndMinerals(tree);
        double energy = performPhotosynthesis(tree);
        expandCrown(tree, energy);
        tree.setHeight(tree.getHeight() + tree.getGrowthRate());
        tree.setAgeYears(tree.getAgeYears() + 1);
    }

    @Override
    public void reactionToSeasonChange(Tree tree, String season) {
        if (tree == null) {
            throw new InvalidTreeException("Tree nie może być null.");
        }
        if (season == null || season.isBlank()) {
            throw new InvalidSeasonException(season);
        }
        Season s;
        try {
            s = Season.valueOf(season.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidSeasonException(season);
        }
        for (Branch branch : tree.getBranches()) {
            branchService.reactToSeasonChange(branch, s.name());
        }
    }

    @Override
    public void runSeasoncycle(Tree tree) {
        if (tree == null) {
            throw new InvalidTreeException("Tree nie może być null.");
        }
        for (Season season : Season.values()) {
            reactionToSeasonChange(tree, season.name());
        }
        grow(tree);
    }

    @Override
    public String buildSummary(Tree tree) {
        if (tree == null) {
            throw new InvalidTreeException("Tree nie może być null.");
        }
        int totalOrgans = tree.getBranches().stream()
                .mapToInt(b -> branchService.countActivateOrgans(b))
                .sum();
        return String.format(
                "Gatunek: %s | Wiek: %d lat | Wysokość: %.2f m | Zdrowie: %.1f%% | Gałęzie: %d | Aktywne organy: %d",
                tree.getSpecies(),
                tree.getAgeYears(),
                tree.getHeight(),
                tree.getHealth(),
                tree.getBranches().size(),
                totalOrgans);
    }

    private void absorbWaterAndMinerals(Tree tree) {
        tree.setHealth(Math.min(100, tree.getHealth() + 1));
    }

    private double performPhotosynthesis(Tree tree) {
        double totalEnergy = 0;
        for (Branch branch : tree.getBranches()) {
            totalEnergy += branchService.countActivateOrgans(branch) * 0.1; // Przykładowa wartość energii
        }
        return totalEnergy;
    }
    
    private void expandCrown(Tree tree, double vigor) {
        for (Branch branch : tree.getBranches()) {
            branchService.grow(branch, 0.1 * vigor);
        }
    }
}
