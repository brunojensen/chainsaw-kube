package de.chainsaw.app.user.service;

import io.quarkus.panache.common.Parameters;
import de.chainsaw.app.user.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class UserService {

    public User findById(final Long id) {
        return User.findById(id);
    }

    public List<User> listAll() {
        return User.listAll();
    }

    @Transactional
    public void persist(final User user) {
        user.persist();
    }

    @Transactional
    public void delete(final Long id) {
        User.delete("id = :id", Parameters.with("id", id));
    }
}
