package developer.paper;

import developer.Language;
import developer.Server;
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

    public Server getServer() {
        return server;
    }

    public Language getBackEndLanguage() {
        return backEndLanguage;
    }

    public Language getFrontEndLanguage() {
        return frontEndLanguage;
    }

    public Programmer getFrontEndProgrammer() {
        return frontEndProgrammer;
    }

    public Programmer getBackEndProgrammer() {
        return backEndProgrammer;
    }

    @Override
    public void setData(final Programmer programmer) {
        programmer.setLanguage(backEndLanguage);
        programmer.setServer(server);
    }
}
