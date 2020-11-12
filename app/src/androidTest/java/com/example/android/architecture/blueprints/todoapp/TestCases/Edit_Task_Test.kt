package com.example.android.architecture.blueprints.todoapp.TestCases

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.TestUtil.testUtil
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Edit_Task_Test {
    @get:Rule
    val activityRule= ActivityScenarioRule(TasksActivity::class.java)

    @Before
    fun setUp(){
        //Prepare for testing task
        val util = testUtil()
        val taskCount=1
        util.prepareTasksByCustomer(taskCount)

        //Click Task01 and check the toolbar title
        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0 ,click()))
        onView(withText("Task Details")).check(matches(isDisplayed()))

        //Click the Edit buttom and check the toolbar title
        onView(withId(R.id.edit_task_fab)).perform(click())
        onView(withText("Edit Task")).check(matches(isDisplayed()))
    }
    @Test
    fun edit_task_with_title(){
        val util = testUtil()


        //Edit the task title and click confirm
        onView(withId(R.id.add_task_title_edit_text)).perform(clearText())
        onView(withId(R.id.add_task_title_edit_text)).perform(typeText("Task02"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.save_task_fab)).perform(click())

        //進入detail選單
        onView(withText("Task02")).perform(click())

        //Verify the detail page for title and description
        onView(withId(R.id.task_detail_title_text)).check(matches(withText("Task02")))
        onView(withId(R.id.task_detail_description_text)).check(matches(withText("1")))

        //clear the testing task
        util.deleteTasksByCustomer(1)

    }

    @Test
    fun edit_task_with_description(){
        val util = testUtil()

        //Edit the task description and click confirm
        onView(withId(R.id.add_task_description_edit_text)).perform(clearText())
        onView(withId(R.id.add_task_description_edit_text)).perform(typeText("2"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.save_task_fab)).perform(click())

        //進入detail選單
        onView(withText("Task01")).perform(click())

        //Verify the detail page for title and description
        onView(withId(R.id.task_detail_title_text)).check(matches(withText("Task01")))
        onView(withId(R.id.task_detail_description_text)).check(matches(withText("2")))

        //clear the testing task
        util.deleteTasksByCustomer(1)

    }

    @Test
    fun edit_task_but_cancel(){
        val util = testUtil()
        //Edit the task title and description
        onView(withId(R.id.add_task_title_edit_text)).perform(clearText())
        onView(withId(R.id.add_task_title_edit_text)).perform(typeText("Task02"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.add_task_description_edit_text)).perform(clearText())
        onView(withId(R.id.add_task_description_edit_text)).perform(typeText("2"), ViewActions.closeSoftKeyboard())
        //press back to cancel
        Espresso.pressBack()

        //Verify the detail page for title and description
        onView(withId(R.id.task_detail_title_text)).check((matches(withText("Task01"))))
        onView(withId(R.id.task_detail_description_text)).check(matches(withText("1")))

        util.deleteTasksByCustomer(1)
    }

}