package developer.domain.paper;

import developer.domain.Language;
import developer.domain.Library;
import developer.domain.Program;
import developer.domain.progammer.FrontEnd;
import developer.domain.progammer.Programmer;

public class Client implements Paper {

    private final Language language = new Language("kotlinJS");
    private final Library library = new Library("vueJS");
    private Programmer programmer;

    @Override
    public Program[] generateProgram() {
        final FrontEnd frontEnd = new FrontEnd<Client>() {
            @Override
            protected void setData(final Client paper) {
                language = paper.getLanguage();
                library = paper.getLibrary();
            }
        };
        setProgrammer(frontEnd);
        final Program program = frontEnd.getProgram(this);
        return new Program[]{program};
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
