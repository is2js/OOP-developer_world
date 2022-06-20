package developer;

import developer.domain.Director;
import developer.domain.Program;
import developer.domain.paper.Client;
import developer.domain.paper.ServerClient;
import developer.domain.progammer.BackEnd;
import developer.domain.progammer.FrontEnd;

public class Main {
    public static void main(String[] args) {
        final String frontProject = "프론트 개편";
        final String backendProject = "백엔드 프로젝트";

        final Director director = new Director();

        director.addProject(frontProject, new Client() {
            @Override
            public Program[] generateProgram() {
                final FrontEnd frontEnd = new FrontEnd<Client>() {
                    @Override
                    protected void setData(final Client paper) {
                        language = paper.getLanguage();
                        library = paper.getLibrary();
                    }
                };
                setProgrammer(frontEnd);
                final Program program = frontEnd.getProgram(this);
                return new Program[]{program};
            }
        });
        director.runProject(frontProject);

        director.addProject(backendProject, new ServerClient() {
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
        });
        director.runProject(backendProject);
    }
}
