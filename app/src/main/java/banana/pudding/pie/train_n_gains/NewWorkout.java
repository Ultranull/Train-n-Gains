package banana.pudding.pie.train_n_gains;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class NewWorkout extends AppCompatActivity {

    private Button add,addActivity;
    private EditText name,descr;
    private ListView activities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_workout);

        add=findViewById(R.id.new_workout_add_button);
        addActivity=findViewById(R.id.new_workout_add_action_button);
        name=findViewById(R.id.new_workout_name);
        descr=findViewById(R.id.new_workout_descr);

        activities=findViewById(R.id.workout_action_list);

    }

    private class Adapter extends ArrayAdapter<WorkoutAction> implements View.OnClickListener {

        private final int resource;
        private List<WorkoutAction> objects;

        public Adapter(@NonNull Context context, int resource, @NonNull List<WorkoutAction> objects) {
            super(context, resource, objects);
            this.resource=resource;
            this.objects=objects;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.new_workout_moveup:{}break;
                case R.id.new_workout_movedown:{}break;
                case R.id.new_workout_delete:{}break;
            }
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
                holder.up.setOnClickListener(this);
                holder.down.setOnClickListener(this);
                holder.delete.setOnClickListener(this);
                convertView.setTag(holder);
            }else{
                holder=(Holder)convertView.getTag();
            }
            holder.title.setText(objects.get(position).getName());

            return convertView;
        }

        private class Holder {
            public TextView title;
            public Button up,down,delete;
        }
    }
}
