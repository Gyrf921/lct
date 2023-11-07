package com.example.lct.service.impl;

import com.example.lct.model.Stage;
import com.example.lct.model.enumformodel.Status;
import com.example.lct.repository.StageRepository;
import com.example.lct.service.HistoryService;
import com.example.lct.service.StageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class StageServiceImpl implements StageService {

    private final HistoryService historyService;
    private final StageRepository stageRepository;
    private static final int LEVEL_FOR_BASE_STAGE = 0;
    private static final String NAME_FOR_BASE_STAGE = "Общие задачи";

    @Override
    public Stage createBaseStageForIntern(Long companyId) {

        Stage stage = stageRepository.save(Stage.builder()
                        .companyId(companyId)
                        .name(NAME_FOR_BASE_STAGE)
                        .levelDifficulty(LEVEL_FOR_BASE_STAGE)
                        .status(Status.OPENED)
                        .deadline(Timestamp.valueOf(LocalDateTime.now().plusMonths(1))).build());

        log.info("[createBaseStageForIntern] << result: {}", stage);

        return stage;
    }
}
