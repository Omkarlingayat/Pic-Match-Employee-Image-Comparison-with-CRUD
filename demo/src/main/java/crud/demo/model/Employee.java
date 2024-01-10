package crud.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "emp")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // primary key generation strategy
    private long id;

    @Column(name = "EmpName")
    private String name;

    @Column(name = "EmpAge")
    private int age;

    @Column(name = "EmpDept")
    private String department;

    @Lob //Indicate a large object field
    @Column(name = "EmpImage", columnDefinition = "BLOB")
    private byte[] image; // field to sotre image data as byte array
}
