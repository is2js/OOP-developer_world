package developer.paper;

import developer.Language;
import developer.Library;
import developer.progammer.FrontEnd;
import developer.progammer.Programmer;

public class Client implements Paper<FrontEnd> {

    private final Language language = new Language("kotlinJS");
    private final Library library = new Library("vueJS");
    private Programmer programmer;

    @Override
    public void setData(final FrontEnd programmer) {
        programmer.setLanguage(language);
        programmer.setLibrary(library);
    }

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
