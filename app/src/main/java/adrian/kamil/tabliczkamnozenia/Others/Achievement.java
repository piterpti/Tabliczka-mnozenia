package adrian.kamil.tabliczkamnozenia.Others;

/**
 * Created by Piter on 07/06/2016.
 */
public class Achievement {

    private static final String a25percent = "25% poprawnych odpowiedzi";
    private static final String a50percent = "50% poprawnych odpowiedzi";
    private static final String a75percent = "75% poprawnych odpowiedzi";
    private static final String a100percent = "100% poprawnych odpowiedzi";

    private int id;
    private String name;
    private int correctAnswersPercent;
    private boolean locked;

    public Achievement(int id, String name, int correctAnswersPercent, boolean locked) {
        this.id = id;
        this.name = name;
        this.correctAnswersPercent = correctAnswersPercent;
        this.locked = locked;
    }

    public static Achievement[] CreateAchievements()
    {
        return new Achievement[] {
                new Achievement(1,a25percent, 25, true),
                new Achievement(2,a50percent, 50, true),
                new Achievement(3,a75percent, 75, true),
                new Achievement(4,a100percent, 100, true),
        };
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getName() {
        return name;
    }
}
