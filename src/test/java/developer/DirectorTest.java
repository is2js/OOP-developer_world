package developer;

import developer.paper.Client;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DirectorTest {
    @DisplayName("")
    @Test
    void name() {
        final Director director = new Director();
        director.addProject("client", new Client());
        director.runProject("client");
    }
}
