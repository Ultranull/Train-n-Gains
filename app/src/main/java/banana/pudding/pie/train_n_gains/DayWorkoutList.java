package banana.pudding.pie.train_n_gains;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class DayWorkoutList extends Fragment {

    private WorkoutPlan wop;
    private ListView workoutList;
    private Adapter adapter;
    private Button startWorkoutButton;

    public DayWorkoutList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_day_workout_list, container, false);
        return view;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        workoutList=view.findViewById(R.id.day_workout_list);
        startWorkoutButton=view.findViewById(R.id.startWorkout);
        adapter=new Adapter(getContext(),R.layout.plan_list_item,wop.getWorkouts());
        workoutList.setAdapter(adapter);

    }


    public void setPlan(WorkoutPlan wp){
        wop=wp;
    }

    private class Adapter extends ArrayAdapter<WorkoutAction>{

        private final int resource;
        private List<WorkoutAction> objects;

        public Adapter(@NonNull Context context, int resource, @NonNull List<WorkoutAction> objects) {
            super(context, resource, objects);
            this.resource=resource;
            this.objects=objects;
        }



        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final Holder holder;
            if(convertView==null) {
                convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
                holder=new Holder();
                holder.title = convertView.findViewById(R.id.plan_list_workout_title);
                holder.progress=convertView.findViewById(R.id.plan_list_workout_progress);
                convertView.setTag(holder);
            }else{
                holder=(Holder)convertView.getTag();
            }
            holder.title.setText(position+". "+objects.get(position).getName());

            //FOR CLICKING LISTVIEW. KEEPING JUST IN CASE
            /*workoutList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), ActiveWorkout.class);
                    String wName = objects.get(position).getName();
                    intent.putExtra("WorkoutName", wName);
                    startActivity(intent);
                }
            });*/

            startWorkoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ActiveWorkout.class);
                    String wName = objects.get(0).getName();
                    intent.putExtra("WorkoutName", wName);
                    startActivity(intent);

                }
            });






            return convertView;
        }

        private class Holder {
            public TextView title,progress;
        }




    }

}
