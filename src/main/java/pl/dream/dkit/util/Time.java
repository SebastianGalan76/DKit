package pl.dream.dkit.util;

public class Time {
    public static String convertTime(long timeInt){
        String time = "";

        long hours = getHours(timeInt);
        long minutes = getMinutes(timeInt);
        long seconds = getSeconds(timeInt);

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

    private static long getHours(long timeInt){
        return timeInt/3600;
    }
    private static long getMinutes(long timeInt){
        timeInt -= getHours(timeInt)*3600;

        return timeInt/60;
    }
    private static long getSeconds(long timeInt){
        timeInt -= getHours(timeInt)*3600;
        timeInt -= getMinutes(timeInt)*60;

        return timeInt;
    }
}
