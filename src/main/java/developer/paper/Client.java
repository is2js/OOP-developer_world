package developer.paper;

import developer.Language;
import developer.Library;
import developer.Program;
import developer.progammer.FrontEnd;
import developer.progammer.Programmer;

public class Client implements Paper {

    private final Language language = new Language("kotlinJS");
    private final Library library = new Library("vueJS");
    private Programmer programmer;

    @Override
    public Program[] run() {
        final FrontEnd frontEnd = new FrontEnd<Client>() {
            @Override
            protected void setData(final Client paper) {
                language = paper.getLanguage();
                library = paper.getLibrary();
            }
        };
        setProgrammer(frontEnd);
        return new Program[]{frontEnd.getProgram(this)};
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
}
