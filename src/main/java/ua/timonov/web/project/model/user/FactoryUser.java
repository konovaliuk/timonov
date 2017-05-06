package ua.timonov.web.project.model.user;

/**
 *
 */
@Deprecated
public class FactoryUser implements IFactoryUser {

    @Override
    public User createUser(UserType userType) {
        return new User(userType);
    }
}
