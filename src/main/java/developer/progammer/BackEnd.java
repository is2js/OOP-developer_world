package developer.progammer;

import developer.Language;
import developer.Library;
import developer.Program;
import developer.Server;
import developer.paper.Paper;

public class BackEnd implements Programmer {

    private Server server;

    private Language language;

    @Override
    public Program makeProgram(final Paper paper) {
        paper.setData(this);
//        if (paper instanceof ServerClient) {
//            final ServerClient pa = (ServerClient) paper;
//            server = pa.getServer();
//            language = pa.getBackEndLanguage();
//        }

        return makeBackEndProgram();
    }

    private Program makeBackEndProgram() {
        return new Program();
    }

    @Override
    public void setLanguage(final Language language) {
        this.language = language;
    }

    @Override
    public void setLibrary(final Library library) {
        //????
        throw new UnsupportedOperationException("BackEnd#setLibrary not implemented.");
    }

    @Override
    public void setServer(final Server server) {
        this.server = server;
    }
}
