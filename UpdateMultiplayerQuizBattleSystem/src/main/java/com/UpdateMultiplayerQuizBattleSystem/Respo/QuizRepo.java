package com.UpdateMultiplayerQuizBattleSystem.Respo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.UpdateMultiplayerQuizBattleSystem.Entity.Quiz;

@Repository
public interface QuizRepo extends JpaRepository<Quiz,Long>{}
