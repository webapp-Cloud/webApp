package com.rio_rishabhNEU.quizApp.service;

import com.rio_rishabhNEU.quizApp.Dao.QuestionDao;
import com.rio_rishabhNEU.quizApp.QuestionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;
    public List<QuestionModel> getAllQuestions() {
        return questionDao.findAll();
    }


    public List<QuestionModel>getallQuestionsByDifficulty(String difficultyLevel) {

        return questionDao.findByDifficultyLevel(difficultyLevel);
    }

    public boolean isDatabaseConnected()  {
       try{
           return questionDao.isDatabaseConnected();
       }catch(Exception e){
           return false;
       }
    }
}
