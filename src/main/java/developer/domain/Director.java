package developer.domain;

import developer.domain.paper.Paper;
import java.util.Arrays;
import java.util.HashMap;

public class Director {
    private HashMap<String, Paper> projects = new HashMap<>();

    public void addProject(final String name, final Paper paper) {
        projects.put(name, paper);
    }

    public void runProject(final String name) {
        if (!projects.containsKey(name)) {
            throw new RuntimeException("no project");
        }

        final Paper paper = projects.get(name);
        final Program[] programs = paper.generateProgram();

        deploy(name, programs);
    }

    private void deploy(final String projectName, final Program... programs) {
        System.out.printf("{0}이 deploy되었습니다.%n", projectName);
        System.out.printf("상세 프로그램은 아래와 같습니다.%n");
        Arrays.stream(programs)
            .forEach(p -> System.out.println(programs));

    }
}
