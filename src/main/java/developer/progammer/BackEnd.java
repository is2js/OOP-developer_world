package developer.progammer;

import developer.Language;
import developer.Program;
import developer.Server;
import developer.paper.Paper;

public abstract class BackEnd<T extends Paper> extends Programmer<T> {

    protected Server server;

    protected Language language;

    protected abstract void setData(final T paper);

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
