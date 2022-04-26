package developer.progammer;

import developer.Language;
import developer.Program;
import developer.Server;
import developer.paper.Paper;
import developer.paper.ServerClient;

public class BackEnd implements Programmer {

    private Server server;
    private Language language;

    public Program makeProgram(final Paper paper) {
        if (paper instanceof ServerClient) {
            final ServerClient pa = (ServerClient) paper;
            server = pa.getServer();
            language = pa.getLanguage();
        }

        return makeBackEndProgram();
    }

    private Program makeBackEndProgram() {
        return new Program();
    }
}
