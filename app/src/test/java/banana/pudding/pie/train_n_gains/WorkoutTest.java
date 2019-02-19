package banana.pudding.pie.train_n_gains;

import org.junit.Test;

import java.util.ArrayList;

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
        WorkoutPlan plan0=new WorkoutPlan();
        assertEquals("None",plan0.getName());
        assertEquals("None",plan0.getDescription());

        plan0.addWorkout(0);
        plan0.addWorkout(1);
        plan0.addWorkout(2);

        ArrayList<Integer> wos=plan0.getWorkouts();
        for(Integer i:wos)
            System.out.println("workout: "+i);
        assertEquals(3,wos.size());
        assertEquals(new Integer(0),wos.get(0));
        assertEquals(new Integer(1),wos.get(1));
        assertEquals(new Integer(2),wos.get(2));
        System.out.println();

        plan0.moveUp(1);
        wos=plan0.getWorkouts();
        for(Integer i:wos)
            System.out.println("workout: "+i);
        assertEquals(3,wos.size());
        assertEquals(new Integer(0),wos.get(1));
        assertEquals(new Integer(1),wos.get(0));
        assertEquals(new Integer(2),wos.get(2));
        System.out.println();

        plan0.moveDown(0);
        wos=plan0.getWorkouts();
        for(Integer i:wos)
            System.out.println("workout: "+i);
        assertEquals(3,wos.size());
        assertEquals(new Integer(0),wos.get(0));
        assertEquals(new Integer(1),wos.get(1));
        assertEquals(new Integer(2),wos.get(2));
        System.out.println();

    }
}