import developer.paper.Client;
import developer.progammer.FrontEnd;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProgrammerTest {
    @DisplayName("")
    @Test
    void name() {
        final FrontEnd frontEnd = new FrontEnd();
        frontEnd.getProgram(new Client());
    }
}
