package banana.pudding.pie.train_n_gains;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ActiveWorkout extends AppCompatActivity {

    TextView workoutName, workoutInsructions, workoutDescription;
    Button completeWorkout;
    String name, ins, des;
    WorkoutPlan currentPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.active_workout);

        workoutName = findViewById(R.id.workoutTitle);
        name = getIntent().getExtras().getString("WorkoutName");
        workoutName.setText(name);


        completeWorkout = findViewById(R.id.complete);
        completeWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

    }



}
