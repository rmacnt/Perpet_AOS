package pet.perpet.framework.widget.calendar.listeners;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.annimon.stream.Stream;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import pet.perpet.framework.R;
import pet.perpet.framework.widget.calendar.CalendarUtils;
import pet.perpet.framework.widget.calendar.CalendarView;
import pet.perpet.framework.widget.calendar.EventDay;
import pet.perpet.framework.widget.calendar.adapters.CalendarPageAdapter;
import pet.perpet.framework.widget.calendar.utils.CalendarProperties;
import pet.perpet.framework.widget.calendar.utils.DateUtils;
import pet.perpet.framework.widget.calendar.utils.DayColorsUtils;
import pet.perpet.framework.widget.calendar.utils.SelectedDay;

/**
 * This class is responsible for handle click events
 * <p>
 * Created by Mateusz Kornakiewicz on 24.05.2017.
 */

public class DayRowClickListener implements AdapterView.OnItemClickListener {

    private CalendarPageAdapter mCalendarPageAdapter;

    private CalendarProperties mCalendarProperties;

    private int mPageMonth;

    public DayRowClickListener(CalendarPageAdapter calendarPageAdapter, CalendarProperties calendarProperties, int pageMonth) {
        mCalendarPageAdapter = calendarPageAdapter;
        mCalendarProperties = calendarProperties;
        mPageMonth = pageMonth < 0 ? 11 : pageMonth;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Calendar day = new GregorianCalendar();
        day.setTime((Date) adapterView.getItemAtPosition(position));

        if (mCalendarProperties.getOnDayClickListener() != null) {
            onClick(day);
        }

        if (mCalendarProperties.isWeekDaySelectProcess() == true) {
            ArrayList<Calendar> list = makeWeekDays(day);
            if (mCalendarProperties.getOnWeekClickListener() != null) {
                mCalendarProperties.getOnWeekClickListener().onDayClick(list);
            }

            mCalendarPageAdapter.clearSelectedDays();
            for (Calendar calendar : list) {
                mCalendarPageAdapter.addSelectedDay(new SelectedDay(calendar));
                mCalendarPageAdapter.notifyDataSetChanged();
            }
            return;
        }

        switch (mCalendarProperties.getCalendarType()) {
            case CalendarView.ONE_DAY_PICKER:
                selectOneDay(view, day);
                break;

            case CalendarView.MANY_DAYS_PICKER:
                selectManyDays(view, day);
                break;

            case CalendarView.RANGE_PICKER:
                selectRange(view, day);
                break;

            case CalendarView.CLASSIC:
                mCalendarPageAdapter.setSelectedDay(new SelectedDay(view, day));
        }
    }

    private static Calendar intervalBeforeDate(Date current, int day) {
        int interval = day * (1000 * 60 * 60 * 24);
        /*SimpleDateFormat yymmdd = new SimpleDateFormat("yyyy MM dd");*/

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            long diff = current.getTime() + interval;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(diff));

            try {
                Log.d("DEBUG", "pre time : " + sdf.format(current));
            } catch (Exception e) {

            }

