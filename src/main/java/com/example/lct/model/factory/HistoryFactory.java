package com.example.lct.model.factory;

import com.example.lct.model.Employee;
import com.example.lct.model.History;
import org.springframework.stereotype.Component;

@Component
public class HistoryFactory {

    private HistoryFactory() {
    }

    public static History createHistory(Long companyId, Employee employee) {
        //HistoryType сам определяется

        return null;
    }


}
