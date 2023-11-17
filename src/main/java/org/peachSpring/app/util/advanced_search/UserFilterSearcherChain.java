package org.peachSpring.app.util.advanced_search;

import org.peachSpring.app.models.User;
import org.peachSpring.app.util.search_config.UserSearchConfig;
import org.peachSpring.app.util.search_config.constants.UserFilter;

import java.util.List;
import java.util.stream.Collectors;

public class UserFilterSearcherChain extends SearcherChain<User>{
    public UserFilterSearcherChain(UserSearchConfig searchConfig) {
        super(searchConfig);
        this.setNextSearcherChain(new UserFinderSearcherChain(searchConfig));
    }

    /*@Override
    public void setNextSearcherChain(SearcherChain<User> searcherChain) {
        super.setNextSearcherChain(searcherChain);
    }*/

    @Override
    protected List<User> getItems(List<User> list) {
        if (searchConfig.getFilter() == null) {
            return list;
        }
        switch ((UserFilter)this.searchConfig.getFilter()){
            case IS_HAS_BOOK -> {
                return list.stream().filter(User::isHasPass).collect(Collectors.toList());
            }
            default -> {
                return list;
            }
        }
    }


}
