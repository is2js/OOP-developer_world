package developer.progammer;

import developer.Language;
import developer.Program;
import developer.Server;
import developer.paper.ServerClient;

public class BackEnd extends Programmer<ServerClient> {

    private Server server;

    private Language language;

    protected void setData(final ServerClient paper) {
        server = paper.getServer();
        language = paper.getBackEndLanguage();
    }

    @Override
    protected Program makeProgram() {
        return new Program();
    }

    public void setLanguage(final Language language) {
        this.language = language;
    }

    public void setServer(final Server server) {
        this.server = server;
    }
}
