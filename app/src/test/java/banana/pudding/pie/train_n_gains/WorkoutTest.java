package banana.pudding.pie.train_n_gains;

import android.graphics.Color;
import android.widget.CalendarView;

import org.junit.Test;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.time.Month;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.vo.DateData;

import static org.junit.Assert.*;

public class WorkoutTest {
    @Test
    public void workoutActionTest() {
        WorkoutAction wa0=new WorkoutAction();
        assertEquals("None",wa0.getName());
        assertEquals("None",wa0.getDescription());
        assertEquals("None",wa0.getInstructions());
        assertEquals(WorkoutAction.TYPE.CORE,wa0.getType());
    }

    @Test
    public void workoutPlanTest(){
//        WorkoutPlan plan0=new WorkoutPlan();
//        assertEquals("None",plan0.getName());
//        assertEquals("None",plan0.getDescription());
//
//        plan0.addWorkout(0);
//        plan0.addWorkout(1);
//        plan0.addWorkout(2);
//
//        ArrayList<WorkoutAction> wos=plan0.getWorkouts();
//        for(WorkoutAction i:wos)
//            System.out.println("workout: "+i);
//        assertEquals(3,wos.size());
//        assertEquals(new Integer(0),wos.get(0));
//        assertEquals(new Integer(1),wos.get(1));
//        assertEquals(new Integer(2),wos.get(2));
//        System.out.println();
//
//        plan0.moveUp(1);
//        wos=plan0.getWorkouts();
//        for(WorkoutAction i:wos)
//            System.out.println("workout: "+i);
//        assertEquals(3,wos.size());
//        assertEquals(new Integer(0),wos.get(1));
//        assertEquals(new Integer(1),wos.get(0));
//        assertEquals(new Integer(2),wos.get(2));
//        System.out.println();
//
//        plan0.moveDown(0);
//        wos=plan0.getWorkouts();
//        for(WorkoutAction i:wos)
//            System.out.println("workout: "+i);
//        assertEquals(3,wos.size());
//        assertEquals(new Integer(0),wos.get(0));
//        assertEquals(new Integer(1),wos.get(1));
//        assertEquals(new Integer(2),wos.get(2));
//        System.out.println();

    }

    @Test
    public void dateTest(){
        Calendar cal=Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        System.out.println(getMonthForInt(month-1)+"/"+day+"/"+year);

        MarkStyle mk0=new MarkStyle(MarkStyle.BACKGROUND, Color.CYAN);
        MarkStyle mk1=new MarkStyle(MarkStyle.BACKGROUND, Color.CYAN);

        System.out.println(mk0.equals(mk1));
    }
    String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }
}