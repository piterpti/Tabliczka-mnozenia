package adrian.kamil.tabliczkamnozenia.Others;

/**
 * Created by Piter on 07/06/2016.
 */
public class GameProgress {

    private Level level;
    private Task [] tasks;
    private int currentTask;

    public GameProgress(Level level) {
        this.level = level;
        currentTask = 0;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setTasks(Task[] tasks) {
        this.tasks = tasks;
    }

    public Task getTask()
    {
        if(currentTask - 1 < level.getCount() )
            return tasks[currentTask - 1];
        else
            return tasks[0];
    }

    public boolean setToNextTaskIfExist()
    {
        currentTask++;
        if(currentTask - 1 >= level.getCount() )
            return false;
        else
            return true;
    }

    public int getCurrentTask() {
        return currentTask;
    }

    public int getCorrectAnswers()
    {
        if(tasks.length < 1)
            return -1;
        int correctAnswers = 0;
        for(Task task : tasks) {
            if(task.isCorrectAnswer())
                correctAnswers++;
        }
        return correctAnswers;
    }
}
