package com.example.lct.web.dto.response.obj;

import com.example.lct.model.enumformodel.ActionType;
import com.example.lct.model.enumformodel.HistoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryResponseDTO {
    private Long historyId;

    private HistoryType historyType;

    private ActionType actionType;

    private String name;

    public Date created;
}
