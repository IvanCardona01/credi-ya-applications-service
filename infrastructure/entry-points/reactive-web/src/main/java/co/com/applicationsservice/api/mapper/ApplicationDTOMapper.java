package co.com.applicationsservice.api.mapper;

import co.com.applicationsservice.api.dto.request.CreateApplicationDTO;
import co.com.applicationsservice.api.dto.response.ApplicationResponseDTO;
import co.com.applicationsservice.model.application.Application;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApplicationDTOMapper {
    Application toModel(CreateApplicationDTO dto);
    ApplicationResponseDTO toDTO(Application application);
}
