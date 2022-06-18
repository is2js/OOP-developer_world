package developer;

import developer.paper.Client;
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
    }
}
