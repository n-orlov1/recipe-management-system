package org.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recipe {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Integer id;
    private String name;
    private String description;
    private String category;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime date;
    private String[] ingredients = new String[]{};
    private String[] directions = new String[]{};

    public boolean validate() {
        if(name == null || description == null
                || name.isBlank() || description.isBlank()
                || category == null || category.isBlank()
                || ingredients.length < 1 || directions.length < 1) {
            return false;
        }
        return true;
    }
}
