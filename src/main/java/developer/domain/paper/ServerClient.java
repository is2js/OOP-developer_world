package developer.domain.paper;

import developer.domain.Language;
import developer.domain.Program;
import developer.domain.Server;
import developer.domain.progammer.BackEnd;
import developer.domain.progammer.FrontEnd;
import developer.domain.progammer.Programmer;

public class ServerClient implements Paper {

    private final Server server = new Server("test");
    private final Language backEndLanguage = new Language("vueJs");
    private final Language frontEndLanguage = new Language("kotlinJS");

    private Programmer frontEndProgrammer;
    private Programmer backEndProgrammer;

    @Override
    public Program[] generateProgram() {
        // 명세마다 필요한 프로그래머(들)
        final FrontEnd frontEnd = new FrontEnd<ServerClient>() {
            @Override
            protected void setData(final ServerClient paper) {
                language = paper.getFrontEndLanguage();
            }
        };

        final BackEnd backEnd = new BackEnd<ServerClient>() {
            @Override
            protected void setData(final ServerClient paper) {
                server = paper.getServer();
                language = paper.getBackEndLanguage();
            }
        };

        // 명세의 필드에 프로그램머 set시켜주기 (명세 나온뒤, 한참뒤에 주입된다고 했었음)
        setFrontEndProgrammer(frontEnd);
        setBackEndProgrammer(backEnd);

        // 각 프로그래머들에게 paper던져주며, program만들라고 시키기
        final Program client = frontEnd.getProgram(this);
        final Program server = backEnd.getProgram(this);
        return new Program[]{client, server};

    }

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
}
