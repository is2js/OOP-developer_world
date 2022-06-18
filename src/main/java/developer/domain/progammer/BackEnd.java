package developer.domain.progammer;

import developer.domain.Language;
import developer.domain.Program;
import developer.domain.Server;
import developer.domain.paper.Paper;
import developer.domain.paper.ServerClient;

public class BackEnd implements Programmer {

    private Server server;
    private Language language;

    @Override
    public Program makeProgram(final Paper paper) {
        if (paper instanceof ServerClient) {
            final ServerClient pa = (ServerClient) paper;
            server = pa.getServer();
            language = pa.getBackEndLanguage();
        }

        return makeBackEndProgram("백엔드 프로그램");
    }

    private Program makeBackEndProgram(final String name) {
        return new Program(name);
    }
}
