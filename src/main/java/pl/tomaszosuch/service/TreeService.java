package pl.tomaszosuch.service;

import pl.tomaszosuch.model.Branch;
import pl.tomaszosuch.tree.Tree;

public interface TreeService {

    // Metoda dodająca gałąź do drzewa
    void addBranch(Tree tree, Branch branch);

    // Metoda symulująca wzrost drzewa
    void grow(Tree tree);

    // Metoda reagująca na zmianę pory roku
    void reactionToSeasonChange(Tree tree, String season);

    // Metoda symulująca cykl sezonowy drzewa
    void runSeasoncycle(Tree tree);

    // Metoda budująca podsumowanie drzewa
    String buildSummary(Tree tree);

}
