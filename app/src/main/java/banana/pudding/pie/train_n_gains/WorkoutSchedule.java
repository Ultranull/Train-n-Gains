package banana.pudding.pie.train_n_gains;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

import sun.bob.mcalendarview.vo.DateData;

 class WorkoutSchedule {

     private static WorkoutSchedule workoutSchedule;
     static WorkoutSchedule getInstance(){
        if(workoutSchedule==null)
            workoutSchedule=new WorkoutSchedule();
        return workoutSchedule;
    }

     static DateData today(){
        Calendar cal=Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return new DateData(year,month,day);
    }

     static String getMonthForInt(int num) {
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 1 && num <= 12 ) {
            return months[num-1];
        }
        return null;
    }




     ArrayList<DateData> dates;
     ArrayList<WorkoutPlan> plans;
     ActionManager actionManager;

    private WorkoutSchedule(){
        dates=new ArrayList<>();
        plans=new ArrayList<>();
        actionManager=new ActionManager();
    }



    private DateData fromString(String date){
        int year,month,day;

        String[] data=date.split("/");
        year=Integer.parseInt(data[0]);
        month=Integer.parseInt(data[1]);
        day=Integer.parseInt(data[2]);

        return new DateData(year,month,day);
    }


     void addWorkoutPlan(DateData dd,WorkoutPlan wop){
        dates.add(dd);
        plans.add(wop);
    }

     WorkoutPlan getAt(DateData dd){
        for(int i=0;i<dates.size();i++)
            if(dates.get(i).equals(dd))
                return plans.get(i);
        return null;
    }

}
