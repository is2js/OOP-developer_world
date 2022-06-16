package developer.progammer;

import developer.Language;
import developer.Program;
import developer.Server;
import developer.paper.Paper;

public class BackEnd implements Programmer {

    private Server server;

    private Language language;

    @Override
    public Program makeProgram(final Paper paper) {
        paper.setData(this);

        return makeBackEndProgram();
    }

    private Program makeBackEndProgram() {
        return new Program();
    }

    public void setLanguage(final Language language) {
        this.language = language;
    }

    public void setServer(final Server server) {
        this.server = server;
    }
}
