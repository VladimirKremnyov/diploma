package diploma.dao;

import diploma.entity.User;

import java.util.List;

public interface UserDao {
    User addUser(User user);

    List<User> getAllUsers();

    User getUserByUserId(long id);

    void updateUserName(User user, String name);

    void updateUserAge(User user, int age);

    void deleteUser(User user);
}
