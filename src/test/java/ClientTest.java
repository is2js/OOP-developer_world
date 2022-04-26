import developer.paper.Client;
import developer.progammer.FrontEnd;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ClientTest {
    @DisplayName("")
    @Test
    void name() {
        final Client client = new Client();
        client.setProgrammer(new FrontEnd());
    }
}
