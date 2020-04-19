package analytical_module.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class FuneralDirector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String surname;
    private String name;
    private String dateOfBirth;
    private String religion;
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funeralDirector", orphanRemoval = true)
    private Set<Fact> facts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Fact> getFacts() {
        return facts;
    }

    public void setFacts(Set<Fact> facts) {
        this.facts = facts;
    }
}
