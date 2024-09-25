package com.example.library_service.mapper;

import com.example.library_service.dto.RecordRequestDTO;
import com.example.library_service.dto.RecordResponseDTO;
import com.example.library_service.entity.Record;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecordMapper {

    RecordResponseDTO toRecordDto(Record recordEntity);

    List<RecordResponseDTO> toRecordDTOList(List<Record> recordEntities);

    void updateRecordFromDTO(RecordRequestDTO recordDTO, @MappingTarget Record record);


}