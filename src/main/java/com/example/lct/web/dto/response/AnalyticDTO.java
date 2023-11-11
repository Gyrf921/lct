package com.example.lct.web.dto.response;

import com.example.lct.model.enumformodel.HistoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AnalyticDTO {

    private HistoryType name;

    private Integer countDone;

}
