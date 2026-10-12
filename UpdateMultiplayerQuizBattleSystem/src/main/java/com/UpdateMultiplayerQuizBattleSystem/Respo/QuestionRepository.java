package com.UpdateMultiplayerQuizBattleSystem.Respo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.UpdateMultiplayerQuizBattleSystem.Entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {}