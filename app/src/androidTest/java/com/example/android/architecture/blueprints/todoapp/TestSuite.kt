package com.example.android.architecture.blueprints.todoapp

import com.example.android.architecture.blueprints.todoapp.TestCases.*
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
        Add_Task_Test::class,
        Delete_Task_Test::class,
        Edit_Task_Test::class,
        Statistics_Test::class,
        Task_Filter_Test::class

)
class TestSuite {
}