package developer.progammer;

import developer.Language;
import developer.Library;
import developer.Program;
import developer.paper.Paper;

public class FrontEnd implements Programmer {

    private Language language;

    private Library library;

    @Override
    public Program makeProgram(final Paper paper) {
        paper.setData(this);

        return makeFrontEndProgram();
    }

    private Program makeFrontEndProgram() {
        return new Program();
    }

    public void setLanguage(final Language language) {
        this.language = language;
    }

    public void setLibrary(final Library library) {
        this.library = library;
    }
}
