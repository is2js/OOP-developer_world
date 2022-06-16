package developer.progammer;

import developer.Program;
import developer.paper.Paper;

public abstract class Programmer {
    public Program makeProgram(final Paper paper) {
        setData(paper);
        return makeProgram();
    }

    protected abstract void setData(final Paper paper);

    protected abstract Program makeProgram();
}
