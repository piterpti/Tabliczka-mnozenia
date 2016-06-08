package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import adrian.kamil.tabliczkamnozenia.Activity;
import adrian.kamil.tabliczkamnozenia.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameEnd extends Fragment {

    private Button backMenuButton;
    private TextView resultTextView;


    public GameEnd() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_end, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        resultTextView = (TextView) view.findViewById(R.id.end_resultTextView);
        backMenuButton = (Button) view.findViewById(R.id.end_backToMenu);
        backMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackToMenu();
            }
        });

        int correctAnswers = Activity.GAME_PROGRESS.getCorrectAnswers();
        int questionCount = Activity.GAME_PROGRESS.getLevel().getCount();
        float percentAnswers = ((float) correctAnswers / (float) questionCount) * 100f;
        String result = String.format("%.2f", percentAnswers) + "%";
        result += " (" + correctAnswers + "/" + questionCount + ")";
        resultTextView.setText(result);
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
