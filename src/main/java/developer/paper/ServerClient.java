package developer.paper;

import developer.Language;
import developer.Server;
import developer.progammer.Programmer;

public abstract class ServerClient implements Paper {

    private final Server server = new Server("test");
    private final Language backEndLanguage = new Language("vueJs");
    private final Language frontEndLanguage = new Language("kotlinJS");
    protected Programmer frontEndProgrammer;
    protected Programmer backEndProgrammer;
    
    public Server getServer() {
        return server;
    }

    public Language getBackEndLanguage() {
        return backEndLanguage;
    }

    public Language getFrontEndLanguage() {
        return frontEndLanguage;
    }
}
