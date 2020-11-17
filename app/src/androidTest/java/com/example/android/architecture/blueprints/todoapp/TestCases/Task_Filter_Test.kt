package com.example.android.architecture.blueprints.todoapp.TestCases

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.TestUtil.DrawableMatcher
import com.example.android.architecture.blueprints.todoapp.TestUtil.IdList
import com.example.android.architecture.blueprints.todoapp.TestUtil.testUtil
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import org.hamcrest.Matcher
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Task_Filter_Test {
    @get:Rule
    var activityRule = ActivityScenarioRule(TasksActivity::class.java)

    val idList = IdList()

    @Test
    fun All_Filter(){
        val util = testUtil()
        util.prepareTasksByCustomer(3)
        //打開menu filter並點選All
        onView(idList.menu_filter).perform(click())
        onView(withText(R.string.nav_all)).perform(click())
        Thread.sleep(1000)

        //Verify
        onView(idList.filtering_text).check(matches(withText(R.string.label_all)))
        onView(withText("Task01")).check(matches(isDisplayed()))
        onView(withText("Task02")).check(matches(isDisplayed()))
        onView(withText("Task03")).check(matches(isDisplayed()))
        util.deleteTasksByCustomer(3)
    }
    @Test
    fun All_Filter_With_Complete_Task(){
        val util = testUtil()
        util.prepareTasksByCustomer(3)
        //打勾一筆資料
        onView(idList.task_list).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0 , clickItemWithId(R.id.complete_checkbox)))

        //打開menu filter並點選All
        onView(idList.menu_filter).perform(click())
        onView(withText(R.string.nav_all)).perform(click())
        //Thread.sleep(1000)

        //Verify
        onView(idList.filtering_text).check(matches(withText(R.string.label_all)))
        onView(withText("Task01")).check(matches(isDisplayed()))
        onView(withText("Task02")).check(matches(isDisplayed()))
        onView(withText("Task03")).check(matches(isDisplayed()))

        //回歸
        onView(idList.task_list).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0 , clickItemWithId(R.id.complete_checkbox)))
        util.deleteTasksByCustomer(3)
    }

    @Test
    fun All_Filter_With_No_Task_Test(){
        //打開menu filter並點選All
        onView(idList.menu_filter).perform(click())
        onView(withText(R.string.nav_all)).perform(click())

        onView(idList.no_tasks_icon).check(matches(withDrawable(R.drawable.logo_no_fill)))
        onView(idList.no_tasks_text).check((matches(withText("You have no tasks!"))))

    }


    @Test
    fun Active_Filter_Test(){
        val util = testUtil()
        util.prepareTasksByCustomer(3)
        //打開menu並點選Active
        onView(idList.menu_filter).perform(click())
        onView(withText(R.string.nav_active)).perform(click())
        //verify
        onView(idList.filtering_text).check(matches(withText(R.string.label_active)))
        for(i in 1 until 4)
        {
            onView(withText("Task0$i")).check(matches(isDisplayed()))
        }

        util.deleteTasksByCustomer(3)
    }

    @Test
    fun Active_Filter_With_Complete_Task_Test(){
        val util = testUtil()
        util.prepareTasksByCustomer(3)
        //打勾task01
        onView(idList.task_list).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0 , clickItemWithId(R.id.complete_checkbox)))

        //打開menu並點選Active
        onView(idList.menu_filter).perform(click())
        onView(withText(R.string.nav_active)).perform(click())
        Thread.sleep(1000)

        //Verify
        onView(idList.filtering_text).check(matches(withText(R.string.label_active)))
        onView(withText("Task01")).check(ViewAssertions.doesNotExist())
        onView(withText("Task02")).check(matches(isDisplayed()))
        onView(withText("Task03")).check(matches(isDisplayed()))

        //回歸
        onView(idList.menu_filter).perform(click())
        onView(withText(R.string.nav_all)).perform(click())
        onView(idList.task_list).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0 , clickItemWithId(R.id.complete_checkbox)))

        util.deleteTasksByCustomer(3)
    }

    @Test
    fun Active_Filter_With_No_Task_Test(){
        //打開menu並點選Active
        onView(idList.menu_filter).perform(click())
        onView(withText(R.string.nav_active)).perform(click())

        onView(idList.no_tasks_icon).check(matches(withDrawable(R.drawable.ic_check_circle_96dp)))
        onView(idList.no_tasks_text).check((matches(withText("You have no active tasks!"))))
    }

    @Test
    fun Complete_Filter_Test(){
        val util = testUtil()
        util.prepareTasksByCustomer(3)

        //點選menu並點選Complete
        onView(idList.menu_filter).perform(click())
        onView(withText(R.string.nav_completed)).perform(click())

        //verify
        onView(idList.no_tasks_icon).check(matches(withDrawable(R.drawable.ic_verified_user_96dp)))
        onView(idList.no_tasks_text).check((matches(withText("You have no completed tasks!"))))


        util.deleteTasksByCustomer(3)
    }

    @Test
    fun Complete_Filter_With_Complete_Task_Test(){
        val util = testUtil()
        util.prepareTasksByCustomer(3)
        //打勾兩筆資料
        onView(idList.task_list).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0 , clickItemWithId(R.id.complete_checkbox)))
        onView(idList.task_list).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1 , clickItemWithId(R.id.complete_checkbox)))

        //點選menu並點選Complete
        Thread.sleep(1000)
        onView(idList.menu_filter).perform(click())
        onView(withText(R.string.nav_completed)).perform(click())
        Thread.sleep(1000)

        //Verify
        onView(withId(R.id.filtering_text)).check(matches(withText(R.string.label_completed)))
        onView(withText("Task01")).check(matches(isDisplayed()))
        onView(withText("Task02")).check(matches(isDisplayed()))
        onView(withText("Task03")).check(ViewAssertions.doesNotExist())

        onView(idList.menu_filter).perform(click())
        onView(withText(R.string.nav_all)).perform(click())
        onView(idList.task_list).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0 , clickItemWithId(R.id.complete_checkbox)))
        onView(idList.task_list).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1 , clickItemWithId(R.id.complete_checkbox)))

        util.deleteTasksByCustomer(3)
    }

    @Test
    fun Complete_Filter_With_No_Task_Test(){
        //點選menu並點選Complete
        onView(idList.menu_filter).perform(click())
        onView(withText(R.string.nav_completed)).perform(click())

        //verify
        onView(idList.no_tasks_icon).check(matches(withDrawable(R.drawable.ic_verified_user_96dp)))
        onView(idList.no_tasks_text).check((matches(withText("You have no completed tasks!"))))
    }



    private fun withDrawable(resourceId: Int): Matcher<in View>? {

        return DrawableMatcher(resourceId)
    }
    fun clickItemWithId(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController, view: View) {
                val v = view.findViewById<View>(id) as View
                v.performClick()
            }
        }
    }
}