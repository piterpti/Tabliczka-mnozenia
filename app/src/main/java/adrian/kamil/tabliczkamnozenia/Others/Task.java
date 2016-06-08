package adrian.kamil.tabliczkamnozenia.Others;

import android.util.Log;
import java.util.*;

import adrian.kamil.tabliczkamnozenia.Activity;

/**
 * Created by Piter on 07/06/2016.
 */
public class Task {

    private final static int QUESTION_COUNT = 120;

    private int id;
    private int first;
    private int second;
    private int mistakes;
    private boolean correctAnswer = false;

    public Task(int id, int first, int second) {
        this.id = id;
        this.first = first;
        this.second = second;
    }

    public Task(int id, int first, int second, int mistakes) {
        this.id = id;
        this.first = first;
        this.second = second;
        this.mistakes = mistakes;
    }

    public String getCorrectAnswer()
    {
        int result = first * second;
        return String.valueOf(result);
    }

    public boolean CheckCorrectAnswer(String res)
    {
        if(res.equals(getCorrectAnswer()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean CheckEnteredText(String text)
    {
        if(text.length() > getCorrectAnswer().length())
        {
            return false;
        }
        if(getCorrectAnswer().substring(0, text.length()).equals(text))
        {
            return true;
        }
        else
        {
            return false;
        }
    }



    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public static Task [] GET_ALL_TASKS()
    {
        Task [] toReturn = new Task[QUESTION_COUNT];
        int counter = 0;
        for(int fir = 0; fir < 11; fir++)
        {
            for(int sec = 0; sec < 11; sec++)
            {
                if(fir * sec < 100)
                {
                    toReturn[counter] = new Task(counter, fir, sec);
                    counter++;
                }
            }
        }
        return toReturn;
    }

    @Override
    public String toString() {
        return first + " * " + second + " =";
    }

    public int getId() {
        return id;
    }

    public int getMistakes() {
        return mistakes;
    }

    public void setMistakes(int mistakes) {
        this.mistakes = mistakes;
    }

    public static Task[] GenerateQuestionForGame(int count)
    {
        Task [] toReturn = new Task[count];
        Set<Integer> uniqueNumbers = new HashSet<>();
        Random r = new Random();
        while(uniqueNumbers.size() < count)
        {
            uniqueNumbers.add(r.nextInt(QUESTION_COUNT));
        }

        int counter = 0;
        for(Integer i : uniqueNumbers) {
            toReturn[counter++] = Activity.GAME_TASKS[i];
        }

        return toReturn;
    }
}
