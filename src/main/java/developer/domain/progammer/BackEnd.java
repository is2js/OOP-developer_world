package developer.domain.progammer;

import developer.domain.Language;
import developer.domain.Program;
import developer.domain.Server;
import developer.domain.paper.ServerClient;

public class BackEnd extends Programmer<ServerClient> {

    private Server server;
    private Language language;

    @Override
    protected void setData(final ServerClient paper) {
        server = paper.getServer();
        language = paper.getBackEndLanguage();
    }

    @Override
    protected Program makeProgram() {
        return new Program("백엔드 프로그램");
    }
}
