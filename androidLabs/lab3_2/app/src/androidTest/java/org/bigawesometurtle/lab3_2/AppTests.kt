package org.bigawesometurtle.lab3_2

import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.NoActivityResumedException
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation

import org.hamcrest.Matchers
import org.junit.Assert

import org.junit.Test
import org.junit.Rule

class AppTests {

    @get:Rule
    var intentsRule: IntentsTestRule<Activity1> = IntentsTestRule(Activity1::class.java)


    @Test
    fun act1Test() {
        intentsRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        Espresso.onView(withId(R.id.btn_from_first_to_second)).perform(ViewActions.click())
        intended(hasComponent(Activity2::class.java.name))
        intentsRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        Espresso.onView(withId(R.id.textView_act2)).check(matches(withText("Activity 2")))
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
        Espresso.onView(withId(R.id.textView_act1)).check(matches(withText("Activity 1")))
        openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
        Espresso.onView(Matchers.allOf(withText("About"))).perform(ViewActions.click())
        intended(hasComponent(ActivityAbout::class.java.name))
        Espresso.onView(withId(R.id.textView)).check(matches(withText("This is Activity 1")))
        intentsRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        Espresso.onView(withId(R.id.textView)).check(matches(withText("This is Activity 1")))
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
        Espresso.onView(withId(R.id.textView_act1)).check(matches(withText("Activity 1")))

    }

    @Test
    fun act2Test() {
        intentsRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        Espresso.onView(withId(R.id.btn_from_first_to_second)).perform(ViewActions.click())
        intended(hasComponent(Activity2::class.java.name))
        openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
        Espresso.onView(Matchers.allOf(withText("About"))).perform(ViewActions.click())
        intended(hasComponent(ActivityAbout::class.java.name))
        Espresso.onView(withId(R.id.textView)).check(matches(withText("This is Activity 2")))
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
        Espresso.onView(withId(R.id.textView_act2)).check(matches(withText("Activity 2")))
        Espresso.onView(withId(R.id.btn_from_second_to_third)).perform(ViewActions.click())
        intended(hasComponent(Activity3::class.java.name))
        intentsRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        Espresso.onView(withId(R.id.textView_act3)).check(matches(withText("Activity 3")))
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
        Espresso.onView(withId(R.id.textView_act2)).check(matches(withText("Activity 2")))
        Espresso.onView(withId(R.id.btn_from_second_to_first)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.textView_act1)).check(matches(withText("Activity 1")))
        try {
            Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
            Assert.fail()
        } catch (e: NoActivityResumedException) {

        }
    }

    @Test
    fun act3Test() {
        intentsRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        Espresso.onView(withId(R.id.btn_from_first_to_second)).perform(ViewActions.click())
        intended(hasComponent(Activity2::class.java.name))
        Espresso.onView(withId(R.id.btn_from_second_to_third)).perform(ViewActions.click())
        intended(hasComponent(Activity3::class.java.name))
        openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
        Espresso.onView(Matchers.allOf(withText("About"))).perform(ViewActions.click())
        intended(hasComponent(ActivityAbout::class.java.name))
        Espresso.onView(withId(R.id.textView)).check(matches(withText("This is Activity 3")))
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
        Espresso.onView(withId(R.id.textView_act3)).check(matches(withText("Activity 3")))
        Espresso.onView(withId(R.id.btn_from_third_to_second)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.textView_act2)).check(matches(withText("Activity 2")))
        Espresso.onView(withId(R.id.btn_from_second_to_third)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.textView_act3)).check(matches(withText("Activity 3")))
        Espresso.onView(withId(R.id.btn_from_third_to_first)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.textView_act1)).check(matches(withText("Activity 1")))
        intentsRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        Espresso.onView(withId(R.id.textView_act1)).check(matches(withText("Activity 1")))
        try {
            Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
            Assert.fail()
        } catch (e: NoActivityResumedException) {

        }
    }

    @Test
    fun backstackTest() {
        Espresso.onView(withId(R.id.btn_from_first_to_second)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.btn_from_second_to_third)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.btn_from_third_to_first)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.btn_from_first_to_second)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.btn_from_second_to_first)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.btn_from_first_to_second)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.btn_from_second_to_third)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.textView_act3)).check(matches(withText("Activity 3")))
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
        try {
            Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
            Assert.fail()
        } catch (e: NoActivityResumedException) {

        }
    }

}
