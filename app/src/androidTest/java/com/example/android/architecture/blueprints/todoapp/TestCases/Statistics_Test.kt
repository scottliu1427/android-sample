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
import com.example.android.architecture.blueprints.todoapp.TestUtil.IdList
import com.example.android.architecture.blueprints.todoapp.TestUtil.testUtil
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Statistics_Test {

    @get:Rule
    var activityRule = ActivityScenarioRule(TasksActivity::class.java)

    val idList = IdList()

    @Test
    fun statistics_percentage_with_all_active_test(){
        //Prepare two tasks for testing data
        val dataCount = 2
        val util = testUtil()
        util.prepareTasksByCustomer(dataCount)

        //點選navigation drawer
        onView(idList.drawer_layout).check(matches(isClosed(Gravity.LEFT))).perform(DrawerActions.open())
        //點選Statistics
        onView(idList.nav_view).perform(NavigationViewActions.navigateTo(R.id.statistics_fragment_dest))

        //Verify the statistics description
        onView(idList.stats_active_text).check(matches(withText("Active tasks: 100.0%")))
        onView(idList.stats_completed_text).check(matches(withText("Completed tasks: 0.0%")))

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
        onView(idList.task_list ).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0 , util.clickItemWithId(R.id.complete_checkbox)))

        //點選navigation drawer
        onView(idList.drawer_layout ).check(matches(isClosed(Gravity.LEFT))).perform(DrawerActions.open())
        //點選Statistics
        onView(idList.nav_view ).perform(NavigationViewActions.navigateTo(R.id.statistics_fragment_dest))

        //Verify the statistics description
        onView(idList.stats_active_text ).check(matches(withText("Active tasks: 50.0%")))
        onView(idList.stats_completed_text ).check(matches(withText("Completed tasks: 50.0%")))

        //還原
        Espresso.pressBack()
        Thread.sleep(500)
        onView(idList.task_list).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0 , util.clickItemWithId(R.id.complete_checkbox)))

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
        onView(idList.task_list).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0 , util.clickItemWithId(R.id.complete_checkbox)))
        onView(idList.task_list).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1 , util.clickItemWithId(R.id.complete_checkbox)))

        //點選navigation drawer
        onView(idList.drawer_layout).check(matches(isClosed(Gravity.LEFT))).perform(DrawerActions.open())
        //點選Statistics
        onView(idList.nav_view).perform(NavigationViewActions.navigateTo(R.id.statistics_fragment_dest))

        //Verify the statistics description
        onView(idList.stats_active_text).check(matches(withText("Active tasks: 0.0%")))
        onView(idList.stats_completed_text).check(matches(withText("Completed tasks: 100.0%")))

        //還原
        Espresso.pressBack()
        Thread.sleep(500)
        onView(idList.task_list).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0 , util.clickItemWithId(R.id.complete_checkbox)))
        onView(idList.task_list).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1 , util.clickItemWithId(R.id.complete_checkbox)))

        //Delete testing task
        util.deleteTasksByCustomer(dataCount)
    }

    @Test
    fun statistics_percentage_with_empty_task_test(){
        //點選navigation drawer
        onView(idList.drawer_layout).check(matches(isClosed(Gravity.LEFT))).perform(DrawerActions.open())
        //點選Statistics
        onView(idList.nav_view).perform(NavigationViewActions.navigateTo(R.id.statistics_fragment_dest))
        //Verify the view
        onView(withText("You have no tasks.")).check(matches(isDisplayed()))
    }



}