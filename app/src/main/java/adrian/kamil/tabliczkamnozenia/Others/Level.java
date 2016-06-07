package adrian.kamil.tabliczkamnozenia.Others;

/**
 * Created by Piter on 07/06/2016.
 */
public class Level {

    private int id;
    private String name;
    private int time;
    private int count;
    private boolean locked = true;

    public Level(int id, String name, int time, int count) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.count = count;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
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

    public static Level[] GET_LEVELS() {
        return new Level[] {new Level(1, "Bardzo łatwy",30, 10),
                new Level(2, "Łatwy",20, 20),
                new Level(3, "Średni",15, 30),
                new Level(4, "Trudny",10, 40),
                new Level(5, "Bardzo trudny",5, 50)};
    }

    @Override
    public boolean equals(Object o) {
        if(id == (((Level)o)).getId())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
