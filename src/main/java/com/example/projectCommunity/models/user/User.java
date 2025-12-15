package com.example.projectCommunity.models.user;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a registered user.
 *
 * <p>The user is the main action taker in the application.</p>
 * */
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "users")
public class User {

    /** Unique identifier of the user */
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /** Email that the user registered with */
    @Column(unique = true, nullable = false)
    private String email;

    /** Password set by the user*/
    @Column(nullable = false)
    private String password;

    /** Count of projects that a user owns */
    @Column(columnDefinition = "int default 0")
    private int projectSize;

}
