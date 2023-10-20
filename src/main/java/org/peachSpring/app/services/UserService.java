package org.peachSpring.app.services;

import org.peachSpring.app.exceptions.CannotDeleteUserException;
import org.peachSpring.app.exceptions.UserNotFoundException;
import org.peachSpring.app.models.User;
import org.peachSpring.app.repositories.UsersRepository;
import org.peachSpring.app.util.search_config.UserSearchConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UsersRepository usersRepository;
    @Autowired
    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    public List<User> findAll(){
        return usersRepository.findAll();
    }
    public List<User> findAll(UserSearchConfig userSearchConfig){
        List<User> list = usersRepository.findAll(PageRequest.of(
                userSearchConfig.getNumberOfPage(),
                userSearchConfig.getItemsPerPage())).
                getContent();
        return list;
    }
    public User findOne(long id) throws UserNotFoundException{
        Optional<User> user = usersRepository.findById(id);
        if (user.isPresent()){
            return user.get();
        } else {
            throw new UserNotFoundException(String.format("User with id %d isn`t found", id));
        }
    }
    @Transactional
    public void save(User user){
        usersRepository.save(user);
    }
    @Transactional
    public void update(long id, User updatedUser){
        updatedUser.setId(id);
        usersRepository.save(updatedUser);

    }
    @Transactional
    public void deleteById(long id) throws CannotDeleteUserException{
        User curUser = usersRepository.getOne(id);
        if (!curUser.isHasBook()){
            usersRepository.deleteById(id);
        } else {
            throw new CannotDeleteUserException(
                    String.format("User with id %d cannot be deleted `cause one has a book", id));
        }

    }

    public Optional<User> findOneByNameIgnoreCase(String name){
        return usersRepository.findFirstByNameIgnoreCase(name);
    }

}
