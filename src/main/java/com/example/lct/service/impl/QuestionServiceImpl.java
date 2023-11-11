package com.example.lct.service.impl;

import com.example.lct.exception.ResourceNotFoundException;
import com.example.lct.model.Question;
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

    public Question getQuestionById(Long id) {
        log.info("[getQuestionById] >> id: {}", id);

        Question question = questionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Question not found by this id :{} ", id);
                    return new ResourceNotFoundException("Question not found by this id :: " + id);
                });

        log.info("[getQuestionById] << result: {}", question);

        return question;
    }


    public List<Question> saveAllQuestionForCompany(Long companyId, QuestionsDTO questionsDTO) {
        List<Question> questions = new ArrayList<>();

        for (QuestionDTO questionDTO : questionsDTO.getQuestionsDTO()) {
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

    public Question saveQuestionForCompany(Long companyId, QuestionDTO questionDTO) {
        return questionRepository.save(Question.builder().companyId(companyId)
                .imagePath(questionDTO.getImagePath())
                .theme(questionDTO.getTheme())
                .answer(questionDTO.getAnswer()).build());
    }

    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    public void deleteQuestion(Question question) {
        questionRepository.delete(question);
    }
}
