package com.UpdateMultiplayerQuizBattleSystem.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.UpdateMultiplayerQuizBattleSystem.Entity.Leaderboard;
import com.UpdateMultiplayerQuizBattleSystem.Entity.Match;
import com.UpdateMultiplayerQuizBattleSystem.Entity.Question;
import com.UpdateMultiplayerQuizBattleSystem.Entity.Quiz;
import com.UpdateMultiplayerQuizBattleSystem.Respo.LeaderboardRepo;
import com.UpdateMultiplayerQuizBattleSystem.Respo.MatchRepo;
import com.UpdateMultiplayerQuizBattleSystem.Respo.QuestionRepo;
import com.UpdateMultiplayerQuizBattleSystem.Respo.QuizRepo;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired QuizRepo quizRepo;
    @Autowired QuestionRepo qRepo;
    @Autowired MatchRepo matchRepo;
    @Autowired LeaderboardRepo lRepo;

    // --- QUIZ MANAGEMENT ---
    
    @PostMapping("/quiz")
    public Quiz createQuiz(@RequestBody Quiz q){
        // Default values for new quiz if not provided
        if(q.getTimeLimit() == 0) q.setTimeLimit(30); 
        return quizRepo.save(q);
    }

    // --- QUESTION MANAGEMENT ---

    // NEW: Needed for the "Question Registry" table in your UI
    @GetMapping("/questions")
    public List<Question> getAllQuestions() {
        return qRepo.findAll();
    }

    @PostMapping("/question")
    public Question addQuestion(@RequestBody Question q){
        return qRepo.save(q);
    }

    @DeleteMapping("/question/{id}")
    public void delete(@PathVariable Long id){
        qRepo.deleteById(id);
    }

    // --- MONITORING ---

    @GetMapping("/matches")
    public List<Match> matches(){
        return matchRepo.findAll();
    }

    // --- ANALYTICS & LEADERBOARD ---

    @GetMapping("/leaderboard")
    public List<Leaderboard> leaderboard(){
        // Optimization: Sort by score descending and return top 10 for a cleaner chart
        return lRepo.findAll().stream()
                .sorted((a, b) -> Integer.compare(b.getScore(), a.getScore()))
                .limit(10) 
                .collect(Collectors.toList());
    }
}