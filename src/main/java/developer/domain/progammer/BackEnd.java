package developer.domain.progammer;

import developer.domain.Language;
import developer.domain.Program;
import developer.domain.Server;
import developer.domain.paper.Paper;
import developer.domain.paper.ServerClient;

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
        return new Program("백엔드 프로그램");
    }
}
