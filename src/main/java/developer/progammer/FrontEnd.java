package developer.progammer;

import developer.Language;
import developer.Library;
import developer.Program;
import developer.paper.Client;
import developer.paper.Paper;

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

        return makeFrontEndProgram();
    }

    private Program makeFrontEndProgram() {
        return new Program();
    }
}
