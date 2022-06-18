package developer.domain.progammer;

import developer.domain.Language;
import developer.domain.Library;
import developer.domain.Program;
import developer.domain.paper.Client;
import developer.domain.paper.Paper;

public class FrontEnd implements Programmer {

    private Language language;
    private Library library;

    @Override
    public Program makeProgram(final Paper paper) {
        if (paper instanceof Client) {
            final Client pb = (Client) paper;
            language = pb.getLanguage();
            library = pb.getLibrary();
        }

        return makeFrontEndProgram("프론트 프로그램");
    }

    private Program makeFrontEndProgram(final String name) {
        return new Program(name);
    }
}
