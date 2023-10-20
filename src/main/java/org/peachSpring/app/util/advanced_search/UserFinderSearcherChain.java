package org.peachSpring.app.util.advanced_search;

import org.peachSpring.app.models.User;
import org.peachSpring.app.util.search_config.SearchConfig;
import org.peachSpring.app.util.search_config.UserSearchConfig;

import java.util.List;
import java.util.stream.Collectors;

public class UserFinderSearcherChain extends SearcherChain<User>{
    public UserFinderSearcherChain(UserSearchConfig searchConfig) {
        super(searchConfig);
    }

    @Override
    public void setNextSearcherChain(SearcherChain<User> searcherChain) {
        super.setNextSearcherChain(searcherChain);
    }

    @Override
    protected List<User> getItems(List<User> list) {
        if (this.searchConfig.getStringToFind()!=null){
            return list.stream().filter(o->o.getName().
                    toLowerCase().
                    contains(this.searchConfig.getStringToFind().toLowerCase())).
                    collect(Collectors.toList());
        } else {
            return list;
        }

    }


}
