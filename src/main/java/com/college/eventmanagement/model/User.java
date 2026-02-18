package com.college.eventmanagement.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    private String id;

    @Indexed(unique = true)
    private String username; //roll number

    @Indexed(unique = true)
    private String email;

    private String password;

    private String name;

    private String branch;

    //always use wrapper class coz int can't have null value it's default value is 0 so it will fail in validation
    private Integer sem;

    private Integer year;

    private Role role;

    private LocalDateTime createdAt;
}
