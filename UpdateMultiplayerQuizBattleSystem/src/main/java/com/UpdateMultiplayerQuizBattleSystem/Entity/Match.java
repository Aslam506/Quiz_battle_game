package com.UpdateMultiplayerQuizBattleSystem.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Match {
 @Id @GeneratedValue
 private Long id;
 private String player1;
 private String player2;
 private String status; // ACTIVE / FINISHED
 public Long getId() {
	return id;
 }
 public void setId(Long id) {
	this.id = id;
 }
 public String getPlayer1() {
	return player1;
 }
 public void setPlayer1(String player1) {
	this.player1 = player1;
 }
 public String getPlayer2() {
	return player2;
 }
 public void setPlayer2(String player2) {
	this.player2 = player2;
 }
 public String getStatus() {
	return status;
 }
 public void setStatus(String status) {
	this.status = status;
 }
 
 
 
 
 
}
