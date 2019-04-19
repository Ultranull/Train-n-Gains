package banana.pudding.pie.train_n_gains;

import android.app.Activity;
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

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import sun.bob.mcalendarview.vo.DateData;


public class DayWorkoutList extends Fragment {

    private WorkoutPlan wop;
    private ListView workoutList;
    private Adapter adapter;
    private Button startWorkoutButton;
    private DatabaseHelper myDB;
    private String dValue;
    private String mValue;
    private String completedValue;
    private String dayValue;
    private String monthValue;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    public DateData newDate;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        myDB = new DatabaseHelper(getContext());
        final Cursor data = myDB.getListContents();
        final ArrayList<String> theList = new ArrayList<>();
        final ArrayList<String> completionList = new ArrayList<>();
        dayValue = getArguments().getString("WorkoutDay");
        monthValue = getArguments().getString("WorkoutMonth").toString();

        workoutList=view.findViewById(R.id.day_workout_list);
        startWorkoutButton=view.findViewById(R.id.startWorkout);
        DateData today=WorkoutSchedule.today();
        if(today.getDay()<Integer.parseInt(dayValue)&&
                today.getMonth()<=Integer.parseInt(monthValue))
        startWorkoutButton.setVisibility(View.GONE);


        {
            while(data.moveToNext()){

                dValue = data.getString(5);
                mValue = data.getString(6);
                completedValue = data.getString(7);

                if(dValue.equals(dayValue) && mValue.equals(monthValue) && !completedValue.equals("0"))
                {
                    theList.add(data.getString(1));
                    adapter=new Adapter(getContext(),R.layout.plan_list_item, theList);
                    workoutList.setAdapter(adapter);
                }
                else if(dValue.equals(dayValue) && mValue.equals(monthValue) && completedValue.equals("0"))
                {
                    completionList.add(data.getString(1));
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
            prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            editor = prefs.edit();

            DayWorkoutEmpty dwe=new DayWorkoutEmpty();
            dwe.date=newDate;

            editor.putString("wDay", dayValue);
            editor.putString("wMonth", monthValue);

            fragmentTransaction.replace(R.id.frameLayout, dwe);
            fragmentTransaction.commit();
        }
        else if(theList.isEmpty() && !completionList.isEmpty())
        {

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            DayWorkoutCompleted dayWorkoutCompleted = new DayWorkoutCompleted();
            fragmentTransaction.replace(R.id.frameLayout, dayWorkoutCompleted);
            fragmentTransaction.commit();

        }


    }


    public void setPlan(WorkoutPlan wp){
        wop=wp;
    }

    private class Adapter extends ArrayAdapter<String>{

        private final int resource;
        private List<String> objects;

        public Adapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
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
            holder.title.setText(position+1+". "+objects.get(position).toString());


            startWorkoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ActiveWorkout.class);
                    intent.putExtra("DV", dayValue);
                    intent.putExtra("MV", monthValue);
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
