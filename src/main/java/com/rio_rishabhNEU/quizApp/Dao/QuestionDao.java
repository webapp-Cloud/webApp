package com.rio_rishabhNEU.quizApp.Dao;

import com.rio_rishabhNEU.quizApp.QuestionModel;
import com.rio_rishabhNEU.quizApp.SimulationConfig;
import lombok.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<QuestionModel, Integer> {

   List<QuestionModel> findByDifficultyLevel( String difficultyLevel);



   @Query(value = "SELECT 1" , nativeQuery = true)
   Integer pingDatabase();
  default boolean isDatabaseConnected() {

      if(SimulationConfig.isSimulateDbDisconnection()){
          return false;
      }
      try{
         Integer result = pingDatabase();
         return result != null && result == 1 ;
      }
      catch (Exception e) {
         return false;
      }
   }
}
