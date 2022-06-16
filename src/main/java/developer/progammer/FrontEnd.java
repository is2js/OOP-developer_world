package developer.progammer;

import developer.Language;
import developer.Library;
import developer.Program;
import developer.paper.Client;
import developer.paper.Paper;

public class FrontEnd extends Programmer {

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
        return new Program();
    }


    public void setLanguage(final Language language) {
        this.language = language;
    }

    public void setLibrary(final Library library) {
        this.library = library;
    }
}
