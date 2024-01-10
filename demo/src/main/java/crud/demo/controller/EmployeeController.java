package crud.demo.controller;

import crud.demo.ImageComparisonUtils;
import crud.demo.exception.ResourceNotFoundException;
import crud.demo.model.Employee;
import crud.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    //Building Create Employee REST API
    // handle http request and convert json into java object
    @PostMapping
    /*public Employee createEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }*/

    public ResponseEntity<Employee> createEmployee(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("name") String name,
                                                   @RequestParam("age") int age,
                                                   @RequestParam("department") String department) {
        try {
            Employee employee = new Employee();
            employee.setName(name);
            employee.setAge(age);
            employee.setDepartment(department);
            employee.setImage(file.getBytes()); // Set image bytes

            Employee savedEmployee = employeeRepository.save(employee);
            return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // GET - Retrieve an employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return ResponseEntity.ok().body(employee);
    }

    // build update employee REST API
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee employeeDetails){
        Employee updateEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not Exist with id : "+id));

        updateEmployee.setName(employeeDetails.getName());
        updateEmployee.setDepartment(employeeDetails.getDepartment());
        updateEmployee.setAge(employeeDetails.getAge());

        employeeRepository.save(updateEmployee);

        return ResponseEntity.ok(updateEmployee);
    }

    // build delete employee REST API
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not Exist with id : " + id));

        employeeRepository.delete(employee);
        return ResponseEntity.noContent().build();
    }

    // check images are matching or not
    @PostMapping("/compare-images")
    public ResponseEntity<?> compareEmployeeImages(@RequestParam("id1") int id1, @RequestParam("id2") int id2){
        Employee employee1 = employeeRepository.findById((long) id1)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id : "+id1));
        Employee employee2 = employeeRepository.findById((long) id2)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id "+id2));

        boolean imagesEqual = ImageComparisonUtils.areImagesEqual(employee1.getImage(), employee2.getImage());
        return ResponseEntity.ok("Image are equal: "+imagesEqual);
    }
}
