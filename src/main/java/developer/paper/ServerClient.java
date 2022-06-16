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
