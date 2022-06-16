package developer.progammer;

import developer.Program;
import developer.paper.Paper;

public abstract class Programmer {
    public Program makeProgram(final Paper paper) {
        paper.setData(this);

        return makeProgram();
    }

    protected abstract Program makeProgram();
}
