package pet.perpet.equal.presentation.intakecheck.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pet.perpet.data.nonnull
import pet.perpet.domain.model.intake.IntakeCare
import pet.perpet.domain.usecase.intake.CareUpdateUseCase
import pet.perpet.equal.MyApplication
import pet.perpet.equal.R
import pet.perpet.equal.databinding.IntakeCheckFragmentBinding
import pet.perpet.equal.presentation.goHome
import pet.perpet.equal.presentation.intakecheck.IntakeStore
import pet.perpet.equal.presentation.intakecheck.model.IntakeAlarm
import pet.perpet.equal.presentation.intakecheck.viewholder.ItemCareViewHolder
import pet.perpet.equal.presentation.intakecheck.viewmodel.IntakeCheckViewModel
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.equal.presentation.observeBindingNotifyNew
import pet.perpet.framework.fragment.Fragment
import pet.perpet.framework.widget.calendarv2.CollapsibleCalendar
import pet.perpet.framework.widget.calendarv2.data.Day
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.OnViewHolderEventListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar

class IntakeCheckFragment : Fragment<IntakeCheckViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: IntakeCheckFragmentBinding

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel.observeBindingNotify {
            binding.model = viewmodel
        }

        viewmodel.checkPermission {
            if(it.not()) {
                IntakeStore.sync(IntakeAlarm( -1, -1))
                binding.model = viewmodel
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.intake_check_fragment, container, false)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = IntakeCheckFragmentBinding.bind(view)
        binding.model = viewmodel

        binding.calendarview.setExpandIconVisible(false)
        binding.calendarview.expanded = false

        val today = GregorianCalendar()
        today.add(Calendar.DATE, 3)
        binding.calendarview.selectedDay = Day(
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        )
        binding.calendarview.setCalendarListener(
            object : CollapsibleCalendar.CalendarListener {

                override fun onDayChanged() {}
                override fun onClickListener() {}
                override fun onDaySelect() {
                    binding.calendarview.todayItemBackgroundDrawable = null


                    viewmodel.selectDayItem(
                        binding.calendarview.selectedDay?.toSelectDate().nonnull(),
                        binding.calendarview.year,
                        binding.calendarview.month + 1,
                        binding.calendarview.selectedDay?.day.nonnull()
                    )

                }

                override fun onItemClick(v: View) {}
                override fun onDataUpdate() {}

                override fun onMonthChange() {
                    binding.calendarview.clearEvent()
                    settingCalender()

                }

                override fun onWeekChange(position: Int) {
                }
            })

        viewmodel.itemListDiffer.adapter.setHolderEventListener(object :
            OnViewHolderEventListener {
            @SuppressLint("SimpleDateFormat")
            override fun onRecyclerViewHolderEvent(
                holder: BaseRecyclerViewHolder<*>,
                id: Int,
                bundle: Bundle,
            ) {

                if (holder is ItemCareViewHolder) {
                    val data = holder.item
                    if (data is IntakeCare) {

                        if (viewmodel.selectFontResult.nonnull()) {
                            val now = System.currentTimeMillis()
                            val date = Date(now)
                            val format = SimpleDateFormat("yyyy-MM-dd")
                            val getTime: String = format.format(date)

                            viewmodel.viewModelScope.launch {
                                CareUpdateUseCase().parameter2 {
                                    this.id = data.care_id
                                    this.petId = data.pet_id
                                    this.orderId = data.order_id
                                    this.result = "O"
                                    this.date = getTime
                                }.success {
                                    settingCalender()
                                }.fail {
                                }.execute()
                            }
                        } else {
                            Toast.makeText(
                                activity,
                                getString(R.string.intake_check_comment25),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                }
            }
        })

        viewmodel.observeBindingNotifyNew {
            binding.tvSuccess.visibility = View.VISIBLE
            binding.model = viewmodel
            val hander = Handler()
            hander.postDelayed({
                binding.tvSuccess.visibility = View.GONE
                binding.model = viewmodel
            }, 3000)
        }

        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        viewmodel.getPetList {
            settingCalender()
        }

    }

    @SuppressLint("SimpleDateFormat")
    fun settingCalender() {
        val dayOfMonth =
            binding.calendarview.mxMonth
        val firstToday = "${binding.calendarview.year}-${
            String.format(
                "%02d",
                binding.calendarview.month + 1
            )
        }-01"

        val lastDay = "${binding.calendarview.year}-${
            String.format(
                "%02d",
                binding.calendarview.month + 1
            )
        }-${String.format("%02d", dayOfMonth)}"

        val now = System.currentTimeMillis()
        val date = Date(now)
        val format = SimpleDateFormat("yyyy-MM-dd")
        val dayFormat = SimpleDateFormat("dd")
        val monthFormat = SimpleDateFormat("MM")
        val yearFormat = SimpleDateFormat("yyyy")
        val getTime: String = format.format(date)
        val getDay: String = dayFormat.format(date)
        val getMonth: String = monthFormat.format(date)
        val getYear: String = yearFormat.format(date)

        if(viewmodel.result.nonnull()) {
            viewmodel.getCareList(firstToday, lastDay, getTime) {

                it.forEach {
                    if (it.cares?.size.nonnull() > 0) {
                        var allCheck = 1
                        it.cares?.forEach { item ->
                            if (item.check.nonnull().not()) {
                                allCheck = 0
                                return@forEach
                            }
                        }

                        if (getYear.toInt() >= binding.calendarview.year && ((binding.calendarview.month + 1) == getMonth.toInt())) {
                            if (it.date?.split("-")?.get(2).nonnull().toInt() <= getDay.toInt()) {
                                binding.calendarview.addEventTag(
                                    it.date?.split("-")?.get(0).nonnull().toInt(),
                                    it.date?.split("-")?.get(1).nonnull().toInt() - 1,
                                    it.date?.split("-")?.get(2).nonnull().toInt(), allCheck
                                )
                            }
                        } else if (getYear.toInt() >= binding.calendarview.year && ((binding.calendarview.month + 1) < getMonth.toInt())) {
                            binding.calendarview.addEventTag(
                                it.date?.split("-")?.get(0).nonnull().toInt(),
                                it.date?.split("-")?.get(1).nonnull().toInt() - 1,
                                it.date?.split("-")?.get(2).nonnull().toInt(), allCheck
                            )
                        }
                    }
                }

                binding.calendarview.setReload()

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(MyApplication.mainOpen.nonnull().not()) {
            context?.goHome()
        }
    }

}