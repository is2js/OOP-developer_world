package developer.domain.progammer;

import developer.domain.Program;
import developer.domain.paper.Paper;

public abstract class Programmer {
    public Program getProgram(final Paper paper) {
        setData(paper);

        return makeProgram();
    }

    protected abstract void setData(Paper paper);

    protected abstract Program makeProgram();
}
