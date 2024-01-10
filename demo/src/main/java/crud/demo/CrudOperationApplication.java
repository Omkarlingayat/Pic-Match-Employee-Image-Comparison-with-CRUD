package crud.demo;

import crud.demo.model.Employee;
import crud.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@SpringBootApplication
public class CrudOperationApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CrudOperationApplication.class, args);
	}

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public void run(String... args) throws Exception {
		Employee obj = new Employee();
		obj.setId(103);
		obj.setName("Shambhu");
		obj.setAge(21);
		obj.setDepartment("AIDS");
		obj.setImage(readImageFromFile("C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\image.jpg"));
		employeeRepository.save(obj);

		Employee obj1 = new Employee();
		obj1.setId(102);
		obj1.setName("Shantanu");
		obj1.setAge(34);
		obj1.setDepartment("Mechanical");
		//employeeRepository.save(obj1);
	}

	private byte[] readImageFromFile(String filePath) throws IOException {
		File file = new File(filePath);
		return Files.readAllBytes(file.toPath());
	}
}
