package com.xxxxx.newsapplication

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Before
    fun setUp() {
        val mainFragment: Fragment = MainFragment()
        activityTestRule.activity.supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, mainFragment)
    }

    @Test
    fun test_RecyclerView_Displayed() {
        onView(withId(R.id.mainRecyclerView)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_Clicking_MovieItem_Opens_Details_Fragment() {

        onView(withId(R.id.mainRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.details_title)).check(matches(ViewMatchers.isDisplayed()))
    }

}