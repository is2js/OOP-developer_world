package developer.paper;

import developer.Language;
import developer.Library;
import developer.progammer.FrontEnd;
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
        // 공통기능이 없는 (정보가 적은)추상체를 넘겨받으면, LSP위반으로 instanceof로 물어봐서 개별 구상체의 기능을 이용해야한다.
        if (programmer instanceof FrontEnd) {
            final FrontEnd frontEnd = (FrontEnd) programmer;
            frontEnd.setLanguage(language);
            frontEnd.setLibrary(library);
        }
    }
}
