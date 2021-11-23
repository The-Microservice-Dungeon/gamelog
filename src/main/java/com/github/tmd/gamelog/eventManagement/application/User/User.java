package com.github.tmd.gamelog.eventManagement.application.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    @Getter
    Long id;

    @Getter
    @Setter
    int exampleScore;

    @Getter
    @Setter
    int exampleScore2;

}
