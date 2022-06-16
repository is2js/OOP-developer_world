package developer.progammer;

import developer.Language;
import developer.Program;
import developer.Server;
import developer.paper.Paper;
import developer.paper.ServerClient;

public class BackEnd extends Programmer {

    private Server server;

    private Language language;

    @Override
    protected void setData(final Paper paper) {
        if (paper instanceof ServerClient) {
            final ServerClient pa = (ServerClient) paper;
            server = pa.getServer();
            language = pa.getBackEndLanguage();
        }
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
