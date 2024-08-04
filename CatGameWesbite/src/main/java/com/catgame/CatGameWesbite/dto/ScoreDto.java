package com.catgame.CatGameWesbite.dto;

public class ScoreDto {

    private int score;

    public ScoreDto() {
    }
   
    public Integer getGameScore(){
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}