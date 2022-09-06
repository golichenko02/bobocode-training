package demo.entity;

import bibernate.annotation.Column;
import bibernate.annotation.Id;
import bibernate.annotation.Table;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Table(name = "persons")
public class Person {

    @Id
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private Integer age;

    @Column(name = "created_at")
    private Timestamp createdAt;
}
