package vocabtrainer.interactionmodule;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FlashCard {
    private int ID;
    private double learnProb;
    private int difficulty;
    private String cardContent;
    private String answerContent[];
    private int aID;


    public double getLearnProb() {
        return this.learnProb;
    }

    public void setLearnProb(double learnProb) {
        this.learnProb = learnProb;
    }

    public void setCardContent(String content) {
        this.cardContent = content;
    }

    public String getCardContent() {
        return cardContent;
    }

    public void setAnswer(int aID) {
        this.aID = aID;
    }

    public void setAnswerContent(String answerContent[])
    {
        this.answerContent[aID] = answerContent[aID];
    }

    public int getAnswer() {
        return this.aID;
    }


    public void updateLearnProb(double updateFactor) {
        this.learnProb += updateFactor;
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


    public int getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}