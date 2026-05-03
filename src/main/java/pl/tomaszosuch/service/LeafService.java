package pl.tomaszosuch.service;

import pl.tomaszosuch.model.Leaf;

public interface LeafService {

    // Metoda do tworzenia liścia
    Leaf createLeaf(double size, boolean green);

    // Metoda do zmiany koloru liścia
    void reactToSeasonChange(Leaf leaf, String season);

    // Metoda do opadania liścia
    void fallOff(Leaf leaf);
}
