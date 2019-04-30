package banana.pudding.pie.train_n_gains;

import org.junit.Test;

import java.text.DateFormatSymbols;
import java.util.Calendar;

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
    public void dateTest(){
        Calendar cal=Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        System.out.println(getMonthForInt(month-1)+"/"+day+"/"+year);

        DateData dd=new DateData(2019,2,12);
        System.out.println(dd.toString());
    }
    private String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }
}