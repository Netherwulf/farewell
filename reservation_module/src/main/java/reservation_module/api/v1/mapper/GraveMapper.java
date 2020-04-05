package reservation_module.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import reservation_module.api.v1.model.GraveDTO;
import reservation_module.models.Grave;

@Mapper
public interface GraveMapper {
    GraveMapper INSTANCE = Mappers.getMapper(GraveMapper.class);

    GraveDTO graveToGraveDTO(Grave grave);

    Grave graveDTOToGrave(GraveDTO graveDTO);
}
