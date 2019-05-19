package com.nowcoder.service;

import com.nowcoder.dao.QuestionDAO;
import com.nowcoder.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @author shine10076
 * @date 2019/5/8 19:55
 */
@Service
public class QuestionService {
    @Autowired
    QuestionDAO questionDAO;

    public Question getById(int id){
        return questionDAO.getById(id);
    }

    public List<Question> getLatestQuestions(int userId, int offset, int limit) {
        return questionDAO.selectLatestQuestions(userId, offset, limit);
    }

    public int addQuestion(Question question){
        return questionDAO.addQuestion(question)>0?question.getId():0;

    }
}
