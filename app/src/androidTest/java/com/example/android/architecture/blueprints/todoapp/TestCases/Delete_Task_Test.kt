package com.example.android.architecture.blueprints.todoapp.TestCases

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.TestUtil.IdList
import com.example.android.architecture.blueprints.todoapp.TestUtil.testUtil
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Delete_Task_Test {
    @get:Rule
    val activityRule= ActivityScenarioRule(TasksActivity::class.java)

    @Test
    fun Delete_Task_in_Task_Detail_Test(){
        val util = testUtil()
        val idList = IdList()
        util.prepareTasksByCustomer(1)

        onView(withText("Task01")).perform(click())
        onView(idList.menu_delete).perform(click())

        //verify
        onView(withText("Task01")).check(doesNotExist())
    }

    @Test
    fun Clear_Complete_Task_Test(){
        val util=testUtil()
        util.prepareTasksByCustomer(1)
        onView(withText("Task01")).check(matches(isDisplayed()))
        //delete
        util.deleteTasksByCustomer(1)

        //verify
        onView(withText("Task01")).check(matches(not(isDisplayed()))) //(Task01 is still on the view)
    }

    @Test
    fun Clear_multiple_Complete_Tasks_Test(){
        val util=testUtil()
        util.prepareTasksByCustomer(3)
        for(i in 1 until 4)
        {
            onView(withText("Task0$i")).check(matches(isDisplayed()))
        }

        //delete
        util.deleteTasksByCustomer(3)

        for(k in 1 until 4)
        {
            onView(withText("Task0$k")).check(matches(not(isDisplayed()))) //(Tasks are still on the view)
        }
    }

}