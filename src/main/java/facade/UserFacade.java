package facade;

import exception.BadCredentialException;
import exception.InvalidUsernameException;
import exception.NotFoundException;
import exception.user.MustBeAnAdminException;
import exception.user.NoLoggedUser;
import logic.controller.user.UserController;
import org.bson.types.ObjectId;
import persistence.entity.user.User;

import java.util.List;

/**
 * Specific facade for Users.
 */
class UserFacade {

    /**
     * Singleton instance.
     */
    private static UserFacade instance;

    private UserFacade() { }

    /**
     * Get the singleton instance of the user facade.
     *
     * @return The singleton instance.
     */
    public static UserFacade getInstance() {
        if (instance == null) {
            instance = new UserFacade();
        }
        return instance;
    }

    /**
     * Register a user.
     *
     * @param user The user to register.
     * @return The id of the inserted user.
     * @throws InvalidUsernameException if the username is already taken.
     */
    protected ObjectId register(User user) throws InvalidUsernameException {
        return UserController.getInstance().register(user);
    }

    /**
     * Login a user.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The user if the login is successful.
     * @throws BadCredentialException if the credentials are invalid.
     */
    protected User login(String username, String password) throws BadCredentialException {
        return UserController.getInstance().login(username, password);
    }

    /**
     * Get all users.
     *
     * @return A list of users.
     */
    protected List<User> getUserList() {
        return UserController.getInstance().findAll();
    }

    /**
     * Get a user by its id.
     *
     * @param id The id of the user.
     * @return The user or null if not found.
     */
    protected User getOneUser(ObjectId id) {
        return UserController.getInstance().findOne(id);
    }

    /**
     * Get the logged user.
     *
     * @return The logged user.
     * @throws NoLoggedUser if there is no user logged.
     */
    protected User getLoggedUser() throws NoLoggedUser {
        return UserController.getInstance().getLoggedUser();
    }

    /**
     * Get a user by its username.
     *
     * @param username The username of the user.
     * @return The user or null if not found.
     */
    protected User getOneUserByUsername(String username) throws NotFoundException {
        return UserController.getInstance().findOneByUsername(username);
    }

    /**
     * Update a user.
     *
     * @param id The id of the user to update.
     * @param user The new user.
     * @return true if the user has been updated, false otherwise.
     */
    protected boolean updateOneUser(ObjectId id, User user) {
        return UserController.getInstance().updateOne(id, user);
    }

    /**
     * Delete a user by its username.
     *
     * @param username The username of the user to delete.
     * @return true if the user has been deleted, false otherwise.
     * @throws MustBeAnAdminException if the user is not an admin.
     */
    protected boolean deleteOneUser(String username) throws MustBeAnAdminException {
        return UserController.getInstance().deleteOne(username);
    }

    /**
     * Check if the user logged in is an admin.
     *
     * @return true if the user is an admin, false otherwise.
     */
    protected boolean isLoggedUserAdmin() {
        return UserController.getInstance().isAdmin();
    }

    /**
     * Logout the logged user.
     */
    public void logout() {
        UserController.getInstance().logout();
    }
}
