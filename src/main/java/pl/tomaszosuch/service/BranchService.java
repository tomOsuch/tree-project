package pl.tomaszosuch.service;

import pl.tomaszosuch.model.Branch;
import pl.tomaszosuch.model.Leaf;
import pl.tomaszosuch.model.Needle;

public interface BranchService {

    // Tworzy nową gałąź o określonej długości i poziomie rozgałęzienia
    Branch createBranch(double length, int branchingLevel);

    // Dodaje liść do gałęzi (dotyczy drzew liściastych)
    void addLeaf(Branch branch, Leaf leaf);
    
    // Dodaje igłę do gałęzi (dotyczy drzew iglastych)
    void addNeedle(Branch branch, Needle needle);

    // Symuluje wzrost gałęzi o określoną długość
    void grow(Branch branch, double deltaBranchLength);

    // Symuluje reakcję gałęzi na zmianę sezonu (np. wiosna, lato, jesień, zima)
    void reactToSeasonChange(Branch branch, String season);

    // Symuluje opadnie liści
    void deactivateAllOrgans(Branch branch);

    // Liczy aktywne organy (liście lub igły) na gałęzi
    int countActivateOrgans(Branch branch);

    // Sprawdza, czy gałąź ma jakiekolwiek liście (dotyczy drzew liściastych)
    boolean hasAnyLeaf(Branch branch);

    // Sprawdza, czy gałąź ma jakiekolwiek igły (dotyczy drzew iglastych)
    boolean hasAnyNeedle(Branch branch);
}
