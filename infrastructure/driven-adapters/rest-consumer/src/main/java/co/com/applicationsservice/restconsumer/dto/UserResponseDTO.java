package co.com.applicationsservice.restconsumer.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UserResponseDTO(
    Long id,
    String documentNumber,
    String name,
    String lastname,
    LocalDate birthdayDate,
    String address,
    String phoneNumber,
    BigDecimal baseSalary,
    String email
) {}
