package developer;

import developer.paper.Client;
import developer.paper.Paper;
import developer.paper.ServerClient;
import developer.progammer.BackEnd;
import developer.progammer.FrontEnd;
import java.util.HashMap;

public class Director {
    private HashMap<String, Paper> projects = new HashMap<>();

    public void addProject(final String name, final Paper paper) {
        projects.put(name, paper);
    }

    public void runProject(final String name) {
        // 실행할 해당 명세가 있나 확인한다. 없으면 에러를 낸다.
        if (projects.containsKey(name)) {
            throw new RuntimeException("no project");
        }
        // 명세가 있으면 각 명세종류에 따라 실행시킨다.
        // -> 모든 구상체들의 공통메서드인 전략메서드가 없으면, 각 구상체마다 확인해서 각각 시켜야한다.
        final Paper paper = projects.get(name);

        //1) ServerClient 명세인 경우
        if (paper instanceof ServerClient) {
            // 명세 
            final ServerClient project = (ServerClient) paper;

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
            project.setFrontEndProgrammer(frontEnd);
            project.setBackEndProgrammer(backEnd);

            // 각 프로그래머들에게 paper던져주며, program만들라고 시키기
            final Program client = frontEnd.getProgram(project);
            final Program server = backEnd.getProgram(project);

            // 자신만의 메서드를 통해, 내부필드값을 이용해, 여기서 deploy시키기
            // -> 완성된 프로그램들을 name과 함께 deploy
            // --> 프론트명세는 2개, 백엔드 명세는 1개의 프로그램 -> 맨 마지막 인자로 두고, 1~2 유동적으로 받을 수 잇는 가변인자로 주기
            deploy(name, client, server);
        }

        //2) Client 명세인 경우
        if (paper instanceof Client) {
            final Client project = (Client) paper;
            final FrontEnd frontEnd = new FrontEnd<Client>() {
                @Override
                protected void setData(final Client paper) {
                    language = paper.getLanguage();
                    library = paper.getLibrary();
                }
            };
            project.setProgrammer(frontEnd);
            final Program program = frontEnd.getProgram(project);
            deploy(name, program);
        }
    }

    private void deploy(final String projectName, final Program... programs) {
    }
}
