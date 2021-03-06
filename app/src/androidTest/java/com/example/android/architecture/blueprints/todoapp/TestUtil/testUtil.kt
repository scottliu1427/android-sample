package com.example.android.architecture.blueprints.todoapp.TestUtil

import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.architecture.blueprints.todoapp.R
import org.hamcrest.Matcher

class testUtil {
    val idList = IdList()


    fun prepareTasksByCustomer(count:Int){
        for(i in 0 until count)
        {
            onView(idList.add_task_fab).perform(click())
            onView(idList.edit_title).perform(ViewActions.typeText("Task0${i+1}"),ViewActions.closeSoftKeyboard())
            onView(idList.edit_description).perform(ViewActions.typeText("${i+1}"),ViewActions.closeSoftKeyboard())
            onView(idList.save_task_fab).perform(click())
        }
    }

    fun deleteTasksByCustomer(count: Int){
        //點選navigation drawer
        onView(idList.drawer_layout).check(matches(isClosed(Gravity.LEFT))).perform(DrawerActions.open())
        //點選Task List
        onView(idList.nav_view).perform(NavigationViewActions.navigateTo(R.id.tasks_fragment_dest))
        Thread.sleep(2000)

        for(i in 0 until count)
        {
            onView(idList.task_list).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(i , clickItemWithId(R.id.complete_checkbox)))
        }
        Thread.sleep(2000)
        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext())
        //Thread.sleep(2000)
        onView(withText("Clear completed")).perform(click())
        
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