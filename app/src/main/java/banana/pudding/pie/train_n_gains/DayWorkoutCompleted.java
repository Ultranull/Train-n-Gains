package banana.pudding.pie.train_n_gains;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class DayWorkoutCompleted extends Fragment {


    private ArrayList<String> list;

    public DayWorkoutCompleted() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_day_workout_completed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView workoutList = view.findViewById(R.id.list);

        Adapter adapter = new Adapter(Objects.requireNonNull(getContext()), R.layout.plan_list_item, new ArrayList<>(list));
        workoutList.setAdapter(adapter);

    }
    void setList(ArrayList<String> l){
        list=l;
    }

    private class Adapter extends ArrayAdapter<String> {

        private final int resource;
        private final List<String> objects;

         Adapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
            this.resource = resource;
            this.objects = objects;
        }


        @SuppressLint("SetTextI18n")
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final Holder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
                holder = new Holder();
                holder.title = convertView.findViewById(R.id.plan_list_workout_title);
                holder.progress = convertView.findViewById(R.id.plan_list_workout_progress);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            String[] s = objects.get(position).split("~");
            holder.title.setText(position + 1 + ". " + s[0]);
            holder.progress.setText(s[1]);

            return convertView;
        }

        private class Holder {
             TextView title, progress;
        }
    }
}
