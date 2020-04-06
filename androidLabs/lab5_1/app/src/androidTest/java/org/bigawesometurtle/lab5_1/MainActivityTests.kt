package org.bigawesometurtle.lab5_1

import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import org.junit.Test

import org.junit.Rule

class MainActivityTests {

    @get:Rule
    var mActivityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun rotateScreen1() {
        mActivityTestRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        onView(withId(R.id.editText)).perform(clearText())
        onView(withId(R.id.editText)).perform(typeText("Some Text abcd"))
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.button)).check(matches(withText("Something 2")))
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.button)).check(matches(withText("Something 2")))
        mActivityTestRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        onView(withId(R.id.button)).check(matches(withText("Something 1")))
        onView(withId(R.id.editText)).check(matches(withText("Some Text abcd")))

    }

    @Test
    fun rotateScreen2() {
        mActivityTestRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.button)).check(matches(withText("Something 2")))
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.button)).check(matches(withText("Something 2")))
        onView(withId(R.id.editText)).perform(clearText())
        onView(withId(R.id.editText)).perform(typeText("Hello"))
        mActivityTestRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        onView(withId(R.id.button)).check(matches(withText("Something 1")))
        onView(withId(R.id.editText)).check(matches(withText("Hello")))
    }
}
