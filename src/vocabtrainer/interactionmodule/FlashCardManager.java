package vocabtrainer.interactionmodule;
import java.util.Random;
import java.util.Scanner;


public class FlashCardManager {

    private Random randGen = new Random();
    private int numCards = 100;
    private double learnRatio = 0;
    private int numPiIntervals = 10; /* 0.0 - 0.9 */
    private int numLRIntervals = 10; /* 0.0 - 0.9 */
    private FlashCard[] flashCard = new FlashCard[numCards];
    private FlashCard currentCard;
    private WordListGenerator wordListGenerator;
    private int agentAction;
    private int userAnswer;
    private double reward;


    public FlashCardManager() {
        setUpFlashCards();
    }

    public void setUpFlashCards()
    {
        wordListGenerator = new WordListGenerator();
        wordListGenerator.readFile();
        for (int i = 0; i < numCards ;i++ ) {
            flashCard[i] = new FlashCard();
            flashCard[i].setID(i);
            flashCard[i].setLearnProb(0.0d);
            flashCard[i].setAnswer(2);
            flashCard[i].setCardContent("What is the meaning of " + wordListGenerator.getRandomWord() + " ?");
        }
        // for (int i = 0; i < numCards ; i++ ) {
        //     flashCard[i].setID(i);
        //     if (i < 25) {
        //         flashCard[i].setDifficulty(0);
        //     } else if (i > 25 && i <= 50) {
        //         flashCard[i].setDifficulty(1);
        //     } else if (i > 50 && i <= 75) {
        //         flashCard[i].setDifficulty(1);
        //     } else if (i > 75 && i <= 99) {
        //         flashCard[i].setDifficulty(1);
        //     }
        //     flashCard[i].setLearnProb(0.0d);
        // }
    }

    public void setAgentAction(int action)
    {
        this.agentAction = action;
        engageFlashCard(action);
    }

    public void setReward(double r) {
        this.reward = r;
    }


    public int getAgentAction()
    {
        return this.agentAction;
    }

    public void setNewLearnerState() {
        learnRatio = 0;
        currentCard = flashCard[randGen.nextInt(100)];
        engageFlashCard(randGen.nextInt(100)); /* do not show, but wait for agent */

    }

    public FlashCard getCurrentCard() {
        return currentCard;
    }

    public void setCurrentCard(FlashCard card) {
        this.currentCard = card;
    }

    public double bruteCalcLearnRatio() {
        double probs = 0;
        for (int i = 0;i < numCards ;i++ ) {
            if(flashCard[i].getLearnProb() == 0.9)
                probs++;
        }
        return probs / numCards;
    }

    public double getLearnRatio() {
        return learnRatio;
    }

    public void setLearnRatio(double ratio) {
        this.learnRatio = ratio;
    }

    public void engageFlashCard(int cardID) {
        Scanner scanner = new Scanner(System.in);
        setCurrentCard(flashCard[cardID]);
        System.out.println("What is the meaning of " + getCurrentCard().getCardContent());
        setUserAnswer(scanner.nextInt());
        setReward(calculateReward());
        updateLearning();
    }

    public boolean validateAnswer() {
        return getUserAnswer() == getCurrentCard().getAnswer();
    }

    public void updateLearning() {
        boolean isCorrect = validateAnswer();
        FlashCard currCard = getCurrentCard();
        double currlearnProb = currCard.getLearnProb();
        
        if (isCorrect && isBound(currlearnProb + 0.1)) {
            currCard.setLearnProb(currlearnProb + 0.1);
        } else if (!isCorrect && isBound(currlearnProb - 0.1)) {
            currCard.setLearnProb(currlearnProb - 0.1);
        }

        setLearnRatio(bruteCalcLearnRatio());

    }

    public boolean isBound(double learn) {
        return (learn >= 0.0) && (learn <= 1.0);
    }

