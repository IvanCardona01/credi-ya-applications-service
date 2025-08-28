package co.com.applicationsservice.api.mapper;

import co.com.applicationsservice.api.dto.response.LoanTypeResponseDTO;
import co.com.applicationsservice.model.loantype.LoanType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanTypeDTOMapper {
    LoanTypeResponseDTO toDTO(LoanType model);
}
