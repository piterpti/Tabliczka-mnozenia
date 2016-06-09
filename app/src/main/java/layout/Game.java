package layout;


import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
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

    private final int PROGRESS_BAR_CHANGE = 20;
    private final int TIME_TO_WAIT = 1000;

    private static final int GOOD_ANSWER_COLOR = Color.GREEN;
    private static final int WRONG_ANSWER_COLOR = Color.RED;

    private Button [] answerButtons;
    private TextView verdictTextView;
    private ProgressBar progressBar;
    private TextView progressTextView;
    private Button backToMenu;
    private TextView currentTaskTextView;
    private String enteredText = "";
    private String task;
    private CountDownTimer answerTimer;
    private boolean nextQuestion;

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
        AddListenerToButtons();
        LoadQuestion();
    }

    private void AddListenerToButtons() {
        for(Button b : answerButtons)
        {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button button = (Button) v;
                    enteredText += button.getText();
                    currentTaskTextView.setText(task + " " + enteredText);
                    if(currentTask.CheckCorrectAnswer(enteredText)) {
                        Verdict(true);
                    }
                    else if(currentTask.CheckEnteredText(enteredText)) {

                    }
                    else {
                        Verdict(false);
                    }
                }
            });
        }
    }

    private void Verdict(boolean correct)
    {
        enteredText = "";
        TurnOffTimer();
        verdictTextView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        LockUnlockButtons(false);
        currentTask.setCorrectAnswer(correct);
        if(correct)
        {
            verdictTextView.setText(getResources().getString(R.string.game_goodAnswer));
            verdictTextView.setTextColor(GOOD_ANSWER_COLOR);
        }
        else
        {
            verdictTextView.setText(getResources().getString(R.string.game_wrongAnswer));
            verdictTextView.setTextColor(WRONG_ANSWER_COLOR);
            currentTaskTextView.setVisibility(View.INVISIBLE);
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                verdictTextView.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                currentTaskTextView.setVisibility(View.VISIBLE);
                nextQuestion = true;
                LoadQuestion();
                LockUnlockButtons(true);
            }
        }, TIME_TO_WAIT);
    }

    private void LockUnlockButtons(boolean onOff)
    {
        for(Button button : answerButtons)
        {
            button.setEnabled(onOff);
        }
    }

    public void CreateGame()
    {
        Level level = Activity.GAME_PROGRESS.getLevel();
        Activity.GAME_PROGRESS = new GameProgress(level);
        Activity.GAME_PROGRESS.setTasks(Task.GenerateQuestionForGame(Activity.GAME_PROGRESS.getLevel().getCount()));
        nextQuestion = true;
    }

    private void LoadQuestion()
    {
        if(nextQuestion)
        {
            nextQuestion = false;
            if(!Activity.GAME_PROGRESS.setToNextTaskIfExist())
            {
                GoToSummary();
                return;
            }
            CreateTimer(Activity.GAME_PROGRESS.getLevel().getTime() * 1000);
        }
        progressTextView.setText(getResources().getString(R.string.game_task) + ": " + Activity.GAME_PROGRESS.getCurrentTask() + "/" + Activity.GAME_PROGRESS.getLevel().getCount());
        currentTask = Activity.GAME_PROGRESS.getTask();
        task = currentTask.toString();
        currentTaskTextView.setText(task + " " + enteredText);
    }

    private void GoToSummary() {
        GameEnd gameEnd = new GameEnd();
        gameEnd.setRetainInstance(true);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, gameEnd, Activity.GAME_ENDED_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void BackToMenu()
    {
        TurnOffTimer();
        Menu menu = new Menu();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, menu, Activity.MENU_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void CreateTimer(int time) {
        TurnOffTimer();
        progressBar.setProgress(100);
        answerTimer = new CountDownTimer(time, PROGRESS_BAR_CHANGE) {
            @Override
            public void onTick(long millisUntilFinished) {
                float ppp = millisUntilFinished / Activity.GAME_PROGRESS.getLevel().getTime() / 10;
                progressBar.setProgress((int) ppp);
            }

            @Override
            public void onFinish() {
                Verdict(false);
            }
        };
        answerTimer.start();
    }

    private void TurnOffTimer()
    {
        if(answerTimer != null) {
            answerTimer.cancel();
            answerTimer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        TurnOffTimer();
    }
}
