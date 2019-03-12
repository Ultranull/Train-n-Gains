package banana.pudding.pie.train_n_gains;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.List;

import sun.bob.mcalendarview.vo.DateData;

public class NewWorkout extends AppCompatActivity implements View.OnClickListener {

    private Button add,addActivity;
    private ListView activities;
    private Adapter adapter;

    private WorkoutPlan wop;
    private DateData day;
    private DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_workout);

        Intent intent=getIntent();
        day=new DateData(
                intent.getIntExtra("year",0),
                intent.getIntExtra("month",0),
                intent.getIntExtra("day",0)
        );

        add=findViewById(R.id.new_workout_add_button);
        add.setOnClickListener(this);
        addActivity=findViewById(R.id.new_workout_add_action_button);
        addActivity.setOnClickListener(this);
        registerForContextMenu(addActivity);

        addActivity=findViewById(R.id.new_workout_add_action_button);

        activities=findViewById(R.id.workout_action_list);
        wop=new WorkoutPlan();
        adapter=new Adapter(this,R.layout.new_workout_actions_list_item,wop.getWorkouts(),this);
        activities.setAdapter(adapter);

        wop.addWorkout(new WorkoutAction(0,"sample","sample","",WorkoutAction.TYPE.ARMS));
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
        private List<WorkoutAction> objects;
        private View.OnClickListener listener;

        public Adapter(@NonNull Context context, int resource, @NonNull List<WorkoutAction> objects,View.OnClickListener l) {
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
            public TextView title;
            public Button up,down,delete;
        }
    }

}
