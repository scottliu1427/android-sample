package com.example.android.architecture.blueprints.todoapp.TestUtil

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity

class IdList {
    val add_task_fab = withId(R.id.add_task_fab)
    val save_task_fab = withId(R.id.save_task_fab)

    val edit_title = withId(R.id.add_task_title_edit_text)
    val edit_description = withId(R.id.add_task_description_edit_text)

    val title_text =withId(R.id.title_text)

    val task_detail_description_text = withId(R.id.task_detail_description_text)
    val task_detail_title_text = withId(R.id.task_detail_title_text)

    val task_list=withId(R.id.tasks_list)

    val menu_delete = withId(R.id.menu_delete)
    val edit_task_fab = withId(R.id.edit_task_fab)

    val drawer_layout= withId(R.id.drawer_layout)
    val nav_view = withId(R.id.nav_view)
    val stats_active_text = withId(R.id.stats_active_text)
    val stats_completed_text= withId(R.id.stats_completed_text)
    val menu_filter = withId(R.id.menu_filter)
    val filtering_text= withId(R.id.filtering_text)
    val no_tasks_icon = withId(R.id.no_tasks_icon)
    val no_tasks_text = withId(R.id.no_tasks_text)

}