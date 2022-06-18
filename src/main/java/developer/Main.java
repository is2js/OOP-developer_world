package developer;

import developer.paper.Client;
import developer.paper.ServerClient;
import developer.progammer.BackEnd;
import developer.progammer.FrontEnd;

public class Main {
    public static void main(String[] args) {
        final Director director = new Director();

        director.addProject("여행사A 프론트 개편", new Client() {
            @Override
            public Program[] run() {
                final FrontEnd<Client> frontEnd = new FrontEnd<>() {
                    @Override
                    protected void setData(final Client paper) {
                        library = paper.getLibrary();
                        language = paper.getLanguage();
                    }
                };

                programmer = frontEnd;
                final Program program = frontEnd.getProgram(this);
                return new Program[]{program};
            }
        });

        director.runProject("여행사A 프론트 개편");

        director.addProject("xx은행 리뉴얼", new ServerClient() {
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

                frontEndProgrammer = frontEnd;
                backEndProgrammer = backEnd;

                return new Program[]{frontEnd.getProgram(this), backEnd.getProgram(this)};
            }
        });

        director.runProject("xx은행 리뉴얼");
    }

}
