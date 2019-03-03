package banana.pudding.pie.train_n_gains;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.util.HashMap;

import sun.bob.mcalendarview.vo.DateData;

public class WorkoutSchedule {

    public static WorkoutSchedule workoutSchedule;
    public static WorkoutSchedule getInstance(){
        if(workoutSchedule==null)
            workoutSchedule=new WorkoutSchedule();
        return workoutSchedule;
    }

    public static DateData today(){
        Calendar cal=Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return new DateData(year,month,day);
    }

    public static String getMonthForInt(int num) {
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 1 && num <= 12 ) {
            return months[num-1];
        }
        return null;
    }


    public ArrayList<DateData> dates;
    public ArrayList<WorkoutPlan> plans;
public ActionManager actionManager;

    public WorkoutSchedule(){
        dates=new ArrayList<>();
        plans=new ArrayList<>();
        actionManager=new ActionManager();
    }

    public void addWorkoutPlan(DateData dd,WorkoutPlan wop){
        dates.add(dd);
        plans.add(wop);
    }

    public WorkoutPlan getAt(DateData dd){
        for(int i=0;i<dates.size();i++)
            if(dates.get(i).equals(dd))
                return plans.get(i);
        return null;
    }

}
