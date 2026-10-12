package com.UpdateMultiplayerQuizBattleSystem.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.UpdateMultiplayerQuizBattleSystem.Entity.User;
import com.UpdateMultiplayerQuizBattleSystem.Respo.UserRepo;
import com.UpdateMultiplayerQuizBattleSystem.Respo.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

 @Autowired UserRepo repo;

 @PostMapping("/register")
 public User register(@RequestBody User u){
  return repo.save(u);
 }

 @PostMapping("/login")
 public User login(@RequestBody User u){
  User db = repo.findByUsername(u.getUsername());
  if(db!=null && db.getPassword().equals(u.getPassword()))
   return db;
  throw new RuntimeException("Invalid Login");
 }
}
