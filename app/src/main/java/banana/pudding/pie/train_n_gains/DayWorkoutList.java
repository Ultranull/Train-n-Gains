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

    private class Adapter extends ArrayAdapter<WorkoutAction> implements CheckBox.OnCheckedChangeListener{

        private final int resource;
        private ArrayList<Boolean> checkstates;
        private List<WorkoutAction> objects;

        public Adapter(@NonNull Context context, int resource, @NonNull List<WorkoutAction> objects) {
            super(context, resource, objects);
            this.resource=resource;
            this.checkstates=new ArrayList<>();
            for(int i=0;i<objects.size();i++)

            this.objects=objects;
        }

        public ArrayList<WorkoutAction> isChecked(){
            ArrayList<WorkoutAction> checked=new ArrayList<>();
            for(int i=0;i<checkstates.size();i++){
                if(checkstates.get(i))
                    checked.add(objects.get(i));
            }
            return checked;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            checkstates.set((Integer) buttonView.getTag(),isChecked);
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
                holder.cb = convertView.findViewById(R.id.plan_list_workout_done);
                holder.cb.setOnCheckedChangeListener(this);
                convertView.setTag(holder);
            }else{
                holder=(Holder)convertView.getTag();
            }
            holder.title.setText(position+". "+objects.get(position).getName());
            holder.cb.setChecked(checkstates.get(position));
            holder.cb.setTag(position);

            return convertView;
        }

        private class Holder {
            public TextView title,progress;
            public CheckBox cb;
        }
    }

}
