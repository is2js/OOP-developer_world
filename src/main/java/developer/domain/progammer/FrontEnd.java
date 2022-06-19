package developer.domain.progammer;

import developer.domain.Language;
import developer.domain.Library;
import developer.domain.Program;
import developer.domain.paper.Client;

public class FrontEnd  extends Programmer<Client> {

    private Language language;
    private Library library;

    @Override
    protected void setData(final Client paper) {
            language = paper.getLanguage();
            library = paper.getLibrary();
    }

    @Override
    protected Program makeProgram() {
        return new Program("프론트 프로그램");
    }
}
