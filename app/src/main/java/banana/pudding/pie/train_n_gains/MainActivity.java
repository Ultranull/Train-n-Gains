package banana.pudding.pie.train_n_gains;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.listeners.OnMonthChangeListener;
import sun.bob.mcalendarview.vo.DateData;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MCalendarView calender;
    private TextView datetitle,monthtitle;
    private Button totoday;
    private ImageButton settingb;
    private MarkStyle selected, planned;
    private DateData lastday;
    private DatabaseHelper myDB;
    private Cursor data;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private String dayValue;
    private String monthValue;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);

        myDB = new DatabaseHelper(this);
        data = myDB.getListContents();

        selected=new MarkStyle(MarkStyle.BACKGROUND,Color.CYAN);
        planned=new MarkStyle(MarkStyle.DOT,Color.GREEN);

        calender=findViewById(R.id.CV);
        datetitle=findViewById(R.id.day_title);
        monthtitle=findViewById(R.id.month_title);
        monthtitle.setOnClickListener(new View.OnClickListener() {
            int count=0;
            @Override
            public void onClick(View v) {

                count++;
                if(count==2){
                    myDB.clearTables();
                    count=0;
                    System.out.println("cleared");
                }
            }
        });

        calender.setOnMonthChangeListener(new MonthListener());
        calender.setOnDateClickListener(new DayListener());
        //calender.hasTitle(false);

        totoday=findViewById(R.id.move_to_today);
        totoday.setOnClickListener(this);
        settingb=findViewById(R.id.settings);
        settingb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Settings.class));
            }
        });


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        DateData today=WorkoutSchedule.today();
        monthtitle.setText(WorkoutSchedule.getMonthForInt(today.getMonth())+" : "+today.getYear());

        WorkoutSchedule wos=WorkoutSchedule.getInstance();
        WorkoutPlan fortoday=wos.getAt(today);
        if(fortoday!=null) {
            DayWorkoutList dwl=new DayWorkoutList();
            dwl.setPlan(fortoday);

            Bundle bundle = new Bundle();
            bundle.putString("WorkoutDay", "");
            bundle.putString("WorkoutMonth", "");
            dwl.setArguments(bundle);
            getFragmentManager().beginTransaction();

            fragmentTransaction.add(R.id.frameLayout, dwl);
            fragmentTransaction.commit();
        }else{
            DayWorkoutEmpty dwe=new DayWorkoutEmpty();
            dwe.date=today;
            fragmentTransaction.add(R.id.frameLayout, dwe);
            fragmentTransaction.commit();
        }

        lastday=today;


        for(DateData d:wos.dates)
            calender.markDate(d.setMarkStyle(planned));


        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }

    @Override
    public void onClick(View v) {
        calender.travelTo(WorkoutSchedule.today());
    }
    private class DayListener extends OnDateClickListener{
        @SuppressLint("SetTextI18n")
        @Override
        public void onDateClick(View view, DateData date) {
            ArrayList<DateData> marked=calender.getMarkedDates().getAll();
            for(int i=0;i<marked.size();i++) {
                MarkStyle s=marked.get(i).getMarkStyle();
                if (s.getColor()==selected.getColor()&&s.getStyle()==selected.getStyle())
                    calender.unMarkDate(marked.get(i));
            }

            if(!calender.getMarkedDates().getAll().contains(date)) {
                calender.markDate(date.setMarkStyle(selected));
            }

            if(date.equals(WorkoutSchedule.today())) {
                datetitle.setText("Today");
            }
            else {
                datetitle.setText(WorkoutSchedule.getMonthForInt(date.getMonth()) + "/" + date.getDay() + ":");
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            WorkoutSchedule wos=WorkoutSchedule.getInstance();
            WorkoutPlan fortoday=wos.getAt(date);

            dayValue = date.getDayString();
            monthValue = date.getMonthString();


            if(fortoday!=null || data.getCount() != 0) {
                DayWorkoutList dwl=new DayWorkoutList();
                dwl.setPlan(fortoday);
                dwl.newDate=date;

                Bundle bundle = new Bundle();
                bundle.putString("WorkoutDay", dayValue);
                bundle.putString("WorkoutMonth", monthValue);
                dwl.setArguments(bundle);

                fragmentTransaction.replace(R.id.frameLayout, dwl);
                fragmentTransaction.commit();
            }else{
                DayWorkoutEmpty dwe=new DayWorkoutEmpty();
                dwe.date=date;

                editor.putString("wDay", date.getDayString());
                editor.putString("wMonth", date.getMonthString());

                fragmentTransaction.replace(R.id.frameLayout, dwe);
                fragmentTransaction.commit();
            }
            lastday=date;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        WorkoutSchedule wos=WorkoutSchedule.getInstance();
        WorkoutPlan fortoday=wos.getAt(lastday);
        data.moveToFirst();

        if(fortoday!=null || data.getCount() != 0) {
            DayWorkoutList dwl=new DayWorkoutList();
            dwl.setPlan(fortoday);

            Bundle bundle = new Bundle();
            bundle.putString("WorkoutDay", lastday.getDayString());
            bundle.putString("WorkoutMonth", lastday.getMonthString());
            dwl.setArguments(bundle);
            getFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.frameLayout, dwl);
            fragmentTransaction.commit();
        }else{
            DayWorkoutEmpty dwe=new DayWorkoutEmpty();
            dwe.date=lastday;
            fragmentTransaction.replace(R.id.frameLayout, dwe);
            fragmentTransaction.commit();
        }
        for(DateData d:wos.dates)
            calender.markDate(d.setMarkStyle(planned));
    }

    private class MonthListener extends OnMonthChangeListener {
        @Override
        public void onMonthChange(int year, int month) {
            monthtitle.setText(WorkoutSchedule.getMonthForInt(month)+" : "+year);
        }
    }






}
