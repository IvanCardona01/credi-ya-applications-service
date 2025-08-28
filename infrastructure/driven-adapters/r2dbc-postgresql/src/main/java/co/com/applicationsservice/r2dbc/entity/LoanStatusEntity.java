package co.com.applicationsservice.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Table("loan_status")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoanStatusEntity {
    @Id
    private Long id;
    private String name;
    private String description;
}
