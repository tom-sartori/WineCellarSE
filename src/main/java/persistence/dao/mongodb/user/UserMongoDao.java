package persistence.dao.mongodb.user;

import com.mongodb.MongoWriteException;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import constant.CollectionNames;
import exception.InvalidUsernameException;
import exception.NotFoundException;
import org.bson.BsonDocument;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import persistence.dao.mongodb.AbstractMongoDao;
import persistence.entity.user.User;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;

public class UserMongoDao extends AbstractMongoDao<User> {

	private static UserMongoDao instance;

	private UserMongoDao() {
	}

	public static UserMongoDao getInstance() {
		if (instance == null) {
			instance = new UserMongoDao();
		}
		return instance;
	}

	@Override
	protected String getCollectionName() {
		return CollectionNames.USER;
	}

	@Override
	protected Class<User> getEntityClass() {
		return User.class;
	}

	@Override
	protected Bson getSetOnUpdate(User entity) {
		List<Bson> updateList = new ArrayList<>();

		updateList.add(Updates.set("username", entity.getUsername()));
		updateList.add(Updates.set("password", entity.getPassword()));
		updateList.add(Updates.set("admin", entity.isAdmin()));

		if (entity.getFirstname() != null) {
			// Nullable attribute.
			updateList.add(Updates.set("firstname", entity.getFirstname()));
		}

		if (entity.getLastname() != null) {
			// Nullable attribute.
			updateList.add(Updates.set("lastname", entity.getLastname()));
		}

		if (entity.getEmail() != null) {
			// Nullable attribute.
			updateList.add(Updates.set("email", entity.getEmail()));
		}

		if (entity.getFriends() != null) {
			// Nullable attribute.
			updateList.add(Updates.set("friends", entity.getFriends()));
		}

		if (entity.getFriendRequests() != null) {
			// Nullable attribute.
			updateList.add(Updates.set("friendRequests", entity.getFriendRequests()));
		}


		return combine(updateList);
	}

	/**
	 * Insert one entity of the parametrized type in the database.
	 *
	 * @param user The entity to insert.
	 * @return The id of the inserted entity.
	 */
	@Override
	public ObjectId insertOne(User user) throws InvalidUsernameException {
		try {
			return super.insertOne(user);
		} catch (MongoWriteException e) {
			if (e.getCode() == 11000) {
				throw new InvalidUsernameException();
			}
			throw e;
		}
	}

	/**
	 * Find a user by its username.
	 *
	 * @param username The username of the user to find.
	 * @return The user if found. Otherwise, throw a NotFoundException.
	 * @throws NotFoundException if the user is not found.
	 */
	public User findOneByUsername(String username) throws NotFoundException {
		BsonDocument filter = new BsonDocument();
		filter.append("username", new org.bson.BsonString(username));
		User user = super.findOne(filter);

		if (user == null) {
			throw new NotFoundException();
		} else {
			return user;
		}
	}

	/**
	 * Delete a user by its username.
	 *
	 * @param username The username of the user to delete.
	 * @return The number of deleted users.
	 */
	public boolean deleteOne(String username) {
		DeleteResult deleteResult = getCollection().deleteOne(eq("username", username));
		return deleteResult.getDeletedCount() == 1;
	}
}
