package adrian.kamil.tabliczkamnozenia;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import layout.Achievement;
import layout.Game;
import layout.Level;
import layout.Menu;

public class Activity extends AppCompatActivity {

    public static final String GAME_FRAGMENT_TAG = "GAME_FRAGMENT";
    public static final String GAME_ENDED_TAG = "GAME_ENDED";
    public static final String MENU_TAG  = "MENU";
    public static final String ACHIEVEMENT_TAG = "ACHIEVEMENT";

    public static Context CONTEXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        Menu menu = new Menu();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(savedInstanceState == null) {
            transaction.add(R.id.fragment_container, menu, MENU_TAG);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        CONTEXT = this;
    }

    public void ExitApp(View view) {
        System.exit(0);
    }

    public void NewGame(View view) {
        Game game = new Game();
        game.setRetainInstance(true);
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
        Level level = new Level();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, level, GAME_FRAGMENT_TAG);
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
