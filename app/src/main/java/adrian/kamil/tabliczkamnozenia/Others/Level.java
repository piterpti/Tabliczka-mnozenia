package adrian.kamil.tabliczkamnozenia.Others;

/**
 * Created by Piter on 07/06/2016.
 */
public class Level {

    private int id;
    private String name;
    private int time;
    private int count;

    public Level(int id, String name, int time, int count) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
