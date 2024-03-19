package pet.perpet.framework.widget.calendarv2.data;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by shrikanthravi on 06/03/18.
 */

public class Day implements Parcelable{

    private int mYear;
    private int mMonth;
    private int mDay;

    public Day(int year, int month, int day){
        this.mYear = year;
        this.mMonth = month;
        this.mDay = day;
    }

    public int getMonth(){
        return mMonth;
    }

    public int getYear(){
        return mYear;
    }

    public int getDay(){
        return mDay;
    }



    public Day(Parcel in){
        int[] data = new int[3];
        in.readIntArray(data);
        this.mYear = data[0];
        this.mMonth = data[1];
        this.mYear = data[2];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(new int[] {this.mYear,
                this.mMonth,
                this.mDay});
    }
    public static final Creator CREATOR = new Creator() {
        public Day createFromParcel(Parcel in) {
            return new Day(in);
        }

        public Day[] newArray(int size) {
            return new Day[size];
        }
    };

    public long toUnixTime(){
        Date date = new Date(this.mYear, this.mMonth, this.mDay);
        return date.getTime();
    }

    @SuppressLint("DefaultLocale")
    public String toSelectDate(){
        return this.mYear+"-"+String.format("%02d",(this.mMonth+1)) +"-"+ String.format("%02d",(this.mDay));
    }

    public int getDiff(){
        Calendar todayCal = Calendar.getInstance();
        Day day = new Day(todayCal.get(Calendar.YEAR), todayCal.get(Calendar.MONTH),todayCal.get(Calendar.DAY_OF_MONTH));
        return (int)( (this.toUnixTime() - day.toUnixTime())
                / (1000 * 60 * 60 * 24) );
    }

    public int getMinDiff(){
        Day day = new Day(2020, 2,25);
        return (int)( (this.toUnixTime() - day.toUnixTime()) / (1000 * 60 * 60 * 24) );
    }
}
