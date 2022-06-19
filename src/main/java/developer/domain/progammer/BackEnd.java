package developer.domain.progammer;

import developer.domain.Language;
import developer.domain.Program;
import developer.domain.Server;
import developer.domain.paper.Paper;

public abstract class BackEnd<T extends Paper> extends Programmer<T> {

    protected Server server;
    protected Language language;

    @Override
    protected Program makeProgram() {
        return new Program("백엔드 프로그램");
    }
}
