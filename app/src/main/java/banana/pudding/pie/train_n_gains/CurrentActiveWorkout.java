package banana.pudding.pie.train_n_gains;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;



public class CurrentActiveWorkout extends Fragment {

    private TextView workoutName, workoutInsructions, workoutDescription;
    private Button completeWorkout;
    private String name, ins, des;
    private WorkoutPlan currentPlan;

    public CurrentActiveWorkout() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_active_workout, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //workoutName = view.findViewById(R.id.workoutTitle);
        //name = getActivity().getIntent().getExtras().getString("WorkoutName");
        //workoutName.setText(name);







    }




}
