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

    public Task[] getTasks() {
        return tasks;
    }

    public void setTasks(Task[] tasks) {
        this.tasks = tasks;
    }

    public Task getCurrentTask()
    {
        return tasks[currentTask];
    }

    public boolean setToNextTaskIfExist()
    {
        currentTask++;
        if(tasks.length >= currentTask)
            return false;
        else
            return true;
    }
}
