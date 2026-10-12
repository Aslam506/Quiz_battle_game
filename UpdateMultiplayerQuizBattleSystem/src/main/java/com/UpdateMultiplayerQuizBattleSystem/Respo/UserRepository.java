package com.UpdateMultiplayerQuizBattleSystem.Respo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.UpdateMultiplayerQuizBattleSystem.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
//public interface QuestionRepository extends JpaRepository<Question, Long> {}
//public interface LeaderboardRepository extends JpaRepository<Leaderboard, Long> {}
