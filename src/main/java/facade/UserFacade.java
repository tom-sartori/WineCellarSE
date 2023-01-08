package facade;

import exception.BadCredentialException;
import exception.InvalidUsernameException;
import exception.NotFoundException;
import exception.user.MustBeAnAdminException;
import exception.user.NoLoggedUser;
import logic.controller.user.UserController;
import org.bson.types.ObjectId;
import persistence.entity.user.Friend;
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
     * Delete a user by its id.
     *
     * @param id The id of the user to delete.
     * @return true if the user has been deleted, false otherwise.
     */
    protected boolean deleteOneUser(ObjectId id) {
        return UserController.getInstance().deleteOne(id);
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
     * Check if there is a user logged in.
     *
     * @return true if there is a user logged in, false otherwise.
     */
    protected boolean isUserLogged() {
        return UserController.getInstance().isLogged();
    }

    /**
     * Check if the user logged in is an admin.
     *
     * @return true if the user is an admin, false otherwise.
     */
    protected boolean isAdminLogged() {
        return UserController.getInstance().isAdmin();
    }

    /**
     * Logout the logged user.
     */
    protected void logout() {
        UserController.getInstance().logout();
    }

    /**
     * Add a friend to the logged user.
     *
     * @param username of the friend to add.
     * @return the friend requested.
     * @throws NotFoundException if the friend is not found.
     * @throws NoLoggedUser if there is no user logged.
     */
    protected User addFriend(String username) throws NotFoundException, NoLoggedUser {
        return UserController.getInstance().addFriend(username);
    }

    /**
     * Accept a friend request.
     *
     * @param username of the friend to accept.
     * @throws NoLoggedUser if there is no user logged.
     */
    protected void acceptFriend(String username) throws NoLoggedUser {
        UserController.getInstance().acceptFriend(username);
    }

    /**
     * Remove a friend from the logged user.
     *
     * @param username of the friend to remove.
     * @return true if the friend has been removed, false otherwise.
     * @throws NoLoggedUser if there is no user logged.
     */
    protected boolean removeFriend(String username) throws NoLoggedUser {
        return UserController.getInstance().removeFriend(username);
    }

    /**
     * Return the list of friends of the logged user.
     *
     * @param onlyAcceptedFriend True if you want only the accepted friends. False if you want all the friends.
     * @return The list of friends of the logged user.
     * @throws NoLoggedUser if there is no user logged.
     */
    protected List<Friend> getFriendList(boolean onlyAcceptedFriend) throws NoLoggedUser {
        return UserController.getInstance().getFriendList(onlyAcceptedFriend);
    }

    /**
     * Return the list of friend requests of the logged user.
     *
     * @return The list of friend requests for the logged user.
     * @throws NoLoggedUser if there is no user logged.
     */
    public List<Friend> getFriendRequestList() throws NoLoggedUser {
        return UserController.getInstance().getFriendRequestList();
    }

    /**
     * Refresh the logged user with the db.
     *
     * @throws NoLoggedUser if there is no user logged.
     */
    public void refreshLoggedUser() throws NoLoggedUser {
        UserController.getInstance().refreshLoggedUser();
    }
}
