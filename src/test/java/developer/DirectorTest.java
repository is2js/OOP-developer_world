package developer;

import developer.paper.Client;
import developer.paper.ServerClient;
import developer.progammer.BackEnd;
import developer.progammer.FrontEnd;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DirectorTest {
    @DisplayName("")
    @Test
    void name() {
        final Director director = new Director();
        director.addProject("client", new Client() {
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
        });
        director.runProject("serverClient");
        director.addProject("serverClient", new ServerClient() {
            @Override
            public Program[] run() {
                final FrontEnd<ServerClient> frontEnd = new FrontEnd<>() {
                    @Override
                    protected void setData(final ServerClient paper) {
                        language = paper.getFrontEndLanguage();
                    }
                };
                final BackEnd<ServerClient> backEnd = new BackEnd<>() {
                    @Override
                    protected void setData(final ServerClient paper) {
                        language = paper.getBackEndLanguage();
                        server = paper.getServer();
                    }
                };

                setFrontEndProgrammer(frontEnd);
                setBackEndProgrammer(backEnd);

                final Program client = frontEnd.getProgram(this);
                final Program server = backEnd.getProgram(this);
                return new Program[]{client, server};
            }
        });
        director.runProject("client");
    }
}
