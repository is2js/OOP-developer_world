package developer;

import developer.domain.Director;
import developer.domain.paper.Client;

public class Main {
    public static void main(String[] args) {
        final String frontProject = "프론트 개편";
        final String backendProject = "백엔드 프로젝트";

        final Director director = new Director();

        director.addProject(frontProject, new Client());
        director.runProject(frontProject);

        director.addProject(backendProject, new Client());
        director.runProject(backendProject);
    }
}
