package com.example.library_service.mapper;

import com.example.library_service.dto.RecordDTO;
import com.example.library_service.entity.Record;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecordMapper {

    RecordDTO toRecordDto(Record recordEntity);

    Record toRecordEntity(RecordDTO recordDTO);

    List<RecordDTO> toRecordDTOList(List<Record> recordEntities);

}