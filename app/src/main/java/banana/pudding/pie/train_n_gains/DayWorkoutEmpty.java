package banana.pudding.pie.train_n_gains;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import sun.bob.mcalendarview.vo.DateData;


/**
 * A simple {@link Fragment} subclass.
 */
public class DayWorkoutEmpty extends Fragment {


    public DateData date;


    public DayWorkoutEmpty() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //final String dayValue = getArguments().getString("WorkoutDay");
        //final String monthValue = getArguments().getString("WorkoutMonth");


        Button add=view.findViewById(R.id.add_plan_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),NewWorkout.class);
                if(date==null)
                    date=WorkoutSchedule.today();
                intent.putExtra("day",date.getDay());
                intent.putExtra("month",date.getMonth());
                intent.putExtra("year",date.getYear());
                startActivity(intent);
            }
        });

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_day_workout_empty, container, false);
    }

}
