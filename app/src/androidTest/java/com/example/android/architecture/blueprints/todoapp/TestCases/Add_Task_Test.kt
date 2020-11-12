package com.example.android.architecture.blueprints.todoapp.TestCases

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.TestUtil.testUtil
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Add_Task_Test {
    @get:Rule
    var activityRule = ActivityScenarioRule(TasksActivity::class.java)
    @Test
    fun Add_One_Task() {
        val util= testUtil()
        onView(withId(R.id.add_task_fab)).perform(click())
        onView(withId(R.id.add_task_title_edit_text)).perform(typeText("Task01"))
        onView(withId(R.id.add_task_description_edit_text)).perform(typeText("1"))
        onView(withId(R.id.save_task_fab)).perform(click())
        onView(withId(R.id.title_text)).check(matches(withText("Task01")))
        onView(withId(R.id.title_text)).perform(click())
        onView(withId(R.id.task_detail_description_text)).check(matches(withText("1")))
        util.deleteTasksByCustomer(1)
    }

    @Test
    fun Add_Task_Without_Description(){
        onView(withId(R.id.add_task_fab)).perform(click())
        onView(withId(R.id.add_task_title_edit_text)).perform(typeText("123"))
        onView(withId(R.id.add_task_description_edit_text)).perform(typeText(""), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.save_task_fab)).perform(click())
        Thread.sleep(1000)

        Espresso.pressBack()
        onView(allOf(withId(R.id.title_text), withText("123"))).check(doesNotExist())
    }

    @Test
    fun Add_Task_Continuousely(){
        val util = testUtil()
        util.prepareTasksByCustomer(3)

        for (i in 0 until 3)
        {
            onView(withText("Task0${i+1}")).perform(click())
            onView(withId(R.id.task_detail_title_text)).check(matches(withText("Task0${i+1}")))
            onView(withId(R.id.task_detail_description_text)).check(matches(withText("${i+1}")))
            Espresso.pressBack()
        }

        util.deleteTasksByCustomer(3)
    }

    @Test
    fun Add_Task_Without_Title(){
        onView(withId(R.id.add_task_fab)).perform(click())
        onView(withId(R.id.add_task_title_edit_text)).perform(typeText(""))
        onView(withId(R.id.add_task_description_edit_text)).perform(typeText("123"),ViewActions.closeSoftKeyboard())
        onView(withId(R.id.save_task_fab)).perform(click())
        Thread.sleep(1000)
        ViewActions.closeSoftKeyboard()
        Espresso.pressBack()
        onView(allOf(withId(R.id.title_text), withText(""))).check(doesNotExist())
    }

    @Test
    fun Add_Task_Until_Fill_Over_Screen_Test(){
        val util = testUtil()
        util.prepareTasksByCustomer(11)
        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10 , click()))
        onView(withId(R.id.task_detail_description_text)).check(matches(withText("11")))
        Espresso.pressBack()
        Thread.sleep(500)
        util.deleteTasksByCustomer(11)
    }



}