package pl.tomaszosuch.service;

import pl.tomaszosuch.model.Needle;

public interface NeedleService {

    // Metoda do tworzenia igły
    Needle createNeedle(double surfaceArea);

    // Metoda do określenia reakcji igły na zmianę sezonu
    Needle createNeedle(double surfaceArea, int lifespanMonths);

    // Metoda do zmiany stanu igły w zależności od sezonu
    void reactToSeasonChange(Needle needle, String season);

    // Metoda do określenia, czy igła powinna zostać zastąpiona (np. w wyniku starzenia się)
    boolean shouldBeReplaced(Needle needle);

}
