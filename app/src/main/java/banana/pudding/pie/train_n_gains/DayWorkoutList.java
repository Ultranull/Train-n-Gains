package banana.pudding.pie.train_n_gains;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import sun.bob.mcalendarview.vo.DateData;


public class DayWorkoutList extends Fragment {

    private String dayValue;
    private String monthValue;
    public DateData newDate;

    public DayWorkoutList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_day_workout_list, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        DatabaseHelper myDB = new DatabaseHelper(getContext());
        final Cursor data = myDB.getListContents();
        final ArrayList<String> theList = new ArrayList<>();
        final ArrayList<String> completionList = new ArrayList<>();
        dayValue = getArguments().getString("WorkoutDay");
        monthValue = getArguments().getString("WorkoutMonth");

        ListView workoutList = view.findViewById(R.id.day_workout_list);
        Button startWorkoutButton = view.findViewById(R.id.startWorkout);
        DateData today=WorkoutSchedule.today();
        if(today.getDay()<Integer.parseInt(dayValue)&&
                today.getMonth()<=Integer.parseInt(monthValue))
            startWorkoutButton.setVisibility(View.GONE);


        {
            while(data.moveToNext()){

                String dValue = data.getString(5);
                String mValue = data.getString(6);
                String completedValue = data.getString(7);


                if(dValue.equals(dayValue) && mValue.equals(monthValue) ) {
                    try {
                        Integer.parseInt(completedValue);
                        theList.add(data.getString(1));
                        Adapter adapter = new Adapter(Objects.requireNonNull(getContext()), R.layout.plan_list_item, theList);
                        workoutList.setAdapter(adapter);

                    }catch (Exception e){
                        completionList.add(data.getString(1)+"~"+ completedValue);
                    }
                }
            }
        }


        //date.setDay(1);
        //date.setMonth(1);
        //date.setYear(1);
        if(theList.isEmpty() && completionList.isEmpty())
        {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor editor = prefs.edit();

            DayWorkoutEmpty dwe=new DayWorkoutEmpty();
            dwe.date=newDate;

            editor.putString("wDay", dayValue);
            editor.putString("wMonth", monthValue);

            fragmentTransaction.replace(R.id.frameLayout, dwe);
            fragmentTransaction.commit();
        }
        else if(theList.isEmpty())
        {

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            DayWorkoutCompleted dayWorkoutCompleted = new DayWorkoutCompleted();
            dayWorkoutCompleted.setList(completionList);
            fragmentTransaction.replace(R.id.frameLayout, dayWorkoutCompleted);
            fragmentTransaction.commit();

        }


        startWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActiveWorkout.class);
                intent.putExtra("DV", dayValue);
                intent.putExtra("MV", monthValue);
                startActivity(intent);

            }
        });
    }


    private class Adapter extends ArrayAdapter<String>{

        private final int resource;
        private final List<String> objects;

        Adapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
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
                convertView.setTag(holder);
            }else{
                holder=(Holder)convertView.getTag();
            }
            holder.title.setText(position+1+". "+objects.get(position));

            return convertView;
        }

        private class Holder {
            TextView title;
        }




    }

}
