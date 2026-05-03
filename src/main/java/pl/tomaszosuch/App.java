package pl.tomaszosuch;

import pl.tomaszosuch.model.Branch;
import pl.tomaszosuch.model.Leaf;
import pl.tomaszosuch.model.Needle;
import pl.tomaszosuch.model.Trunk;
import pl.tomaszosuch.service.BranchService;
import pl.tomaszosuch.service.LeafService;
import pl.tomaszosuch.service.NeedleService;
import pl.tomaszosuch.service.TreeService;
import pl.tomaszosuch.service.impl.BranchServiceImpl;
import pl.tomaszosuch.service.impl.LeafServiceImpl;
import pl.tomaszosuch.service.impl.NeedleServiceImpl;
import pl.tomaszosuch.service.impl.TreeServiceImpl;
import pl.tomaszosuch.tree.ConiferousTree;
import pl.tomaszosuch.tree.DeciduousTree;
import pl.tomaszosuch.tree.Tree;

public class App {

    private static final LeafService leafService = new LeafServiceImpl();
    private static final NeedleService needleService = new NeedleServiceImpl();
    private static final BranchService branchService = new BranchServiceImpl(leafService, needleService);
    private static final TreeService treeService = new TreeServiceImpl(branchService, leafService, needleService);


    public static void main(String[] args) {
        Tree oak = buildDeciduousTree();
        Tree pine = buildConiferousTree();

        treeService.runSeasoncycle(oak);
        treeService.runSeasoncycle(pine);

        System.out.println(treeService.buildSummary(oak));
        System.out.println(treeService.buildSummary(pine));
    }

     private static Tree buildDeciduousTree() {
        DeciduousTree oak = new DeciduousTree();
        oak.setSpecies("Oak");
        oak.setAgeYears(10);
        oak.setHeight(8.0);
        oak.setHealth(80.0);
        oak.setGrowthRate(0.4);
        oak.setTrunk(new Trunk(0.5, 2.0, "rough"));

        Branch branch = branchService.createBranch(2.5, 1);
        Leaf leafA = leafService.createLeaf(2.2, true);
        Leaf leafB = leafService.createLeaf(1.8, true);
        branchService.addLeaf(branch, leafA);
        branchService.addLeaf(branch, leafB);
        treeService.addBranch(oak, branch);
        return oak;
    }

    private static Tree buildConiferousTree() {
        ConiferousTree pine = new ConiferousTree();
        pine.setSpecies("Pine");
        pine.setAgeYears(12);
        pine.setHeight(9.0);
        pine.setHealth(85.0);
        pine.setGrowthRate(0.35);
        pine.setTrunk(new Trunk(0.45, 2.3, "scaly"));
        pine.setNeedleRetentionYears(4);

        Branch branch = branchService.createBranch(2.0, 1);
        Needle needleA = needleService.createNeedle(1.0);
        Needle needleB = needleService.createNeedle(1.1);
        branchService.addNeedle(branch, needleA);
        branchService.addNeedle(branch, needleB);
        treeService.addBranch(pine, branch);
        return pine;
    }
}