    public double calculateReward() {

        boolean isCorrect = validateAnswer();
        System.out.println("Your answer is " + getUserAnswer());
        System.out.println("Your answer is " + isCorrect);
        double currlearnProb = getCurrentCard().getLearnProb();
        if (isCorrect) {
            if(currlearnProb < 0.7) {
                return 5.0d;
            } else if (currlearnProb >= 0.7 && currlearnProb < 0.9) {
                return 2.0d;
            } else if (currlearnProb == 0.9 ) {
                return -2.0d;
            }
        } else if (!isCorrect) {
            if(currlearnProb < 0.7) {
                return -2.0d;
            } else if (currlearnProb >= 0.7 && currlearnProb < 0.9) {
                return -1.0d;
            } else if (currlearnProb == 0.9 ) {
                return 2.0d;
            }
        }
        return 0.0d;
    }

    public int getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(int answer) {
        this.userAnswer = answer;
    }

    public int getNumStates() {
        return  numPiIntervals * numLRIntervals;
    }

    public int getState() {
        return toInterval(getLearnRatio()) * numPiIntervals + toInterval(getCurrentCard().getLearnProb());
    }

    public int toInterval(double value)
    {
        return (int) Math.round(value * 10);
    }

    // /**
    //  * Sets the agent current state to startRow,startCol.
    //  * @param startRow
    //  * @param startCol
    //  * @return true if the state is valid and not terminal, otherwise
    //  * return false.
    //  */
    // public void setAgentState(int action) {
    //     if(!isTerminal())
    //         this.agentAction = action;
    // }

    public boolean isTerminal() {
        if (getLearnRatio() == 0.9) {
            return true;
        }
        return false;
    }

    /**
     * Calculate the reward for the current agent state.
     * @return
     */
    public double getReward() {
        return reward;
    }


    // public void updatePosition(int theAction) {
    //     /* When the move would result in hitting an obstacles, the agent simply doesn't move */
    //     int newRow = agentRow;
    //     int newCol = agentCol;


    //     if (theAction == 0) {/*move down*/
    //         newCol = agentCol - 1;
    //     }
    //     if (theAction == 1) { /*move up*/
    //         newCol = agentCol + 1;
    //     }
    //     if (theAction == 2) {/*move left*/
    //         newRow = agentRow - 1;
    //     }
    //     if (theAction == 3) {/*move right*/
    //         newRow = agentRow + 1;
    //     }


    //     /*Check if new position is out of bounds or inside an obstacle */
    //     if (isValid(newRow, newCol)) {
    //         agentRow = newRow;
    //         agentCol = newCol;
    //     }
    // }

    /**
     * Print out the current state to the screen
     */
    // void print_state() {
    //     System.out.printf("Agent is at: %d,%d\n", agentRow, agentCol);
    //     System.out.printf("Columns:0-10                10-17\n");
    //     System.out.printf("Col    ");
    //     for (int col = 0; col < 18; col++) {
    //         System.out.printf("%d ", col % 10);
    //     }

    //     for (int row = 0; row < 6; row++) {
    //         System.out.printf("\nRow: %d ", row);

    //         for (int col = 0; col < 18; col++) {
    //             if (agentRow == row && agentCol == col) {
    //                 System.out.printf("A ");
    //             } else {
    //                 if (theMap[row][col] == SampleMinesEnvironment.WORLD_GOAL) {
    //                     System.out.printf("G ");
    //                 }
    //                 if (theMap[row][col] == SampleMinesEnvironment.WORLD_MINE) {
    //                     System.out.printf("M ");
    //                 }
    //                 if (theMap[row][col] == SampleMinesEnvironment.WORLD_OBSTACLE) {
    //                     System.out.printf("* ");
    //                 }
    //                 if (theMap[row][col] == SampleMinesEnvironment.WORLD_FREE) {
    //                     System.out.printf("  ");
    //                 }
    //             }
    //         }
    //     }
    //     System.out.printf("\n");
    // }
}
