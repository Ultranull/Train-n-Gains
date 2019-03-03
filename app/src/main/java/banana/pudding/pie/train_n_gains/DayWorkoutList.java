package banana.pudding.pie.train_n_gains;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class DayWorkoutList extends Fragment {

    private WorkoutPlan wop;
    private ListView workoutList;
    private Adapter adapter;

    public DayWorkoutList() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        workoutList=view.findViewById(R.id.day_workout_list);
        adapter=new Adapter(getContext(),R.layout.plan_list_item,wop.getWorkouts());
        workoutList.setAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_day_workout_list, container, false);
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
            Holder holder;
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

            return convertView;
        }

        private class Holder {
            public TextView title,progress;
        }
    }

}
