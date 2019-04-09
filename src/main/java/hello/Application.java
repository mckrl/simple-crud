package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner loadData(StudentRepository repository) {
        return (args) -> {
            repository.save(new Student("Jan", "Kowalski", "jkow@gmail.com"));
            repository.save(new Student("Andrzej", "Nowak", "andnow@gmail.com"));
            repository.save(new Student("Anna", "Wiśniewska", "awisn@gmail.com"));
            repository.save(new Student("Dawid", "Kciuk", "dawk@gmail.com"));
            repository.save(new Student("Magda", "Kowal", "mkowal@gmail.com"));

        };
    }

}
