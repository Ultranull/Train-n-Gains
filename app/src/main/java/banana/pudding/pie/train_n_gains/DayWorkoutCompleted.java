package banana.pudding.pie.train_n_gains;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DayWorkoutCompleted extends Fragment {

    private ListView workoutList;
    private Adapter adapter;
    private DatabaseHelper myDB;
    private String dValue;
    private String mValue;
    private String dayValue;
    private String monthValue;

    public DayWorkoutCompleted() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_day_workout_completed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}
