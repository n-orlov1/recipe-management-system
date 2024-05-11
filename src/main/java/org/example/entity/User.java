package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Integer id;
    private String email;
    private String password;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Recipe> recipes = new ArrayList<>();
    private static final Pattern EMAIL_REGEXP = Pattern.compile("^.+\\@.+\\..+$");

    public boolean validate() {
        return emailIsCorrect(this.email) && passwordIsCorrect(this.password);
    }

    private boolean emailIsCorrect(String email) {

        if(email == null) return false;

        boolean emailMatches = EMAIL_REGEXP.matcher(email).matches();

        return emailMatches && !email.isBlank();
    }

    private boolean passwordIsCorrect(String password) {

        if(password == null) return false;

        return password.length() >= 8 && !password.isBlank();
    }
}
