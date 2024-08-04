package com.catgame.CatGameWesbite.dto;

public class HighscoreDto {

    private int highscore;

    // Constructor
    public HighscoreDto(int highscore) {
        this.highscore = highscore;
    }

    // Getter
    public int getHighscore() {
        return highscore;
    }

    // Setter
    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }
}