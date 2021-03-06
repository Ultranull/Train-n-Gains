package banana.pudding.pie.train_n_gains;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sun.bob.mcalendarview.vo.DateData;

public class NewWorkout extends AppCompatActivity implements View.OnClickListener {

    private Button addActivity;
    private Adapter adapter;
    private EditText newName;
    private EditText newDesc;
    private EditText newIns;

    private WorkoutPlan wop;
    private DateData day;
    private DatabaseHelper myDB;
    private String d;
    private String m;


    private final ArrayList<String> actionsList = new ArrayList<>();


    class Numbers {
        final Random randnum;

        Numbers() {
            randnum = new Random();
        }

        int random(int i){
            return randnum.nextInt(i);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_workout);
        myDB = new DatabaseHelper(this);

        Intent intent=getIntent();
        day=new DateData(
                intent.getIntExtra("year",0),
                intent.getIntExtra("month",0),
                intent.getIntExtra("day",0)
        );

        newName=findViewById(R.id.newName);
        newDesc=findViewById(R.id.newDesc);
        newIns=findViewById(R.id.newIns);
        Button create = findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newWorkoutName = newName.getText().toString();
                String newWorkoutDescription = newDesc.getText().toString();
                String newWorkoutInstruction = newIns.getText().toString();

                if(!newWorkoutName.equals(""))
                {
                    ActionManager am=WorkoutSchedule.getInstance().actionManager;
                    int i = am.actions.size() + 1;
                    am.actions.put(i, new WorkoutAction(i, newWorkoutName, newWorkoutDescription, newWorkoutInstruction, WorkoutAction.TYPE.OTHER));

                    newName.setText("");
                    newDesc.setText("");
                    newIns.setText("");
                    Toast.makeText(NewWorkout.this, "Successfully added new workout.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(NewWorkout.this, "You Need To Enter A New Workout Name", Toast.LENGTH_LONG).show();
                }
            }
        });


        Button add = findViewById(R.id.new_workout_add_button);
        add.setOnClickListener(this);
        addActivity=findViewById(R.id.new_workout_add_action_button);
        addActivity.setOnClickListener(this);
        registerForContextMenu(addActivity);

        addActivity=findViewById(R.id.new_workout_add_action_button);

        ListView activities = findViewById(R.id.workout_action_list);
        wop=new WorkoutPlan();
        adapter=new Adapter(this,R.layout.new_workout_actions_list_item,wop.getWorkouts(),this);
        activities.setAdapter(adapter);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        d = prefs.getString("wDay", day.getDayString());
        m = prefs.getString("wMonth", day.getMonthString());

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Activities:");
        ActionManager am=WorkoutSchedule.getInstance().actionManager;
        ArrayList<WorkoutAction> actions=am.getActions();
        for(int i=0;i<actions.size();i++)
            menu.add(0,actions.get(i).getId(),0,actions.get(i).getName());

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ActionManager am=WorkoutSchedule.getInstance().actionManager;
        wop.addWorkout(am.getAction(item.getItemId()));

        Numbers r = new Numbers();
        int temp = r.random(1000000) + 1;
        String tempString = String.valueOf(temp);
        actionsList.add(tempString);

        AddData(am.getAction(item.getItemId()), d, m, tempString);
        adapter.notifyDataSetChanged();
        return super.onContextItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Object tag=v.getTag();
        int i=tag!=null?(int)tag:-1;
        switch (v.getId()){
            case R.id.new_workout_moveup:{
                wop.moveUp(i);
            }break;
            case R.id.new_workout_movedown:{
                wop.moveDown(i);
            }break;
            case R.id.new_workout_delete:{
                wop.removeWorkout(i);
                String deleteThis = actionsList.get(i);
                Log.i("TEST DELETE", deleteThis);
                myDB.deleteData(deleteThis);
            }break;
            case R.id.new_workout_add_action_button:{
                openContextMenu(addActivity);
            }break;
            case R.id.new_workout_add_button:{
                WorkoutSchedule.getInstance().addWorkoutPlan(day,wop);
                finish();
            }break;

        }
        adapter.notifyDataSetChanged();
    }

    private class Adapter extends ArrayAdapter<WorkoutAction> {

        private final int resource;
        private final List<WorkoutAction> objects;
        private final View.OnClickListener listener;

        Adapter(@NonNull Context context, int resource, @NonNull List<WorkoutAction> objects, View.OnClickListener l) {
            super(context, resource, objects);
            this.resource=resource;
            this.objects=objects;
            listener=l;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Holder holder;
            if(convertView==null) {
                convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
                holder=new Holder();
                holder.title = convertView.findViewById(R.id.new_workout_action_name);
                holder.up=convertView.findViewById(R.id.new_workout_moveup);
                holder.down=convertView.findViewById(R.id.new_workout_movedown);
                holder.delete=convertView.findViewById(R.id.new_workout_delete);
                holder.up.setOnClickListener(listener);
                holder.down.setOnClickListener(listener);
                holder.delete.setOnClickListener(listener);
                convertView.setTag(holder);
            }else{
                holder=(Holder)convertView.getTag();
            }
            holder.title.setText(objects.get(position).getName());
            holder.delete.setTag(position);
            holder.up.setTag(position);
            holder.down.setTag(position);

            return convertView;
        }

        private class Holder {
            TextView title;
            Button up;
            Button down;
            Button delete;
        }
    }


    private void AddData(WorkoutAction a, String day, String month, String completed)
    {
        boolean insertData = myDB.addData(a, day, month, completed);

        if(insertData){
            Toast.makeText(this, "Successfully Entered Workout Information!", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "You Can Only Enter 1 of the Same Workout!", Toast.LENGTH_LONG).show();
        }
    }



}
