package com.example.lct.web.dto.response;

import com.example.lct.web.dto.response.obj.HistoryResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryListResponseDTO {

    List<HistoryResponseDTO> historyResponseDTOS;
}
