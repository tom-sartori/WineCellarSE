package logic.controller.user;

import exception.BadCredentialException;
import exception.InvalidUsernameException;
import exception.NotFoundException;
import logic.controller.AbstractController;
import org.bson.types.ObjectId;
import org.mindrot.jbcrypt.BCrypt;
import persistence.dao.user.UserDao;
import persistence.entity.user.User;

/**
 * UserController class extending Controller class parametrized with User class.
 */
public class UserController extends AbstractController<User> {

    /**
     * Instance of UserController to ensure Singleton design pattern.
     */
    private static UserController instance;

    /**
     * Private constructor for UserController to ensure Singleton design pattern.
     */
    private UserController() { }

    /**
     * @return the instance of UserController to ensure Singleton design pattern.
     */
    public static UserController getInstance() {
        if(instance == null){
            instance = new UserController();
        }
        return instance;
    }

    /**
     * @return the DAO of the specific Controller (UserDao).
     */
    @Override
    protected UserDao getDao() {
        return UserDao.getInstance();
    }

    /**
     * Try to register a new user.
     *
     * @param user The user to register.
     * @return The id of the registred user.
     * @throws InvalidUsernameException if the username is already taken.
     */
    public ObjectId register(User user) throws InvalidUsernameException {
        user.handleOnCreate();
        user.setPassword(getHashedPassword(user.getPassword()));
        return getDao().insertOne(user);
    }

    /**
     * Try to log in a user.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The user logged in.
     */
    public User login(String username, String password) throws BadCredentialException {
        User user;
        try {
            user = getDao().findOneByUsername(username);
        }
        catch (NotFoundException e) {
            throw new BadCredentialException();
        }

        if (checkPassword(password, user.getPassword())) {
            // The password is correct.
            return user;
        }
        else {
            // The password isn't correct.
            throw new BadCredentialException();
        }
    }

    private String getHashedPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(15));
    }

    private boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public User findOneByUsername(String username) throws NotFoundException {
        User user = getDao().findOneByUsername(username);
        user.handleOnFind();
        return user;
    }
}
