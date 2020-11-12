package com.example.android.architecture.blueprints.todoapp.TestCases

import android.view.Gravity
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.TestUtil.testUtil
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Statistics_Test {
    @get:Rule
    var activityRule = ActivityScenarioRule(TasksActivity::class.java)

    @Test
    fun statistics_percentage_with_all_active_test(){
        //Prepare two tasks for testing data
        val dataCount = 2
        val util = testUtil()
        util.prepareTasksByCustomer(dataCount)

        //點選navigation drawer
        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.LEFT))).perform(DrawerActions.open())
        //點選Statistics
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.statistics_fragment_dest))

        //Verify the statistics description
        onView(withId(R.id.stats_active_text)).check(matches(withText("Active tasks: 100.0%")))
        onView(withId(R.id.stats_completed_text)).check(matches(withText("Completed tasks: 0.0%")))

        //Delete testing task
        util.deleteTasksByCustomer(dataCount)
    }

    @Test
    fun statistics_percentage_with_mid_range_test(){

        //Prepare two tasks for testing data
        val dataCount = 2
        val util = testUtil()
        util.prepareTasksByCustomer(dataCount)

        //Click one task's checkbox as complete
        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0 , util.clickItemWithId(R.id.complete_checkbox)))

        //點選navigation drawer
        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.LEFT))).perform(DrawerActions.open())
        //點選Statistics
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.statistics_fragment_dest))

        //Verify the statistics description
        onView(withId(R.id.stats_active_text)).check(matches(withText("Active tasks: 50.0%")))
        onView(withId(R.id.stats_completed_text)).check(matches(withText("Completed tasks: 50.0%")))

        //還原
        Espresso.pressBack()
        Thread.sleep(500)
        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0 , util.clickItemWithId(R.id.complete_checkbox)))

        //Delete testing task
        util.deleteTasksByCustomer(dataCount)
    }

    @Test
    fun statistics_percentage_with_all_complete_test(){
        //Prepare two tasks for testing data
        val dataCount = 2
        val util = testUtil()
        util.prepareTasksByCustomer(dataCount)

        //Click all tasks' checkbox as complete
        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0 , util.clickItemWithId(R.id.complete_checkbox)))
        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1 , util.clickItemWithId(R.id.complete_checkbox)))

        //點選navigation drawer
        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.LEFT))).perform(DrawerActions.open())
        //點選Statistics
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.statistics_fragment_dest))

        //Verify the statistics description
        onView(withId(R.id.stats_active_text)).check(matches(withText("Active tasks: 0.0%")))
        onView(withId(R.id.stats_completed_text)).check(matches(withText("Completed tasks: 100.0%")))

        //還原
        Espresso.pressBack()
        Thread.sleep(500)
        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0 , util.clickItemWithId(R.id.complete_checkbox)))
        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1 , util.clickItemWithId(R.id.complete_checkbox)))

        //Delete testing task
        util.deleteTasksByCustomer(dataCount)
    }

    @Test
    fun statistics_percentage_with_empty_task_test(){
        //點選navigation drawer
        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.LEFT))).perform(DrawerActions.open())
        //點選Statistics
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.statistics_fragment_dest))
        //Verify the view
        onView(withText("You have no tasks.")).check(matches(isDisplayed()))
    }


//    fun clickItemWithId(id: Int): ViewAction {
//        return object : ViewAction {
//            override fun getConstraints(): Matcher<View>? {
//                return null
//            }
//
//            override fun getDescription(): String {
//                return "Click on a child view with specified id."
//            }
//
//            override fun perform(uiController: UiController, view: View) {
//                val v = view.findViewById<View>(id) as View
//                v.performClick()
//            }
//        }
//    }
}