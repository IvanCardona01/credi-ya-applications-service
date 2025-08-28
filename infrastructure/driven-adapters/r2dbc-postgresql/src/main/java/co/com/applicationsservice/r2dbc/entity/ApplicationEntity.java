package co.com.applicationsservice.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("applications")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationEntity {
    @Id
    private Long id;

    @Column("client_document")
    private String clientDocument;
    @Column("credit_amount")
    private BigDecimal creditAmount;

    private Integer months;

    @Column("status_id")
    private Long statusId;
    @Column("type_id")
    private Long typeId;
}
