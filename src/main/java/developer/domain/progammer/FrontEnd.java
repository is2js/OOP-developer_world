package developer.domain.progammer;

import developer.domain.Language;
import developer.domain.Library;
import developer.domain.Program;
import developer.domain.paper.Paper;

public abstract class FrontEnd<T extends Paper> extends Programmer<T> {

    protected Language language;
    protected Library library;

    @Override
    protected Program makeProgram() {
        return new Program("프론트 프로그램");
    }
}
