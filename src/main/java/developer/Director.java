package developer;

import developer.paper.Paper;
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
        final Program[] programs = paper.run();
        deploy(name, programs);
    }

    private void deploy(final String projectName, final Program... programs) {
    }
}
