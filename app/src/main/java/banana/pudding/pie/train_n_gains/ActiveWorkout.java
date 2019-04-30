package banana.pudding.pie.train_n_gains;

import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;


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

    private Chronometer chronometer;
    private boolean running;


    ArrayList<String> nameList = new ArrayList<>();
    ArrayList<String> descriptionList = new ArrayList<>();
    ArrayList<String> instructionsList = new ArrayList<>();
    ArrayList<String> typeList = new ArrayList<>();
    ArrayList<String> completionList = new ArrayList<>();

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
        chronometer=findViewById(R.id.chronometer);


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
                    completionList.add(data.getString(7));
                }
            }
        }

        //set TextViews equal to the first item in each list
        workoutName.setText(nameList.get(itr));
        description.setText(descriptionList.get(itr));
        instructions.setText(instructionsList.get(itr));
        //myDB.updateCompletion("0", completionList.get(itr));

        //Start Chronometer for the first exercise
        if(!running) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            running = true;
        }

        completeWorkout.setOnClickListener(this);

        calibrate.setOnClickListener(this);


        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),sensorManager.SENSOR_DELAY_FASTEST);


    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.calibrate_button:{
                capCalibrate=true;
            }break;
            case R.id.completed_button:{
                myDB.updateCompletion("sets: "+sets+", reps: "+(reps-1)+" time: "+chronometer.getContentDescription(), completionList.get(itr));

                if(size < nameList.size())
                {
                    itr++;
                    size++;

                    repsview.setText("0");
                    setsview.setText("0");


                    capCalibrate=false;
                    repping=false;
                    down=new Vector3f(1,2,3);
                    hist=new double[20];
                    index=0;
                    reps=1;
                    sets=0;
                    detects=1;

                    workoutName.setText(nameList.get(itr));
                    description.setText(descriptionList.get(itr));
                    instructions.setText(instructionsList.get(itr));
                    //myDB.updateCompletion("0", completionList.get(itr));

                    //Reset chronometer for next workout
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();

                }
                else
                {
                    finish();
                }
            }break;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION));
        System.out.println("sensor stopped");
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),sensorManager.SENSOR_DELAY_FASTEST);
        System.out.println("sensor resumed");
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    private boolean capCalibrate=false,repping=false;
    private Vector3f down=new Vector3f(1,2,3);
    private double[] hist=new double[20];
    private int index=0;
    private int reps=1,sets=0,detects=1;

    @Override
    public void onSensorChanged(SensorEvent event) {
        //Definition wise, a set is between 8-12 repetitions. So when the repsTextView is between 8-12, set setTextView accordingly.
        if (event.sensor.getType()==Sensor.TYPE_LINEAR_ACCELERATION){
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
                hist[index%hist.length]=diff;
                index++;
            }
            double avg=0;
            for(int i=0;i<hist.length;i++) {
                avg+=Math.round(hist[i]);
            }
            if(Math.abs(avg)>=10&&!repping)
                repping=true;
            else if(repping&&avg==0){
                repping=false;
                detects++;
                if((detects)%3==0) {
                    reps++;
                    if ((reps) % 9 == 0)
                        sets++;
                }
            }
            repsview.setText((reps-1)+"");
            setsview.setText(sets+"");

        }
    }







}
