package developer.domain.progammer;

import developer.domain.Language;
import developer.domain.Library;
import developer.domain.Program;
import developer.domain.paper.Client;
import developer.domain.paper.Paper;

public class FrontEnd  extends Programmer {

    private Language language;
    private Library library;

    @Override
    protected void setData(final Paper paper) {
        if (paper instanceof Client) {
            final Client pb = (Client) paper;
            language = pb.getLanguage();
            library = pb.getLibrary();
        }
    }

    @Override
    protected Program makeProgram() {
        return new Program("프론트 프로그램");
    }
}
