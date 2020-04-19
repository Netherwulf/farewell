package analytical_module.api.v1.mapper;

import analytical_module.api.v1.model.FactDTO;
import analytical_module.models.Fact;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FactMapper {
    FactMapper INSTANCE = Mappers.getMapper(FactMapper.class);

    FactDTO factToFactDTO(Fact fact);

    Fact factDTOToFact(FactDTO factDTO);
}
