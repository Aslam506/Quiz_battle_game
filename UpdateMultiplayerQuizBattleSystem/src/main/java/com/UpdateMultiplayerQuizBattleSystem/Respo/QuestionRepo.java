package com.UpdateMultiplayerQuizBattleSystem.Respo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.UpdateMultiplayerQuizBattleSystem.Entity.Question;

@Repository
public interface QuestionRepo extends JpaRepository<Question,Long>{
	 List<Question> findByQuizId(Long quizId);
	}