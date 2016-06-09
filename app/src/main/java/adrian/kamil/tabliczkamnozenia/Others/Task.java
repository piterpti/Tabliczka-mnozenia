package adrian.kamil.tabliczkamnozenia.Others;

import android.util.Log;
import java.util.*;

import adrian.kamil.tabliczkamnozenia.Activity;

/**
 * Created by Piter on 07/06/2016.
 */
public class Task implements Comparable<Task>{

    private final static int QUESTION_COUNT = 120;

    private int id;
    private int first;
    private int second;
    private int mistakes;
    private boolean correctAnswer = false;

    public Task(Task t) {
        this.id = t.getId();
        this.first = t.getFirst();
        this.second = t.getSecond();
        this.mistakes = t.getMistakes();
    }

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

    public void setCorrectAnswer(boolean correctAnswer)
    {
        this.correctAnswer = correctAnswer;
        if(correctAnswer)
        {
            mistakes--;
        }
        else
        {
            mistakes++;
        }
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

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }


    @Override
    public String toString() {
        return first + " * " + second + " =";
    }

    public String toStringDebug() {
        return first + " * " + second + " =" + "| " + mistakes;
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
        Task[] toReturn = new Task[count];

        for(int i = 0; i < count; i++)
        {
            toReturn[i] = Activity.GAME_TASKS[i];
        }
        Arrays.sort(toReturn, new Comparator<Task>() {
            @Override
            public int compare(Task lhs, Task rhs) {
                if(new Random().nextBoolean())
                    return 1;
                else
                    return -1;
            }
        });
        return toReturn;
    }

    @Override
    public int compareTo(Task another) {
        if(mistakes > another.mistakes)
            return -5;
        else if(mistakes == another.mistakes)
        {
            if(new Random().nextBoolean())
                return 1;
            else
                return -1;
        }
        else
            return 5;
    }
}
