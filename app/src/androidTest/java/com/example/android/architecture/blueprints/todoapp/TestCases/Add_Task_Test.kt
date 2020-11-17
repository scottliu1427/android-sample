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
import com.example.android.architecture.blueprints.todoapp.TestUtil.IdList
import com.example.android.architecture.blueprints.todoapp.TestUtil.testUtil
import com.example.android.architecture.blueprints.todoapp.addedittask.AddEditTaskViewModel
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import org.hamcrest.Matchers.allOf
import org.junit.BeforeClass
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
        val idList = IdList()
        onView(idList.add_task_fab).perform(click())
        onView(idList.edit_title).perform(typeText("Task01"))

        onView(idList.edit_description).perform(typeText("1"))
        onView(idList.save_task_fab).perform(click())
        onView(idList.title_text).check(matches(withText("Task01")))
        onView(idList.title_text).perform(click())
        onView(idList.task_detail_description_text).check(matches(withText("1")))
        util.deleteTasksByCustomer(1)
    }

    @Test
    fun Add_Task_Without_Description(){
        val idList = IdList()

        onView(idList.add_task_fab).perform(click())
        onView(idList.edit_title).perform(typeText("123"))
        onView(idList.edit_description).perform(typeText(""), ViewActions.closeSoftKeyboard())
        onView(idList.save_task_fab).perform(click())

        Espresso.pressBack()
        onView(allOf(idList.title_text, withText("123"))).check(doesNotExist())
    }

    @Test
    fun Add_Task_Continuousely(){
        val util = testUtil()
        util.prepareTasksByCustomer(3)
        val idList = IdList()
        for (i in 0 until 3)
        {
            onView(withText("Task0${i+1}")).perform(click())
            onView(idList.task_detail_title_text).check(matches(withText("Task0${i+1}")))
            onView(idList.task_detail_description_text).check(matches(withText("${i+1}")))
            Espresso.pressBack()
        }

        util.deleteTasksByCustomer(3)
    }

    @Test
    fun Add_Task_Without_Title(){
        val idList = IdList()
        onView(idList.add_task_fab).perform(click())
        onView(idList.edit_title ).perform(typeText(""))
        onView(idList.edit_description).perform(typeText("123"),ViewActions.closeSoftKeyboard())
        onView(idList.save_task_fab).perform(click())


        Espresso.pressBack()
        onView(allOf(idList.title_text, withText(""))).check(doesNotExist())
    }

    @Test
    fun Add_Task_Until_Fill_Over_Screen_Test(){
        val util = testUtil()
        val idList = IdList()
        util.prepareTasksByCustomer(11)
        onView(idList.task_list).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10 , click()))
        onView(idList.task_detail_description_text).check(matches(withText("11")))
        Espresso.pressBack()
        Thread.sleep(500)
        util.deleteTasksByCustomer(11)
    }


    

}