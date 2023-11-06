package com.example.lct.service.impl;

import com.example.lct.model.Company;
import com.example.lct.model.Knowledge;
import com.example.lct.model.KnowledgeBase;
import com.example.lct.model.Post;
import com.example.lct.model.status.Department;
import com.example.lct.repository.KnowledgeBaseRepository;
import com.example.lct.repository.KnowledgeRepository;
import com.example.lct.web.dto.request.admin.KnowledgeBaseDTO;
import com.example.lct.web.dto.request.admin.obj.KnowledgeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class KnowledgeBaseServiceImpl {

    private final KnowledgeBaseRepository knowledgeBaseRepository;

    private final PostServiceImpl postService;

    private final KnowledgeRepository knowledgeRepository;


    public KnowledgeBase saveKnowledgeBaseForCompany(Company company, KnowledgeBaseDTO knowledgeBaseDTO) {
        //Создать KnowledgeBase, создать Knowledge с ссылкой на KnowledgeBase, добавить в KnowledgeBase

        Post post =
                postService.getPostByNameAndCompanyId(company.getCompanyId(), knowledgeBaseDTO.getPostName());

        KnowledgeBase knowledgeBase = knowledgeBaseRepository.save(KnowledgeBase.builder()
                .companyId(company.getCompanyId())
                .post(post).build());

        List<Knowledge> knowledges;

        if (knowledgeBase.getKnowledge() == null || knowledgeBase.getKnowledge().isEmpty()){
            knowledges = new ArrayList<>(
                    createKnowledgeListForKnowledgeBase(knowledgeBase.getKnowledgeBaseId(), knowledgeBaseDTO.getKnowledge()));
        }
        else {
            knowledges = knowledgeBase.getKnowledge();

            knowledges.addAll(
                    createKnowledgeListForKnowledgeBase(knowledgeBase.getKnowledgeBaseId(), knowledgeBaseDTO.getKnowledge()));

        }

        List<Knowledge> savedKnowledge = knowledgeRepository.saveAll(knowledges);

        knowledgeBase.setKnowledge(savedKnowledge);

        KnowledgeBase knowledgeBaseSaved = knowledgeBaseRepository.save(knowledgeBase);

        log.info("[saveKnowledgeBaseForCompany] << result: {}", knowledgeBaseSaved);
        return knowledgeBaseSaved;
    }

    private List<Knowledge> createKnowledgeListForKnowledgeBase(Long knowledgeBaseId, List<KnowledgeDTO> knowledgeDTOs){

        List<Knowledge> knowledgeList = new ArrayList<>();

        for (KnowledgeDTO knowledge : knowledgeDTOs){
            knowledgeList.add(Knowledge.builder()
                    .knowledgeBaseId(knowledgeBaseId).theme(knowledge.getTheme())
                    .information(knowledge.getInformation()).build());
        }

        return knowledgeList;
    }
}
