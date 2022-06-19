package developer.domain.progammer;

import developer.domain.Program;
import developer.domain.paper.Paper;

public abstract class Programmer<T extends Paper> {
    public Program getProgram(final T paper) {
        setData(paper);

        return makeProgram();
    }

    protected abstract void setData(T paper);

    protected abstract Program makeProgram();
}
