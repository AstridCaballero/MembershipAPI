package com.apprentice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee extends PanacheEntityBase {

    @Id
    private String employeeId;

    @OneToOne(targetEntity = Card.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "cardId")
    @JsonIgnore
    private Card card;

    private String employeeName;
    private String employeeEmail;
    private String employeeMobileNumber;

}
