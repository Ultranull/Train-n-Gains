package banana.pudding.pie.train_n_gains;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

import org.joml.Vector3f;


public class ActiveWorkout extends AppCompatActivity  implements SensorEventListener {

    private SensorManager sensorManager;

    private TextView workoutName, insructions, description;
    private Button completeWorkout;
    private String name, ins, des;
    private WorkoutPlan currentPlan;
    private DatabaseHelper myDB;
    private Cursor data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.active_workout);

        myDB = new DatabaseHelper(this);
        data = myDB.getListContents();

        workoutName = findViewById(R.id.action_title);
        name = getIntent().getExtras().getString("WorkoutName");
        workoutName.setText(name);

        insructions=findViewById(R.id.action_instructions);

        completeWorkout = findViewById(R.id.completed_button);
        completeWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    private Vector3f oldacc=new Vector3f(0);

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){

            Vector3f acc=new Vector3f(
                    event.values[0],
                    event.values[1],
                    event.values[2]);

            Vector3f dif=oldacc.sub(acc);
            insructions.setText(dif.toString());
            oldacc=acc;
        }
    }

}
