package hw;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class MyService {
    private final MyRepository myRepository;

    public void serviceMethod() {
        System.out.println("service");
        myRepository.repositoryMethod();
    }
}