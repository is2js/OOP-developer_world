package developer.paper;

import developer.Language;
import developer.Library;
import developer.progammer.Programmer;

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

    @Override
    public void setData(final Programmer programmer) {
        programmer.setLanguage(language);
        programmer.setLibrary(library);
    }
}
