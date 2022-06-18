package developer.paper;

import developer.Language;
import developer.Library;
import developer.progammer.Programmer;

public abstract class Client implements Paper {

    private final Language language = new Language("kotlinJS");
    private final Library library = new Library("vueJS");
    protected Programmer programmer;

    public Language getLanguage() {
        return language;
    }

    public Library getLibrary() {
        return library;
    }
}
