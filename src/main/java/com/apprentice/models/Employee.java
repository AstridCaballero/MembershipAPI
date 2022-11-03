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
