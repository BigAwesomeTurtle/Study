package org.bigawesometurtle.blackjack

import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso
import androidx.test.espresso.NoActivityResumedException
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers
import org.hamcrest.Matchers.not
import org.junit.Assert

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

class InstrumentedTest {

    @get:Rule
    var intentsRule: IntentsTestRule<MainActivity> = IntentsTestRule(MainActivity::class.java)

    @Test
    fun testUI_1() {
        intentsRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        Espresso.onView(withId(R.id.choose_card_cover_button)).perform(click())
        intended(hasComponent(ChooseCoverActivity::class.java.name))
        Espresso.onView(withId(R.id.img_choose_back_2)).perform(click())
        Espresso.onView(withId(R.id.img_choose_back_1)).perform(click())
        Espresso.pressBack()
        Espresso.onView(withId(R.id.start_game_button)).perform(click())
        Espresso.onView(withText("START")).perform(click())
        Espresso.onView(withId(R.id.btn_start)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.money_amount)).check(matches(withText("2500")))
        Espresso.onView(withId(R.id.bet_amount)).check(matches(withText("0")))
        Espresso.onView(withId(R.id.btn_bet_hundred)).perform(click())
        Espresso.onView(withId(R.id.btn_bet_ten)).perform(click())
        Espresso.onView(withId(R.id.money_amount)).check(matches(withText("2390")))
        Espresso.onView(withId(R.id.bet_amount)).check(matches(withText("110")))
        Espresso.onView(withId(R.id.btn_remove)).perform(click())
        Espresso.onView(withId(R.id.money_amount)).check(matches(withText("2500")))
        Espresso.onView(withId(R.id.bet_amount)).check(matches(withText("0")))
        Espresso.onView(withId(R.id.btn_bet_fifty)).perform(click())
        Espresso.onView(withId(R.id.btn_bet_ten)).perform(click())
        Espresso.onView(withId(R.id.money_amount)).check(matches(withText("2440")))
        Espresso.onView(withId(R.id.bet_amount)).check(matches(withText("60")))
        Espresso.onView(withId(R.id.btn_start)).perform(click())
        try {
            Espresso.onView(withId(R.id.btn_stand)).check(matches(isDisplayed()))
            Espresso.onView(withId(R.id.btn_stand)).perform(click())
        } catch (e: NoMatchingViewException) {
        }
        Espresso.onView(withId(R.id.btn_start)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.btn_bet_ten)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.btn_bet_fifty)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.btn_bet_hundred)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.bet_amount)).check(matches(withText("0")))
        Espresso.pressBack()
        try {
            Espresso.pressBack()
            fail()
        } catch (e: NoActivityResumedException) {

        }
    }
}
