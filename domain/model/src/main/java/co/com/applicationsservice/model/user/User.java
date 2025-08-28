package co.com.applicationsservice.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private Long id;
    private String documentNumber;
    private String name;
    private String lastname;
    private LocalDate birthdayDate;
    private String address;
    private String phoneNumber;
    private BigDecimal baseSalary;
    private String email;
}
