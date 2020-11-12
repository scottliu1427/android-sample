package com.example.android.architecture.blueprints.todoapp.TestCases

import android.view.Gravity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.TestUtil.testUtil
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Refresh_Test {
    @get:Rule
    val activityRule= ActivityScenarioRule(TasksActivity::class.java)

    @Test
    fun Refresh_Test_After_Clear(){
        val util = testUtil()
        util.prepareTasksByCustomer(1)
        util.deleteTasksByCustomer(1)

        onView(withText("Task01")).check(matches(not(isDisplayed())))

        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext())
        onView(withText("Refresh")).perform(click())
        onView(withText("Task01")).check(doesNotExist())

        //點選navigation drawer
        onView(ViewMatchers.withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isClosed(Gravity.LEFT))).perform(DrawerActions.open())
        //點選Task List
        onView(ViewMatchers.withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.tasks_fragment_dest))

        onView(withText("Task01")).check(doesNotExist())


    }
}