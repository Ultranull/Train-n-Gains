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

import java.util.ArrayList;


public class ActiveWorkout extends AppCompatActivity  implements SensorEventListener, View.OnClickListener {

    private SensorManager sensorManager;
    private TextView workoutName, instructions, description,repsview, setsview;
    private Button completeWorkout,calibrate;
    private String day, month, ins, des, dValue, mValue;
    private int itr = 0, size=1;
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

        final ArrayList<String> nameList = new ArrayList<>();
        final ArrayList<String> descriptionList = new ArrayList<>();
        final ArrayList<String> instructionsList = new ArrayList<>();
        final ArrayList<String> typeList = new ArrayList<>();

        //Get the Day and Month for the started workout list.
        day = getIntent().getExtras().getString("DV");
        month = getIntent().getExtras().getString("MV");

        workoutName = findViewById(R.id.action_title);
        instructions=findViewById(R.id.action_instructions);
        description=findViewById(R.id.action_description);
        repsview=findViewById(R.id.number_of_reps);
        setsview=findViewById(R.id.number_of_sets);
        completeWorkout = findViewById(R.id.completed_button);
        calibrate=findViewById(R.id.calibrate_button);


        {
            while(data.moveToNext()){

                dValue = data.getString(5);
                mValue = data.getString(6);

                if(dValue.equals(day) && mValue.equals(month))
                {
                    nameList.add(data.getString(1));
                    descriptionList.add(data.getString(2));
                    instructionsList.add(data.getString(3));
                    typeList.add(data.getString(4));
                }
            }
        }

        //set TextViews equal to the first item in each list
        workoutName.setText(nameList.get(itr));
        description.setText(descriptionList.get(itr));
        instructions.setText(instructionsList.get(itr));

        completeWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(size < nameList.size())
                {
                    itr++;
                    size++;
                    repsview.setText("");
                    setsview.setText("");
                    workoutName.setText(nameList.get(itr));
                    description.setText(descriptionList.get(itr));
                    instructions.setText(instructionsList.get(itr));
                }
                else
                {
                    finish();
                }
            }
        });

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
