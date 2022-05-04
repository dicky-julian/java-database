package insw;

import insw.entity.Actor;
import insw.implementation.ActorImplementation;
import insw.repository.ActorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RepositoryTest {
    private ActorRepository actorRepository;

    @BeforeEach
    void init() {
        actorRepository = new ActorImplementation();
    }

    @Test
    void testInsert() {
        Actor actor = new Actor("MARSHA", "LENATHEA");
        actorRepository.insert(actor);

        System.out.println(actor.getId() + " " + actor.getFirstName());
    }

    @Test
    void testFindById() {
        Actor actor = actorRepository.findById(208);
        System.out.println(actor.getId() + " " + actor.getFirstName());
    }

    @Test
    void testFindAll() {
        List<Actor> actors = actorRepository.findAll();
        actors.forEach(actor -> {
            System.out.println(actor.getId() + " " + actor.getFirstName());
        });
    }

    @Test
    void testFinByFirstName() {
        List<Actor> actors = actorRepository.findAllByFirstName("MARSHA");
        actors.forEach(actor -> {
            System.out.println(actor.getId() + " " + actor.getFirstName());
        });
    }
}
