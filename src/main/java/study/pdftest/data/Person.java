package study.pdftest.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class Person {

    private String name;
    private int age;
    private LocalDate birthDate;

    public static List<Person> getDump() {
        return List.of(
                new Person("홍길동", 10, LocalDate.of(2020, 1, 11)),
                new Person("임꺽정", 40, LocalDate.of(2010, 5, 4)),
                new Person("개똥이", 30, LocalDate.of(2000, 2, 13))
        );
    }
}
