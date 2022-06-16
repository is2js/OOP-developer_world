package developer.paper;

import developer.progammer.Programmer;

public interface Paper<T extends Programmer> {

    void setData(T programmer);
}
