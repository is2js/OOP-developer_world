package developer.domain;

public class Program {
    private final String name;

    public Program(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Program{" +
            "name='" + name + '\'' +
            '}';
    }
}
