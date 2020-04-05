package reservation_module.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import reservation_module.api.v1.model.FuneralDTO;
import reservation_module.models.Funeral;

@Mapper
public interface FuneralMapper {
    FuneralMapper INSTANCE = Mappers.getMapper(FuneralMapper.class);

    FuneralDTO funeralToFuneralDTO(Funeral funeral);

    Funeral funeralDTOToFuneral(FuneralDTO funeralDTO);
}
