package developer.progammer;

import developer.Language;
import developer.Library;
import developer.Program;

public class FrontEnd extends Programmer {

    private Language language;

    private Library library;

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
