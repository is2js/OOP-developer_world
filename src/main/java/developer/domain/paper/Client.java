package developer.domain.paper;

import developer.domain.Language;
import developer.domain.Library;
import developer.domain.progammer.Programmer;

public class Client implements Paper {

    private final Language language = new Language("kotlinJS");
    private final Library library = new Library("vueJS");
    private Programmer programmer;

    public void setProgrammer(final Programmer programmer) {
        this.programmer = programmer;
    }

    public Language getLanguage() {
        return language;
    }

    public Library getLibrary() {
        return library;
    }

    public Programmer getProgrammer() {
        return programmer;
    }
}
