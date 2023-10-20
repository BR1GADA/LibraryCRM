package org.peachSpring.app.util.search_config;

import org.peachSpring.app.util.search_config.constants.BookFilter;
import org.peachSpring.app.util.search_config.constants.UserFilter;

public class UserSearchConfig extends SearchConfig{
    public UserSearchConfig() {
    }
    private UserFilter filter;

    public UserFilter getFilter() {
        return filter;
    }

    public void setFilter(UserFilter filter) {
        this.filter = filter;
    }
}
