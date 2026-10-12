package com.UpdateMultiplayerQuizBattleSystem.Config;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.UpdateMultiplayerQuizBattleSystem.Entity.Leaderboard;
import com.UpdateMultiplayerQuizBattleSystem.Entity.Question;
import com.UpdateMultiplayerQuizBattleSystem.Respo.LeaderboardRepo;
import com.UpdateMultiplayerQuizBattleSystem.Respo.QuestionRepo;

@Component
public class GameSocket extends TextWebSocketHandler {

    Queue<WebSocketSession> queue = new LinkedList<>();
    Map<String, Integer> score = new HashMap<>();
    Map<String, Integer> streak = new HashMap<>();
    
    // NEW: Map to store Session ID -> Real Username
    Map<String, String> sessionUsernames = new HashMap<>();

    List<Question> questions;
    int index = 0;
    int answersReceived = 0;
    WebSocketSession p1, p2;

    @Autowired QuestionRepo qRepo;
    @Autowired LeaderboardRepo lRepo;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Extract username from URL: ws://localhost:9090/game?username=PlayerName
        String query = session.getUri().getQuery();
        String username = "Anonymous";
        if (query != null && query.contains("username=")) {
            username = query.split("username=")[1];
        }
        sessionUsernames.put(session.getId(), username);
    }

    @Override
    protected void handleTextMessage(WebSocketSession s, TextMessage m) throws Exception {
        if (m.getPayload().equals("JOIN")) {
            queue.add(s);
            if (queue.size() >= 2) {
                p1 = queue.poll();
                p2 = queue.poll();
                startGame();
            }
        } else {
            checkAnswer(s, m.getPayload());
        }
    }

    void startGame() throws Exception {
        questions = qRepo.findAll();
        index = 0;
        score.put(p1.getId(), 0);
        score.put(p2.getId(), 0);
        streak.put(p1.getId(), 0);
        streak.put(p2.getId(), 0);
        sendQuestion();
    }

    void sendQuestion() throws Exception {
        if (index >= questions.size()) {
            endGame();
            return;
        }
        Question q = questions.get(index);
        answersReceived = 0;

        String msg = "Q|" + q.getQuestion() + "|" + q.getOptionA() + "|" + 
                     q.getOptionB() + "|" + q.getOptionC() + "|" + q.getOptionD();

        p1.sendMessage(new TextMessage(msg));
        p2.sendMessage(new TextMessage(msg));
    }

    void checkAnswer(WebSocketSession s, String ans) throws Exception {
        Question q = questions.get(index);
        if (ans.equals(q.getCorrectOption())) {
            streak.put(s.getId(), streak.get(s.getId()) + 1);
            int pointsEarned = q.getPoints() + (streak.get(s.getId()) * 2);
            score.put(s.getId(), score.get(s.getId()) + pointsEarned);
        } else {
            streak.put(s.getId(), 0);
        }

        answersReceived++;
        if (answersReceived == 2) {
            index++;
            sendQuestion();
        }
    }

    void endGame() throws Exception {
        p1.sendMessage(new TextMessage("END|" + score.get(p1.getId())));
        p2.sendMessage(new TextMessage("END|" + score.get(p2.getId())));
        saveScore(p1);
        saveScore(p2);
    }

    void saveScore(WebSocketSession s) {
        Leaderboard l = new Leaderboard();
        // FIXED: Using the mapped username instead of session ID
        l.setUsername(sessionUsernames.getOrDefault(s.getId(), "Unknown"));
        l.setScore(score.get(s.getId()));
        lRepo.save(l);
    }
}