package com.example.android.architecture.blueprints.todoapp


import android.app.PendingIntent.getActivity
import android.service.autofill.Validators.not
import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import com.example.android.architecture.blueprints.todoapp.util.EspressoIdlingResource
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.startsWith
import org.junit.*
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ExampleTest {
    @get:Rule
    var activityRule = ActivityScenarioRule(TasksActivity::class.java)


//    @Before
//    fun registerIdlingResource(){
//        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
//    }
//    @After
//    fun unregisterIdlingResource(){
//        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
//    }

    @Test
    fun testAddTask() {
        onView(withId(R.id.add_task_fab)).perform(click())
        onView(withId(R.id.add_task_title_edit_text)).perform(typeText("Adopt Android Espresso Tests"))
        onView(withId(R.id.add_task_description_edit_text)).perform(typeText("Test Everything!"))
        onView(withId(R.id.save_task_fab)).perform(click())
        onView(withId(R.id.title_text)).check(matches(withText("Adopt Android Espresso Tests")))
        onView(withId(R.id.title_text)).perform(click())
        onView(withId(R.id.task_detail_description_text)).check(matches(withText("Test Everything!")))
    }

    @Test
    fun Add_Task_Without_Pwd(){
        onView(withId(R.id.add_task_fab)).perform(click())
        onView(withId(R.id.add_task_title_edit_text)).perform(typeText("123"))
        onView(withId(R.id.add_task_description_edit_text)).perform(typeText(""), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.save_task_fab)).perform(click())
        Thread.sleep(1000)
        ViewActions.closeSoftKeyboard()
        Espresso.pressBack()
        onView(allOf(withId(R.id.title_text), withText("123"))).check(doesNotExist())
    }

    @Test
    fun Add_Task_Continuouse(){
        onView(withId(R.id.add_task_fab)).perform(click())
        onView(withId(R.id.add_task_title_edit_text)).perform(typeText("Add Task Continuous"))
        onView(withId(R.id.add_task_description_edit_text)).perform(typeText("Test Everything!"))
        onView(withId(R.id.save_task_fab)).perform(click())
        //onView(allOf(withId(R.id.title_text), withText("Add Task Continuous"))).check(matches(withText("Add Task Continuous")))
        onView(withText("Add Task Continuous")).check(matches(withText("Add Task Continuous")))
        onView(withText("Add Task Continuous")).perform(click())
        onView(withId(R.id.task_detail_description_text)).check(matches(withText("Test Everything!")))
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
    fun All_Filter(){

        //val count =
        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0 , clickItemWithId(R.id.complete_checkbox)))
        onView(withId(R.id.menu_filter)).perform(click())
        onView(withText(R.string.nav_all)).perform(click())
        Thread.sleep(1000)
        onView(withText("Add Task Continuous")).check(matches(isDisplayed()))
        onView(withText("Adopt Android Espresso Tests")).check(matches(isDisplayed()))
    }

    @Test
    fun Active_Filter(){
        onView(withId(R.id.menu_filter)).perform(click())
        onView(withText(R.string.nav_active)).perform(click())
        Thread.sleep(1000)
        onView(withText("Adopt Android Espresso Tests")).check(doesNotExist())
        onView(withText("Add Task Continuous")).check(matches(isDisplayed()))
    }

    @Test
    fun Complete_Filter(){
        onView(withId(R.id.menu_filter)).perform(click())
        onView(withText(R.string.nav_completed)).perform(click())
        Thread.sleep(1000)
        onView(withText("Adopt Android Espresso Tests")).check(matches(isDisplayed()))
        onView(withText("Add Task Continuous")).check(doesNotExist())
    }


    @Test
    fun Statistics_test(){

        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.LEFT))).perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.statistics_fragment_dest))
        onView(withId(R.id.stats_active_text)).check(matches(withText(startsWith("Active tasks"))))
        onView(withId(R.id.stats_completed_text)).check(matches(withText(startsWith("Completed tasks"))))

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
