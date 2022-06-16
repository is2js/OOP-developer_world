package developer.progammer;

import developer.Language;
import developer.Library;
import developer.Program;
import developer.Server;
import developer.paper.Paper;

public interface Programmer {

    Program makeProgram(Paper paper);

    void setLanguage(Language language);

    void setLibrary(Library library);

    void setServer(Server server);
}
