package com.UpdateMultiplayerQuizBattleSystem.Respo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.UpdateMultiplayerQuizBattleSystem.Entity.Leaderboard;

@Repository
public interface LeaderboardRepository extends JpaRepository<Leaderboard, Long> {}