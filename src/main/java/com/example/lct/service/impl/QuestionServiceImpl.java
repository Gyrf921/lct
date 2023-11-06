package com.example.lct.service.impl;

import com.example.lct.model.status.Department;
import com.example.lct.model.status.Question;
import com.example.lct.repository.QuestionRepository;
import com.example.lct.web.dto.request.admin.QuestionsDTO;
import com.example.lct.web.dto.request.admin.obj.QuestionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionServiceImpl {

    private final QuestionRepository questionRepository;

    public List<Question> saveAllQuestionForCompany(Long companyId, QuestionsDTO questionsDTO) {
        List<Question> questions = new ArrayList<>();

        for (QuestionDTO questionDTO: questionsDTO.getQuestionsDTO()) {
            //TODO mapper
            questions.add(Question.builder().companyId(companyId)
                    .imagePath(questionDTO.getImagePath())
                    .theme(questionDTO.getTheme())
                    .answer(questionDTO.getAnswer()).build());
        }

        List<Question> savedDep = questionRepository.saveAll(questions);

        log.info("[saveAllQuestionForCompany] << result: {}", savedDep);

        return savedDep;

    }
}
