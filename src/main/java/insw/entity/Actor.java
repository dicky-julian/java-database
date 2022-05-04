package insw.entity;

public class Actor {
    private Integer actor_id;
    private String first_name, last_name;

    public Actor(String first_name, String last_name) {
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public Actor(Integer actor_id, String first_name, String last_name) {
        this.actor_id = actor_id;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public Integer getId() {
        return actor_id;
    }

    public void setId(Integer actor_id) {
        this.actor_id = actor_id;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }
}
