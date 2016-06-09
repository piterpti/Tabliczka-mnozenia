package adrian.kamil.tabliczkamnozenia;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import adrian.kamil.tabliczkamnozenia.Others.Achievement;
import adrian.kamil.tabliczkamnozenia.Others.GameProgress;
import adrian.kamil.tabliczkamnozenia.Others.Level;
import adrian.kamil.tabliczkamnozenia.Others.Task;
import layout.AchievementFrag;
import layout.Game;
import layout.GameEnd;
import layout.LevelFrag;
import layout.Menu;
import java.util.*;

public class Activity extends AppCompatActivity {

    public static final String GAME_FRAGMENT_TAG = "GAME_FRAGMENT";
    public static final String GAME_ENDED_TAG = "GAME_ENDED";
    public static final String MENU_TAG  = "MENU";
    public static final String ACHIEVEMENT_TAG = "ACHIEVEMENT";
    public static final String DIFFICULT_LEVEL_KEY = "DIFFICULT_LEVEL";
    public static final String SUPER_MEMO_SHARED = "SUPER_MEMO_HELPER";
    public static final String ACHIEVEMENT_KEY = "ACHIEVEMENT_KEY";

    public static final Level [] LEVELS = Level.GET_LEVELS();
    public static GameProgress GAME_PROGRESS;
    public static Task[] GAME_TASKS;
    public static int[] UNLOCKED_ACHIEVEMENTS;
    public static Achievement[] ACHIEVEMENTS;

    public static Context CONTEXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        Menu menu = new Menu();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        CONTEXT = this;
        if(savedInstanceState == null) {
            transaction.add(R.id.fragment_container, menu, MENU_TAG);
            transaction.addToBackStack(null);
            transaction.commit();
            init();
            LoadLevel();
            LoadAchievements();
        }
    }

    private void LoadAchievements() {
        ACHIEVEMENTS = Achievement.CreateAchievements();
        UNLOCKED_ACHIEVEMENTS = new int[5];
        String achievements = PreferenceManager.getDefaultSharedPreferences(CONTEXT).getString(ACHIEVEMENT_KEY, "");
        if(achievements.length() < 1) {
            achievements = "0,0,0,0,0";
            PreferenceManager.getDefaultSharedPreferences(CONTEXT).edit().putString(ACHIEVEMENT_KEY, achievements).commit();
        } else {
            String ach[] = achievements.split(",");
            int counter = 0;
            for(String a : ach)
            {
                UNLOCKED_ACHIEVEMENTS[counter] = Integer.valueOf(a);
                counter++;
            }
        }
        for(int i = 0; i < UNLOCKED_ACHIEVEMENTS[GAME_PROGRESS.getLevel().getId() - 1]; i++) {
            ACHIEVEMENTS[i].setLocked(false);
        }
    }

    private void LoadSharedPreferencesToTasks() {
        String superMemo = PreferenceManager.getDefaultSharedPreferences(CONTEXT).getString(SUPER_MEMO_SHARED, "");
        if(superMemo.length() < 1) {
            for(Task t : GAME_TASKS) {
                superMemo += "0,";
                t.setMistakes(0);
            }
            PreferenceManager.getDefaultSharedPreferences(CONTEXT).edit().putString(SUPER_MEMO_SHARED, superMemo).commit();
        }
        else
        {
            int counter = 0;
            String[] mistakes = superMemo.split(",");
            for(Task t : GAME_TASKS) {
                t.setMistakes(Integer.valueOf(mistakes[counter]));
                counter++;
            }
        }
    }

    private void init() {
        GAME_TASKS = Task.GET_ALL_TASKS();
        LoadSharedPreferencesToTasks();
        Arrays.sort(GAME_TASKS);
        for(Task t : GAME_TASKS) {
            Log.d("blabla", t.toStringDebug());
        }
        GAME_PROGRESS = new GameProgress(null);

    }
    private void LoadLevel()
    {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        int lastDiffLevelId = sharedPreferences.getInt(DIFFICULT_LEVEL_KEY, 1);
        for(Level level : LEVELS) {
            if(level.getId() == lastDiffLevelId) {
                GAME_PROGRESS.setLevel(level);
            }
        }
    }

    public void ExitApp(View view) {
        System.exit(0);
    }

    public void NewGame(View view) {
        Game game = new Game();
        game.setRetainInstance(true);
        game.CreateGame();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, game, GAME_FRAGMENT_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void Achievements(View view) {
        AchievementFrag achievementFrag = new AchievementFrag();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, achievementFrag, ACHIEVEMENT_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
        LoadAchievements();
    }

    public void Level(View view) {
        LevelFrag levelFrag = new LevelFrag();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, levelFrag, GAME_FRAGMENT_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        Menu menuFragment = null;
        menuFragment = (Menu) getSupportFragmentManager().findFragmentByTag(MENU_TAG);
        if(menuFragment != null && menuFragment.isVisible()) {
            System.exit(0);
        }
        boolean callSuper = true;
        GameEnd gameEnd = null;
        gameEnd = (GameEnd) getSupportFragmentManager().findFragmentByTag(GAME_ENDED_TAG);
        if(gameEnd != null && gameEnd.isVisible())
        {
            gameEnd.BackToMenu();
            callSuper = false;
        }

        if(callSuper)
        {
            super.onBackPressed();
        }
    }
}
