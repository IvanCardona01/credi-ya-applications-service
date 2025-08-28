package co.com.applicationsservice.api.mapper;

import co.com.applicationsservice.api.dto.response.LoanStatusResponseDTO;
import co.com.applicationsservice.model.loanstatus.LoanStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanStatusDTOMapper {
     LoanStatusResponseDTO toDTO(LoanStatus model);
}
