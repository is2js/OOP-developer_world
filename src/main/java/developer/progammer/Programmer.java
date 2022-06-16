package developer.progammer;

import developer.Program;
import developer.paper.Paper;

public abstract class Programmer<T extends Paper> {
    public Program getProgram(final T paper) {
        setData(paper);
        return makeProgram();
    }

    protected abstract void setData(final T paper);

    protected abstract Program makeProgram();
}
