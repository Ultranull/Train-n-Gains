package banana.pudding.pie.train_n_gains;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.listeners.OnMonthChangeListener;
import sun.bob.mcalendarview.vo.DateData;
import sun.bob.mcalendarview.vo.MarkedDates;

import static banana.pudding.pie.train_n_gains.DatabaseHelper.TABLE_NAME;

public class MainActivity extends AppCompatActivity {

    private MCalendarView calender;
    private TextView datetitle,monthtitle;
    private Button totoday;
    private MarkStyle selected, planned;
    private DateData lastday;
    private DatabaseHelper myDB;
    private Cursor data;


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

        calender.setOnMonthChangeListener(new MonthListener());
        calender.setOnDateClickListener(new DayListener());
        //calender.hasTitle(false);


        totoday=findViewById(R.id.move_to_today);
        totoday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calender.travelTo(WorkoutSchedule.today());
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
            fragmentTransaction.add(R.id.frameLayout, dwl);
            fragmentTransaction.commit();
        }else{
            fragmentTransaction.add(R.id.frameLayout, new DayWorkoutEmpty());
            fragmentTransaction.commit();
        }

        lastday=today;


        for(DateData d:wos.dates)
            calender.markDate(d.setMarkStyle(planned));
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
            if(fortoday!=null) {
                DayWorkoutList dwl=new DayWorkoutList();
                dwl.setPlan(fortoday);
                fragmentTransaction.replace(R.id.frameLayout, dwl);
                fragmentTransaction.commit();
            }else{
                DayWorkoutEmpty dwe=new DayWorkoutEmpty();
                dwe.date=date;
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


        if(fortoday!=null || data.getCount() != 0) {
            DayWorkoutList dwl=new DayWorkoutList();
            dwl.setPlan(fortoday);
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


    private void test(){
        Date current=Calendar.getInstance().getTime();
        final MCalendarView cv=findViewById(R.id.CV);

        cv.travelTo(new DateData(current.getYear()+1900,current.getMonth()+1,current.getDay()));

        cv.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {
                if(!cv.getMarkedDates().remove(date))
                    cv.markDate(date);
            }
        });

        MarkStyle wo=new MarkStyle(MarkStyle.LEFTSIDEBAR,Color.GREEN);

        ArrayList<DateData> dates=new ArrayList<>();
        dates.add(new DateData(2019,4,26).setMarkStyle(wo));
        dates.add(new DateData(2019,4,27).setMarkStyle(wo));

        for(int i=0;i<dates.size();i++) {
            cv.markDate(dates.get(i));
        }
    }



}
