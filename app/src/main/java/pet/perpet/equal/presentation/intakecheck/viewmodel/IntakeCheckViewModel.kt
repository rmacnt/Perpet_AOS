package pet.perpet.equal.presentation.intakecheck.viewmodel

import IntakeData
import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pet.perpet.data.nonnull
import pet.perpet.domain.UserStore
import pet.perpet.domain.usecase.intake.CareListUseCase
import pet.perpet.domain.usecase.pet.PetListUseCase
import pet.perpet.equal.R
import pet.perpet.equal.presentation.checkPermissionCall
import pet.perpet.equal.presentation.getColor
import pet.perpet.equal.presentation.intakecheck.IntakeStore
import pet.perpet.equal.presentation.intakecheck.differ.IntakeCareListDiffer
import pet.perpet.equal.presentation.intakecheck.model.IntakeAlarm
import pet.perpet.equal.support.alarm.AlarmReceiver
import pet.perpet.equal.support.logger.AppLogger
import pet.perpet.equal.support.logger.d
import pet.perpet.equal.support.navigation.showAlarm
import pet.perpet.equal.support.navigation.showPetList
import pet.perpet.equal.support.navigation.showSubscribeList
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


class IntakeCheckViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================

    val result: Boolean?
        get() = petHealth.value.nonnull() && petSubscribe.value.nonnull()

    val btnComment: String?
        get() = if (petHealth.value.nonnull().not()) {
            getString(R.string.intake_check_comment2)
        } else if (petSubscribe.value.nonnull().not()) {
            getString(R.string.intake_check_comment20)
        } else {
            ""
        }

    val btnMessage: String?
        get() = if (petHealth.value.nonnull().not()) {
            getString(R.string.intake_check_comment3)
        } else if (petSubscribe.value.nonnull().not()) {
            getString(R.string.intake_check_comment4)
        } else {
            ""
        }

    val alarm: String?
        get() = when (alarmValue.value?.type) {
            0 -> {
                "매일 오전 ${alarmValue.value?.time}시"
            }

            1 -> {
                "매일 오후 ${alarmValue.value?.time}시"
            }

            else -> {
                "섭취 알림 없음"
            }
        }

    val selectType: Int?
        get() = selectDayType.value

    val alarmMessage: String?
        get() = alarmMessageValue.value

    val selectFontResult: Boolean?
        get() = selectDayType.value == 0

    val selectDayColor: Int?
        get() = selectDayColorValue.value

    val empty: String?
        get() = when (selectType) {
            0 -> getString(R.string.intake_check_comment23)
            1 -> getString(R.string.intake_check_comment24)
            2 -> getString(R.string.intake_check_comment25)
            else -> getString(R.string.intake_check_comment23)
        }


    val calendarToday: String?
        get() = calendarTodayTitle.value

    val calendarTodayCount: String?
        get() = calendarTodayCountTitle.value

    val petHealth: MutableLiveData<Boolean> = MutableLiveData(false)
    val petSubscribe: MutableLiveData<Boolean> = MutableLiveData(false)
    val calendarTodayTitle: MutableLiveData<String> = MutableLiveData("")
    val alarmMessageValue: MutableLiveData<String> = MutableLiveData("")
    val calendarTodayCountTitle: MutableLiveData<String> = MutableLiveData("")
    val selectDayType: MutableLiveData<Int> = MutableLiveData(0)
    val selectDayColorValue: MutableLiveData<Int> = MutableLiveData(0)
    val alarmValue: MutableLiveData<IntakeAlarm> = MutableLiveData(null)

    var dataSetting: ArrayList<IntakeData> = ArrayList()
    var intakeSetting: ArrayList<Calendar> = ArrayList()

    val itemListDiffer by lazy {
        IntakeCareListDiffer()
    }

    val typeMediator by lazy {
        object : BaseRecyclerView.LayoutMediator() {
            override fun onCreateAdapter(): RecyclerView.Adapter<out RecyclerView.ViewHolder> {
                return itemListDiffer.adapter
            }

            override fun onCreateLayoutManager(): RecyclerView.LayoutManager {
                return LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var alarm = false
        if(IntakeStore.intakeAlarm?.size.nonnull() > 0) {
            IntakeStore.intakeAlarm?.forEach {
                if(it.userId == UserStore.userInfo?.id) {
                    alarm = true
                    alarmValue.value = it
                    return@forEach
                }
            }

            if(alarm.not())
                alarmValue.value = IntakeAlarm(-1,-1, UserStore.userInfo?.id)

        } else {
            alarmValue.value = IntakeAlarm(-1,-1, UserStore.userInfo?.id)
        }
    }

    //======================================================================
    // Public Methods
    //======================================================================

    fun onHomeClick(view: View) {
        activity?.let {
            it.finish()
        }
    }


    fun onAlramListClick(view: View) {
        checkPermission {
            if (it) {
                activity?.let {
                    it.showAlarm(alarmValue.value) {
                        IntakeStore.sync(it)
                        alarmValue.value = it
                        if (it.time != -1) {

                            val manager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                            val hasPermission: Boolean =
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                    manager.canScheduleExactAlarms()
                                } else {
                                    true
                                }

                            if (hasPermission) {
                                val intentCancel = Intent(context, AlarmReceiver::class.java)
                                val pendingIntentCancel = PendingIntent.getBroadcast(context, 7727, intentCancel, IntakeStore.ALARM_FLAG)

                                if (pendingIntentCancel != null) {
                                    manager.cancel(pendingIntentCancel)
                                }

                                val intent = Intent(context, AlarmReceiver::class.java)
                                intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)
                                val pendingIntent = PendingIntent.getBroadcast(context, 7727, intent, IntakeStore.ALARM_FLAG)

                                val calendar = Calendar.getInstance().apply {

                                    timeInMillis = System.currentTimeMillis()

                                    set(Calendar.HOUR_OF_DAY ,if (it.type == 1) it.time.nonnull() + 12 else it.time.nonnull())
                                    set(Calendar.MINUTE, 0)
                                    set(Calendar.SECOND, 0)
                                    set(Calendar.MILLISECOND, 0)

                                    if (before(Calendar.getInstance())) {
                                        add(Calendar.DATE, 1)
                                    }
                                }

                                manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
                                alarmMessageValue.value = getString(R.string.intake_check_comment30)
                            } else {
                                setAlarmCheck()
                            }
                        } else {
                            val intent = Intent(context, AlarmReceiver::class.java)
                            val pendingIntent = PendingIntent.getBroadcast(context, 7727, intent, IntakeStore.ALARM_FLAG)

                            val am = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                            if (pendingIntent != null) {
                                am.cancel(pendingIntent)
                            }

                            alarmMessageValue.value = getString(R.string.intake_check_comment31)
                        }

                        executeViewBindingNew()
                    }
                }
            } else {
                setAlarmCheck()
            }
        }

    }

    fun onSubmitClick(view: View) {
        if (petHealth.value.nonnull().not()) {
            activity?.showPetList()
        } else if (petSubscribe.value.nonnull().not()) {
            activity?.showSubscribeList()
        }
    }

    fun getPetList(result: () -> Unit) {
        PetListUseCase().parameter2 {
        }.success {
            it?.let {
                it.content?.let { data ->
                    data.forEach {
                        if (it.latest_medical_id != null) {
                            petHealth.value = true
                        }
                        if (it.latest_order_id != null) {
                            petSubscribe.value = true
                            return@forEach
                        }
                    }
                }
                result()
                executeViewBinding()

            }
        }.fail {
        }.execute()
    }

    fun getCareList(
        startDate: String,
        endDate: String,
        today: String,
        call: (ArrayList<IntakeData>) -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            CareListUseCase().parameter2 {
                this.startDate = startDate
                this.endDate = endDate
            }.success {
                it?.let {
                    dataSetting.clear()
                    dataSetting.addAll(it)
                    it.forEach {
                        val newDate = it.date?.split("-")

                        val currentDate = Calendar.getInstance()
                        currentDate.set(Calendar.YEAR, newDate?.get(0).nonnull().toInt())
                        currentDate.set(Calendar.MONTH, newDate?.get(1).nonnull().toInt())
                        currentDate.set(Calendar.DATE, newDate?.get(2).nonnull().toInt())

                        intakeSetting.clear()
                        intakeSetting.add(currentDate)

                        if (today == it.date) {
                            it.cares?.forEach {
                                it.today = true
                            }
                            itemListDiffer.clearList()
                            itemListDiffer.allList(it.cares)

                            var checkSize = 0
                            it.cares?.forEach {
                                if (it.check.nonnull())
                                    checkSize++
                            }
                            if (checkSize == it.cares?.size) {
                                selectDayColorValue.value = getColor(R.color.intake_color)
                            } else {
                                selectDayColorValue.value = getColor(R.color.intake_not_color)
                            }
                            calendarTodayCountTitle.value = "$checkSize / ${it.cares?.size}"
                            calendarTodayTitle.value = getString(R.string.intake_check_comment21)
                            return@forEach
                        }
                    }
                }
                call(dataSetting)
                executeViewBinding()

            }.fail {
            }.execute()
        }


    }

    @SuppressLint("SimpleDateFormat")
    fun selectDayItem(selectDay: String, year: Int, month: Int, day: Int) {

        val dateFormat = SimpleDateFormat("yyyy-MM-dd")

        val now = System.currentTimeMillis()
        val date = Date(now)
        val format = SimpleDateFormat("yyyy-MM-dd")
        val formatYear = SimpleDateFormat("yyyy")
        val getTime: String = format.format(date)
        val getYear: String = formatYear.format(date)

        val startDate = dateFormat.parse(selectDay)?.time
        val endDate = dateFormat.parse(getTime)?.time

        val cmp = endDate?.compareTo(startDate.nonnull())
        when {
            cmp.nonnull() > 0 -> {
                dataSetting.forEach {
                    if (selectDay == it.date) {

                        it.cares?.forEach {
                            it.today = false
                        }

                        itemListDiffer.clearList()
                        itemListDiffer.allList(it.cares)

                        var checkSize = 0
                        it.cares?.forEach {
                            if (it.check.nonnull())
                                checkSize++
                        }
                        calendarTodayCountTitle.value = "$checkSize / ${it.cares?.size}"
                        if (getYear == year.toString()) {
                            calendarTodayTitle.value = "${month}월 ${day}일 복용기록"
                        } else {
                            calendarTodayTitle.value = "$${year}년 ${month}월 ${day}일 복용기록"
                        }
                        selectDayColorValue.value = getColor(R.color.text_hint_color)
                        selectDayType.value = 1
                        executeViewBinding()
                        itemListDiffer.adapter.notifyDataSetChanged()
                        return@forEach
                    }
                }
            }

            cmp.nonnull() < 0 -> {
                itemListDiffer.clearList()
                selectDayColorValue.value = getColor(R.color.text_hint_color)
                calendarTodayTitle.value = getString(R.string.intake_check_comment22)
                calendarTodayCountTitle.value = ""
                selectDayType.value = 2
                executeViewBinding()
            }

            cmp.nonnull() == 0 -> {
                dataSetting.forEach {
                    if (selectDay == it.date) {
                        it.cares?.forEach {
                            it.today = true
                        }

                        itemListDiffer.clearList()
                        itemListDiffer.allList(it.cares)

                        var checkSize = 0
                        it.cares?.forEach {
                            if (it.check.nonnull())
                                checkSize++
                        }
                        if (checkSize == it.cares?.size) {
                            selectDayColorValue.value = getColor(R.color.intake_color)
                        } else {
                            selectDayColorValue.value = getColor(R.color.intake_not_color)
                        }

                        calendarTodayCountTitle.value = "$checkSize / ${it.cares?.size}"
                        calendarTodayTitle.value = getString(R.string.intake_check_comment21)
                        selectDayType.value = 0
                        executeViewBinding()
                        itemListDiffer.adapter.notifyDataSetChanged()
                        return@forEach
                    }
                }
            }

            else -> {
                print("Both dates are equal")
            }

        }
    }

    fun checkPermission(callback: (success: Boolean) -> Unit) {

        var array: ArrayList<String> = arrayListOf()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            array = arrayListOf(
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.SCHEDULE_EXACT_ALARM
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            array = arrayListOf(
                Manifest.permission.SCHEDULE_EXACT_ALARM
            )
        }

        activity.checkPermissionCall(
            array
        ) { grants: BooleanArray?, showRequestPermissions: BooleanArray? ->
            val permission =
                grants?.all { it }.nonnull()

            AppLogger.Tag.DOWNLOAD.d(
                "checkPermission > permission : ${permission}"
            )
            callback(permission)
        }
    }

    private fun setAlarmCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, activity?.packageName)
                activity?.startActivity(intent)
            }else {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:${activity?.packageName}")
                activity?.startActivity(intent)
            }

        } else {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:${activity?.packageName}")
            activity?.startActivity(intent)
        }
    }
}