package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Accounting extends ArrayList<Travel> {
    public void enterTravel(LocalDateTime start, LocalDateTime end) throws OnlyOneSimultaneousTravelAllowed {
        travelsMustNotOverlap(start, end);

        this.add(new Travel(start, end));
    }

    public void travelsMustNotOverlap(LocalDateTime start, LocalDateTime end) throws OnlyOneSimultaneousTravelAllowed {
        if (this.stream().anyMatch((travel) -> travel.start().compareTo(start) <= 0 && travel.end().compareTo(end) >= 0)) {
            throw new OnlyOneSimultaneousTravelAllowed();
        }
    }
}
