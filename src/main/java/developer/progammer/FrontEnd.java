package developer.progammer;

import developer.Language;
import developer.Library;
import developer.Program;
import developer.paper.Client;

public class FrontEnd extends Programmer<Client> {

    private Language language;

    private Library library;

    protected void setData(final Client paper) {
        language = paper.getLanguage();
        library = paper.getLibrary();
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
