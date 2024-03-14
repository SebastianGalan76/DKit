package pl.dream.dkit;

public class Utils {
    public static String convertTime(int timeInt){
        String time = "";

        int hours = getHours(timeInt);
        int minutes = getMinutes(timeInt);
        int seconds = getSeconds(timeInt);

        if(hours<=0){
            time+="00:";
        }
        else if(hours<10){
            time+="0"+hours+":";
        }
        else{
            time+=hours+":";
        }

        if(minutes<=0){
            time+="00:";
        }
        else if(minutes<10){
            time+="0"+minutes+":";
        }
        else{
            time+=minutes+":";
        }

        if(seconds<=0){
            time+="00";
        }
        else if(seconds<10){
            time+="0"+seconds;
        }
        else{
            time+=seconds;
        }

        return time;
    }

    private static int getHours(int timeInt){
        return timeInt/3600;
    }
    private static int getMinutes(int timeInt){
        timeInt -= getHours(timeInt)*3600;

        return timeInt/60;
    }
    private static int getSeconds(int timeInt){
        timeInt -= getHours(timeInt)*3600;
        timeInt -= getMinutes(timeInt)*60;

        return timeInt;
    }
}