            try {
                Log.d("DEBUG", "next time :  " + sdf.format(calendar.getTime()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return calendar;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Calendar> makeWeekDays(Calendar currentDay) {
        ArrayList<Calendar> weeks = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat week = new SimpleDateFormat("E");

        Calendar first = Calendar.getInstance();
        first.setTime(currentDay.getTime());

        int currentIndex = currentDay.get(Calendar.DAY_OF_WEEK);
        for (int i = 1; i < currentIndex; i++) {
            Calendar cal = intervalBeforeDate(currentDay.getTime(), i * -1);
            weeks.add(cal);
        }

        for (int i = currentIndex; i <= 7; i++) {
            if (i == currentIndex) {
                weeks.add(currentDay);
                continue;
            }
            Calendar cal = intervalBeforeDate(currentDay.getTime(), i - currentIndex);
            weeks.add(cal);
        }

        Collections.sort(weeks, (o1, o2) -> {
            if (o1.getTime().getTime() < o2.getTime().getTime()) {
                return -1;
            }
            return 0;
        });

        for (Calendar cal : weeks) {
            Log.d("DEBUG", "DAY_OF_WEEK : " + cal.get(Calendar.DAY_OF_WEEK) + " date : " + sdf.format(cal.getTime()) + " 2 : " + week.format(cal.getTime()));
        }

        return weeks;
    }

    private void selectOneDay(View view, Calendar day) {
        SelectedDay previousSelectedDay = mCalendarPageAdapter.getSelectedDay();

        TextView dayLabel = view.findViewById(R.id.dayLabel);

        if (isAnotherDaySelected(previousSelectedDay, day)) {
            selectDay(dayLabel, day);
            reverseUnselectedColor(previousSelectedDay);
        }
    }

    private void selectManyDays(View view, Calendar day) {
        TextView dayLabel = (TextView) view.findViewById(R.id.dayLabel);

        if (isCurrentMonthDay(day) && isActiveDay(day)) {
            SelectedDay selectedDay = new SelectedDay(dayLabel, day);

            if (!mCalendarPageAdapter.getSelectedDays().contains(selectedDay)) {
                DayColorsUtils.setSelectedDayColors(dayLabel, mCalendarProperties);
            } else {
                reverseUnselectedColor(selectedDay);
            }

            mCalendarPageAdapter.addSelectedDay(selectedDay);
        }
    }

    private void selectRange(View view, Calendar day) {
        TextView dayLabel = (TextView) view.findViewById(R.id.dayLabel);

        if (!isCurrentMonthDay(day) || !isActiveDay(day)) {
            return;
        }

        List<SelectedDay> selectedDays = mCalendarPageAdapter.getSelectedDays();

        if (selectedDays.size() > 1) {
            clearAndSelectOne(dayLabel, day);
        }

        if (selectedDays.size() == 1) {
            selectOneAndRange(dayLabel, day);
        }

        if (selectedDays.isEmpty()) {
            selectDay(dayLabel, day);
        }
    }

    private void clearAndSelectOne(TextView dayLabel, Calendar day) {
        Stream.of(mCalendarPageAdapter.getSelectedDays()).forEach(this::reverseUnselectedColor);
        selectDay(dayLabel, day);
    }

    private void selectOneAndRange(TextView dayLabel, Calendar day) {
        SelectedDay previousSelectedDay = mCalendarPageAdapter.getSelectedDay();

        Stream.of(CalendarUtils.getDatesRange(previousSelectedDay.getCalendar(), day))
                .filter(calendar -> !mCalendarProperties.getDisabledDays().contains(calendar))
                .forEach(calendar -> mCalendarPageAdapter.addSelectedDay(new SelectedDay(calendar)));

        DayColorsUtils.setSelectedDayColors(dayLabel, mCalendarProperties);

        mCalendarPageAdapter.addSelectedDay(new SelectedDay(dayLabel, day));
        mCalendarPageAdapter.notifyDataSetChanged();
    }

    private void selectDay(TextView dayLabel, Calendar day) {
        DayColorsUtils.setSelectedDayColors(dayLabel, mCalendarProperties);
        mCalendarPageAdapter.setSelectedDay(new SelectedDay(dayLabel, day));
    }

    private void reverseUnselectedColor(SelectedDay selectedDay) {
        DayColorsUtils.setCurrentMonthDayColors(selectedDay.getCalendar(),
                DateUtils.getCalendar(), (TextView) selectedDay.getView(), mCalendarProperties);
    }

    private boolean isCurrentMonthDay(Calendar day) {
        return day.get(Calendar.MONTH) == mPageMonth && isBetweenMinAndMax(day);
    }

    private boolean isActiveDay(Calendar day) {
        return !mCalendarProperties.getDisabledDays().contains(day);
    }

    private boolean isBetweenMinAndMax(Calendar day) {
        return !((mCalendarProperties.getMinimumDate() != null && day.before(mCalendarProperties.getMinimumDate()))
                || (mCalendarProperties.getMaximumDate() != null && day.after(mCalendarProperties.getMaximumDate())));
    }

    private boolean isAnotherDaySelected(SelectedDay selectedDay, Calendar day) {
        return selectedDay != null && !day.equals(selectedDay.getCalendar())
                && isCurrentMonthDay(day) && isActiveDay(day);
    }

    private void onClick(Calendar day) {
        if (mCalendarProperties.getEventDays() == null) {
            createEmptyEventDay(day);
            return;
        }

        Stream.of(mCalendarProperties.getEventDays())
                .filter(eventDate -> eventDate.getCalendar().equals(day))
                .findFirst()
                .ifPresentOrElse(this::callOnClickListener, () -> createEmptyEventDay(day));
    }

    private void createEmptyEventDay(Calendar day) {
        EventDay eventDay = new EventDay(day);
        callOnClickListener(eventDay);
    }

    private void callOnClickListener(EventDay eventDay) {
        boolean enabledDay = mCalendarProperties.getDisabledDays().contains(eventDay.getCalendar())
                || !isBetweenMinAndMax(eventDay.getCalendar());

        eventDay.setEnabled(enabledDay);
        mCalendarProperties.getOnDayClickListener().onDayClick(eventDay);
    }
}
