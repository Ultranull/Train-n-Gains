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

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.joml.Vector3f;


public class ActiveWorkout extends AppCompatActivity  implements SensorEventListener, View.OnClickListener {

    private SensorManager sensorManager;

    private TextView workoutName, insructions, description,repsview;
    private Button completeWorkout,calibrate;
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
        description=findViewById(R.id.action_description);
repsview=findViewById(R.id.number_of_reps);

        completeWorkout = findViewById(R.id.completed_button);
        completeWorkout.setOnClickListener(this);

        calibrate=findViewById(R.id.calibrate_button);
        calibrate.setOnClickListener(this);


        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),sensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.calibrate_button:{
                capCalibrate=true;
            }break;
        }

    }
    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    private boolean capCalibrate=false,repping=false;
    private Vector3f down=new Vector3f(1,2,3);
    private double[] hist=new double[20];
    private int index=0;
    private int reps=0;

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            if (capCalibrate) {
                down = new Vector3f(
                        event.values[0],
                        event.values[1],
                        event.values[2]);
                capCalibrate=false;
            }else if(!down.equals(1,2,3)){
                Vector3f acc = new Vector3f(
                        event.values[0],
                        event.values[1],
                        event.values[2]);

                float diff = acc.dot(down)/down.length();
                hist[index%hist.length]=diff-9.81;
                index++;
            }
            double avg=0;
            DataPoint[] data=new DataPoint[hist.length];
            for(int i=0;i<hist.length;i++) {
                data[i]=new DataPoint(i,Math.round(hist[i]));
                avg+=Math.round(hist[i]);
            }
            if(avg<-5&&!repping)
                repping=true;
            else if(repping&&avg==0){
                repping=false;
                reps++;
            }
            repsview.setText(reps+"");


        }
    }

}
