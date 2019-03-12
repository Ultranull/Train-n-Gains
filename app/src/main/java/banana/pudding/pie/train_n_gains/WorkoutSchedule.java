package banana.pudding.pie.train_n_gains;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
    public JSONArray toJSON(){
        JSONArray jsonArray=new JSONArray();
        try{
            JSONObject obj=new JSONObject();
            for (int i=0;i<dates.size();i++){
                DateData dd=dates.get(i);
                obj.put("date",dd.getYear()+"/"+dd.getMonth()+"/"+dd.getDay());
                obj.put("plan",plans.get(i).toJSON());
                jsonArray.put(obj);
            }
        }catch(Exception e){e.printStackTrace();}
        return jsonArray;
    }
    public void loadActions(Context context){
        File directory = context.getFilesDir();
        File file = new File(directory, "workoutActions.json");
        if (file.exists()) {
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                br.close();
                actionManager.load(text.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else System.out.println("no file");
    }

    public void loadSchedule(Context context){
        File directory = context.getFilesDir();
        File file = new File(directory, "userPlan.json");
        if (file.exists()) {
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                br.close();
                loadJSON(text.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else System.out.println("no file");

    }
    private DateData fromString(String date){
        int year,month,day;

        String[] data=date.split("/");
        year=Integer.parseInt(data[0]);
        month=Integer.parseInt(data[1]);
        day=Integer.parseInt(data[2]);

        return new DateData(year,month,day);
    }
    public void loadJSON(String json){
        try{
            JSONArray jsonArray=new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj=jsonArray.getJSONObject(i);
                dates.add(fromString(obj.getString("date")));
                plans.add(new WorkoutPlan(obj.getJSONArray("plan")));
            }
        }catch(Exception e){e.printStackTrace();}
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
