package logic.controller.user;

import exception.BadCredentialException;
import exception.InvalidUsernameException;
import exception.NotFoundException;
import exception.user.MustBeAnAdminException;
import exception.user.NoLoggedUser;
import logic.controller.AbstractController;
import org.bson.types.ObjectId;
import org.mindrot.jbcrypt.BCrypt;
import persistence.dao.user.UserDao;
import persistence.entity.user.Friend;
import persistence.entity.user.User;

import java.util.List;
import java.util.stream.Collectors;

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
     * Check if there is a user logged in.
     *
     * @return true if there is a user logged in, false otherwise.
     */
    public boolean isLogged() {
        return loggedUser != null;
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
     * @throws MustBeAnAdminException if the user is not an admin.
     */
    public boolean deleteOne(String username) throws MustBeAnAdminException {
        if (isAdmin()) {
            return getDao().deleteOne(username);
        }
        else {
            throw new MustBeAnAdminException();
        }
    }

    /**
     * Logout the logged user.
     */
    public void logout() {
        if (loggedUser != null) {
            System.out.println("The logged user " + loggedUser.getUsername() + " has been logged out.");
        }
        loggedUser = null;
    }

    /**
     * Add a friend to the logged user.
     *
     * @param username of the friend to add.
     * @return the friend requested.
     * @throws NotFoundException if the friend is not found.
     * @throws NoLoggedUser if there is no user logged.
     */
    public User addFriend(String username) throws NotFoundException, NoLoggedUser {
        // Add request to the requested user.
        User requestedUser = getDao().findOneByUsername(username);
        if (requestedUser.getFriendRequests() == null || requestedUser.getFriendRequests().stream().noneMatch(friend -> friend.getUsername().equals(loggedUser.getUsername())) ) {
            // The requested user has NOT already received the request from the logged user.
            requestedUser.addFriendRequest(new Friend(loggedUser));
            getDao().updateOne(requestedUser.getId(), requestedUser);
        }

        // Add friend to the logged user. The friend is not confirmed yet.
        if (getLoggedUser().getFriends() == null || getLoggedUser().getFriends().stream().noneMatch(x -> x.getUsername().equals(username))) {
            // If the friend isn't already added.
            getLoggedUser().addFriend(new Friend(requestedUser));
            getDao().updateOne(getLoggedUser().getId(), getLoggedUser());
        }
        return requestedUser;
    }

    /**
     * Accept a friend request.
     *
     * @param username of the friend to accept.
     * @throws NoLoggedUser if there is no user logged.
     */
    public void acceptFriend(String username) throws NoLoggedUser {
        refreshLoggedUser();
        // Get the friend.
        User newFriend = getDao().findOneByUsername(username);


        // For the logged user, delete the corresponding friend in friend and request lists if exists.
        getLoggedUser().getFriends().removeIf(friend -> friend.getUsername().equals(username));
        getLoggedUser().getFriendRequests().removeIf(friend -> friend.getUsername().equals(username));

        // For the new friend, delete the friend corresponding to the logged user in friend and request lists if exists.
        newFriend.getFriends().removeIf(friend -> friend.getUsername().equals(loggedUser.getUsername()));
        newFriend.getFriendRequests().removeIf(friend -> friend.getUsername().equals(loggedUser.getUsername()));


        // For the logged user, add the friend in the friend list.
        getLoggedUser().addFriend(new Friend(newFriend, true));

        // For the new friend, add the logged user in the friend list.
        newFriend.addFriend(new Friend(getLoggedUser(), true));


        // Send to the db.
        getDao().updateOne(getLoggedUser().getId(), getLoggedUser());
        getDao().updateOne(newFriend.getId(), newFriend);
    }

    /**
     * Remove a friend from the logged user.
     *
     * @param username of the friend to remove.
     * @return true if the friend has been removed, false otherwise.
     * @throws NoLoggedUser if there is no user logged.
     */
    public boolean removeFriend(String username) throws NoLoggedUser {
        // Remove for the logged user.
        getLoggedUser().getFriends().removeIf(x -> x.getUsername().equals(username));
        getLoggedUser().getFriendRequests().removeIf(x -> x.getUsername().equals(username));

        // Remove for the friend.
        User friend = getDao().findOneByUsername(username);
        String loggedUsername = getLoggedUser().getUsername();
        friend.getFriends().removeIf(x -> x.getUsername().equals(loggedUsername));
        friend.getFriendRequests().removeIf(x -> x.getUsername().equals(loggedUsername));

        return getDao().updateOne(getLoggedUser().getId(), getLoggedUser()) &&
                getDao().updateOne(friend.getId(), friend);
    }

    /**
     * Return the list of friends of the logged user.
     *
     * @param onlyAcceptedFriend True if you want only the accepted friends. False if you want all the friends.
     * @return The list of friends of the logged user.
     * @throws NoLoggedUser if there is no user logged.
     */
    public List<Friend> getFriendList(boolean onlyAcceptedFriend) throws NoLoggedUser {
        refreshLoggedUser();
        if (onlyAcceptedFriend) {
            return getLoggedUser().getFriends().stream().filter(Friend::isAccepted).collect(Collectors.toList());
        }
        else {
            return getLoggedUser().getFriends();
        }
    }

    /**
     * Return the list of friend requests of the logged user.
     *
     * @return The list of friend requests for the logged user.
     * @throws NoLoggedUser if there is no user logged.
     */
    public List<Friend> getFriendRequestList() throws NoLoggedUser {
        refreshLoggedUser();
        return getLoggedUser().getFriendRequests();
    }

    /**
     * Refresh the logged user with the db.
     *
     * @throws NoLoggedUser if there is no user logged.
     */
    public void refreshLoggedUser() throws NoLoggedUser {
        setLoggedUser(getDao().findOneByUsername(getLoggedUser().getUsername()));
    }
}
