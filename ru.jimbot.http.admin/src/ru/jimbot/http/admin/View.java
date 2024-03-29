package ru.jimbot.http.admin;

public interface View {

    /**
     * This method is called when the view is activated.
     */
    public abstract void activated(Object... params);

    /**
     * This method is called when the view is deactivated via the
     * {@link ViewHandler}.
     */
    public abstract void deactivated(Object... params);

    /**
     * Возвращает врагмент url для вызова данного представления.
     * @return
     */
    public abstract String getFragment();
}