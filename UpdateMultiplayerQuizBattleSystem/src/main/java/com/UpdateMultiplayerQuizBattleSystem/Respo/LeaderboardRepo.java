package com.UpdateMultiplayerQuizBattleSystem.Respo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.UpdateMultiplayerQuizBattleSystem.Entity.Leaderboard;

public interface LeaderboardRepo extends JpaRepository<Leaderboard,Long>{}
