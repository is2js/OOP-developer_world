package developer.progammer;

import developer.Language;
import developer.Program;
import developer.Server;

public class BackEnd extends Programmer {

    private Server server;

    private Language language;

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
