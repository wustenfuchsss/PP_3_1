package ru.katapp.springbootcrudcfg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.katapp.springbootcrudcfg.dao.UserDAO;
import ru.katapp.springbootcrudcfg.entity.User;

import java.util.List;


@Service
public class UserServiceImp implements UserService {

    private final UserDAO userDao;

    @Autowired
    public UserServiceImp(UserDAO userDao) {
        this.userDao = userDao;
    }


    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public List<User> listAll() {
        return userDao.listAll();
    }

    @Override
    public User find(Long id) {
        return userDao.find(id);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }
}
