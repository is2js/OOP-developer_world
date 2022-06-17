package developer.progammer;

import developer.Language;
import developer.Library;
import developer.Program;
import developer.paper.Paper;

public abstract class FrontEnd<T extends Paper> extends Programmer<T> {

    protected Language language;

    protected Library library;
    
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
