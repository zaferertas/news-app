package com.xxxxx.newsapplication

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
class MainFragmentTest {

    @get:Rule
    var activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java, true, false)

    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun testFragmentVisible() {

        val scenario = launchFragmentInContainer<MainFragment>(
            Bundle(),
            R.style.AppTheme
        )

        dataBindingIdlingResource.monitorFragment(scenario)
        onView(withId(R.id.mainRecyclerView)).check(matches(ViewMatchers.isDisplayed()))
    }

}