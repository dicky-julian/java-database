package insw.repository;

import insw.entity.Actor;

import java.util.List;

public interface ActorRepository {
    void insert(Actor actor);

    Actor findById(Integer id);
    List<Actor> findAll();
    List<Actor> findAllByFirstName(String firstName);
}
