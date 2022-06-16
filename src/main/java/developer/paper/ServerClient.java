package developer.paper;

import developer.Language;
import developer.Server;
import developer.progammer.BackEnd;
import developer.progammer.FrontEnd;
import developer.progammer.Programmer;

public class ServerClient implements Paper {

    private final Server server = new Server("test");
    private final Language backEndLanguage = new Language("vueJs");
    private final Language frontEndLanguage = new Language("kotlinJS");


    private Programmer frontEndProgrammer;
    private Programmer backEndProgrammer;

    public void setFrontEndProgrammer(final Programmer programmer) {
        this.frontEndProgrammer = frontEndProgrammer;
    }

    public void setBackEndProgrammer(final Programmer programmer) {
        this.backEndProgrammer = backEndProgrammer;
    }


    @Override
    public void setData(final Programmer programmer) {
        // 2개의 instanceof는 제네릭으로 형을 좁혀 치환할 수 없다.
        // 1개의 paper는 여러 programmer를 다룰 수 있기 때문에, 1쪽에서는 N을 받으면 안된다.
        // -> programmer(N)쪽에서 paper(1)를 넘겨받아 의존/사용/알게한다
        if (programmer instanceof FrontEnd) {
            final FrontEnd frontEnd = (FrontEnd) programmer;
            frontEnd.setLanguage(frontEndLanguage);
        }

        if (programmer instanceof BackEnd) {
            final BackEnd backEndProgrammer = (BackEnd) programmer;
            backEndProgrammer.setLanguage(backEndLanguage);
            backEndProgrammer.setServer(server);
        }

    }
}
