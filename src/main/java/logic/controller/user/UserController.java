package logic.controller.user;

import exception.BadCredentialException;
import exception.InvalidUsernameException;
import exception.NotFoundException;
import exception.user.NoLoggedUser;
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
     * References a user if connected. Null otherwise.
     */
    private User loggedUser;

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

    private void setLoggedUser(User user) {
        System.out.println("The logged user is now " + user.getUsername());
        this.loggedUser = user;
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
            setLoggedUser(user);
            return user;
        }
        else {
            // The password isn't correct.
            throw new BadCredentialException();
        }
    }

    /**
     * Hash a password.
     * @param password The password to hash.
     * @return The hashed password.
     */
    private String getHashedPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(15));
    }

    /**
     * Check if the password is correct.
     *
     * @param password The password to check.
     * @param hashedPassword The hashed password to check.
     * @return True if the password is correct, false otherwise.
     */
    private boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    /**
     * Find one user by its username.
     *
     * @param username The username of the user to find.
     * @return The user if found. Otherwise, throw a NotFoundException.
     * @throws NotFoundException if the user is not found.
     */
    public User findOneByUsername(String username) throws NotFoundException {
        User user = getDao().findOneByUsername(username);
        user.handleOnFind();
        return user;
    }

    /**
     * Return true if the user logged user is admin.
     *
     * @return True if the user is an admin. Otherwise, false.
     */
    public boolean isAdmin() {
        if (loggedUser == null) {
            return false;
        }
        else {
            return loggedUser.isAdmin();
        }
    }

    /**
     * Return the logged user. Throw a NoLoggedUser exception if no user is logged.
     *
     * @return The logged user.
     * @throws NoLoggedUser if there is no user logged.
     */
    public User getLoggedUser() throws NoLoggedUser {
        if (loggedUser == null) {
            throw new NoLoggedUser();
        }
        else {
            return loggedUser;
        }
    }

    /**
     * Delete a user by its username.
     *
     * @param username The username of the user to delete.
     * @return true if the user has been deleted, false otherwise.
     */
    public boolean deleteOne(String username) {
        return getDao().deleteOne(username);
    }
}
