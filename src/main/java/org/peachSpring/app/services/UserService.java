package org.peachSpring.app.services;

import org.peachSpring.app.exceptions.CannotDeleteUserException;
import org.peachSpring.app.exceptions.UserNotFoundException;
import org.peachSpring.app.models.User;
import org.peachSpring.app.repositories.UsersRepository;
import org.peachSpring.app.security.UsersDetails;
import org.peachSpring.app.util.advanced_search.UserFilterSearcherChain;
import org.peachSpring.app.util.search_config.UserSearchConfig;
import org.peachSpring.app.util.search_config.constants.UserFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UsersRepository usersRepository;

    @Autowired
    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<User> findAll() {
        return usersRepository.findAll();
    }

    public List<User> findAll(UserSearchConfig userSearchConfig) {


        List<User> list = new UserFilterSearcherChain(userSearchConfig)
                .searcherManager(usersRepository.findAll())
                .stream()
                .skip(userSearchConfig.getNumberOfPage() * userSearchConfig.getItemsPerPage())
                .toList();
        /*List<User> list = usersRepository.findAll(PageRequest.of(
                        userSearchConfig.getNumberOfPage(),
                        userSearchConfig.getItemsPerPage())).
                getContent();*/
        return list.subList(0, Math.min(list.size(), userSearchConfig.getItemsPerPage()));
    }

    public User findOne(long id) throws UserNotFoundException {
        Optional<User> user = usersRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException(String.format("User with id %d isn`t found", id));
        }
    }

    @Transactional
    public void save(User user) {
        usersRepository.save(user);
    }

    @Transactional
    public void update(long id, User updatedUser) {
        updatedUser.setId(id);
        usersRepository.save(updatedUser);

    }

    @Transactional
    public void deleteById(long id) throws CannotDeleteUserException {
        User curUser = usersRepository.getOne(id);
        if (!curUser.isHasBook()) {
            usersRepository.deleteById(id);
        } else {
            throw new CannotDeleteUserException(
                    String.format("User with id %d cannot be deleted `cause one has a book", id));
        }

    }

    public Optional<User> findOneByNameIgnoreCase(String name) {
        return usersRepository.findFirstByNameIgnoreCase(name);
    }

    public Optional<User> findOneByLogin(String login) {
        return usersRepository.findByLogin(login);
    }
    public static User getCurrentUsersPrinciples(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsersDetails usersDetails = (UsersDetails) authentication.getPrincipal();
        return usersDetails.getOrigin();
    }

}
