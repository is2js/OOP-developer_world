package developer.paper;

import developer.Language;
import developer.Program;
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

    @Override
    public Program[] run() {
        // 나온 명세마다 필요한 프로그래머(들)
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

        // 명세의 필드에 프로그래머 set시켜주기 (명세 나온뒤, 한참뒤에 주입된다고 했었음)
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
