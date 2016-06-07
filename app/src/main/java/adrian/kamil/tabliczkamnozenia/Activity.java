package adrian.kamil.tabliczkamnozenia;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import adrian.kamil.tabliczkamnozenia.Others.GameProgress;
import adrian.kamil.tabliczkamnozenia.Others.Level;
import adrian.kamil.tabliczkamnozenia.Others.Task;
import layout.Achievement;
import layout.Game;
import layout.LevelFrag;
import layout.Menu;

public class Activity extends AppCompatActivity {

    public static final String GAME_FRAGMENT_TAG = "GAME_FRAGMENT";
    public static final String GAME_ENDED_TAG = "GAME_ENDED";
    public static final String MENU_TAG  = "MENU";
    public static final String ACHIEVEMENT_TAG = "ACHIEVEMENT";
    public static final String DIFFICULT_LEVEL_KEY = "DIFFICULT_LEVEL";
    public static final String SUPER_MEMO_SHARED = "SUPER_MEMO_HELPER";

    public static final Level [] LEVELS = Level.GET_LEVELS();
    public static GameProgress GAME_PROGRESS;
    public static Task[] GAME_TASKS;

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
                Log.d("blabla", t.toString());
            }
        }
    }

    private void init() {
        GAME_TASKS = Task.GET_ALL_TASKS();
        LoadSharedPreferencesToTasks();
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
        Achievement achievement = new Achievement();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, achievement, ACHIEVEMENT_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
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
        super.onBackPressed();
    }
}
