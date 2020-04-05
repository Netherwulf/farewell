package reservation_module.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import reservation_module.api.v1.model.DeceasedDTO;
import reservation_module.models.Deceased;

@Mapper
public interface DeceasedMapper {
    DeceasedMapper INSTANCE = Mappers.getMapper(DeceasedMapper.class);

    DeceasedDTO deceasedToDeceasedDTO(Deceased deceased);

    Deceased deceasedDTOToDeceased(DeceasedDTO deceasedDTO);
}
