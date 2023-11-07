package com.example.lct.mapper;

import com.example.lct.model.History;
import com.example.lct.web.dto.response.obj.HistoryResponseDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface HistoryMapper {
    HistoryResponseDTO historyToHistoryResponseDTO(History history);

}
