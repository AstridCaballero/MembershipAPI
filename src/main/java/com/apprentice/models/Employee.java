package com.apprentice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.*;

import javax.persistence.*;

/**
 * This entity contains the attributes that represent the fields of a table 'Employee' of a DB
 * it is using Object Relational Mapping - ORM in order to take an object Employee
 * and map it into the table 'Employee' of a database
 */
@Entity
@Data //Lombok takes care of the getters and setters
@EqualsAndHashCode(callSuper = true) //Lombok takes care of the equals and hash methods
@AllArgsConstructor //Lombok creates a constructor with all the attributes as args
@NoArgsConstructor //Lombok creates a default empty constructor
@ToString //Lombok overrides the .toString()
public class Employee extends PanacheEntityBase {

    // Card and Employee shared primary key Id. this makes 'select' easier
    @Id
    private String cardId;
    private String employeeId;

    // Card and Employee shared primary key Id
    @OneToOne
    @MapsId
    @JoinColumn(name = "cardId")
    @JsonIgnore
    private Card card;

    private String employeeName;
    private String employeeEmail;
    private String employeeMobileNumber;

}
