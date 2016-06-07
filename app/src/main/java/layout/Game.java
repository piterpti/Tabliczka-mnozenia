package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import adrian.kamil.tabliczkamnozenia.Activity;
import adrian.kamil.tabliczkamnozenia.Others.GameProgress;
import adrian.kamil.tabliczkamnozenia.Others.Level;
import adrian.kamil.tabliczkamnozenia.Others.Task;
import adrian.kamil.tabliczkamnozenia.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Game extends Fragment {

    private Button [] answerButtons;
    private TextView verdictTextView;
    private ProgressBar progressBar;
    private TextView progressTextView;
    private Button backToMenu;
    private TextView currentTaskTextView;

    private Task currentTask;


    public Game() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        answerButtons = new Button[10];
        answerButtons[0] = (Button) view.findViewById(R.id.game_button0);
        answerButtons[1] = (Button) view.findViewById(R.id.game_button1);
        answerButtons[2] = (Button) view.findViewById(R.id.game_button2);
        answerButtons[3] = (Button) view.findViewById(R.id.game_button3);
        answerButtons[4] = (Button) view.findViewById(R.id.game_button4);
        answerButtons[5] = (Button) view.findViewById(R.id.game_button5);
        answerButtons[6] = (Button) view.findViewById(R.id.game_button6);
        answerButtons[7] = (Button) view.findViewById(R.id.game_button7);
        answerButtons[8] = (Button) view.findViewById(R.id.game_button8);
        answerButtons[9] = (Button) view.findViewById(R.id.game_button9);
        verdictTextView = (TextView) view.findViewById(R.id.game_verdictTextView);
        progressBar = (ProgressBar) view.findViewById(R.id.game_progressBar);
        progressTextView = (TextView) view.findViewById(R.id.game_progressTextView);
        currentTaskTextView = (TextView) view.findViewById(R.id.game_taskTextView);
        backToMenu = (Button) view.findViewById(R.id.game_backToMenu);
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackToMenu();
            }
        });
        LoadQuestion();
    }

    public void CreateGame()
    {
        Level level = Activity.GAME_PROGRESS.getLevel();
        Activity.GAME_PROGRESS = new GameProgress(level);
        Activity.GAME_PROGRESS.setTasks(Task.GenerateQuestionForGame(Activity.GAME_PROGRESS.getLevel().getCount()));
    }

    private void LoadQuestion()
    {
        currentTask = Activity.GAME_PROGRESS.getCurrentTask();
        currentTaskTextView.setText(currentTask.toString());
    }

    public void BackToMenu()
    {
        Menu menu = new Menu();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, menu, Activity.MENU_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
