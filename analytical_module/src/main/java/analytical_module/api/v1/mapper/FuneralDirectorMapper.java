package analytical_module.api.v1.mapper;

import analytical_module.api.v1.model.FuneralDirectorDTO;
import analytical_module.models.FuneralDirector;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FuneralDirectorMapper {
    FuneralDirectorMapper INSTANCE = Mappers.getMapper(FuneralDirectorMapper.class);

    FuneralDirectorDTO funeralDirectorToFuneralDirectorDTO(FuneralDirector funeralDirector);

    FuneralDirector funeralDirectorDTOToFuneralDirector(FuneralDirectorDTO funeralDirectorDTO);
}
