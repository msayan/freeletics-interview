package com.hololo.app.squarerepo.ui.list

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.hololo.app.squarerepo.App
import com.hololo.app.squarerepo.R
import com.hololo.app.squarerepo.ui.main.MainActivity
import com.hololo.app.squarerepo.utils.WaitUtils
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ListFragmentTest {

    @get:Rule
    var mActivityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun openDetailFromList() {
        WaitUtils.waitTime()

        val relativeLayout = Espresso.onView(
                Matchers.allOf(childAtPosition(
                        childAtPosition(
                                ViewMatchers.withId(R.id.project_recyler),
                                0),
                        0),
                        ViewMatchers.isDisplayed()))
        relativeLayout.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        val linearLayout = Espresso.onView(
                Matchers.allOf(childAtPosition(
                        Matchers.allOf(ViewMatchers.withId(R.id.project_recyler),
                                childAtPosition(
                                        ViewMatchers.withId(R.id.rootView),
                                        0)),
                        0),
                        ViewMatchers.isDisplayed()))
        linearLayout.perform(ViewActions.click())

        WaitUtils.waitTime()

        onView(withText("html5")).check(matches(isDisplayed()))

        WaitUtils.cleanupWaitTime()
    }

    @Test
    fun bookmarkIconShowWhenRepoAddedToBookmark() {
        val relativeLayout = Espresso.onView(
                Matchers.allOf(childAtPosition(
                        childAtPosition(
                                ViewMatchers.withId(R.id.project_recyler),
                                0),
                        0),
                        ViewMatchers.isDisplayed()))
        relativeLayout.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        val linearLayout = Espresso.onView(
                Matchers.allOf(childAtPosition(
                        Matchers.allOf(ViewMatchers.withId(R.id.project_recyler),
                                childAtPosition(
                                        ViewMatchers.withId(R.id.rootView),
                                        0)),
                        0),
                        ViewMatchers.isDisplayed()))
        linearLayout.perform(ViewActions.click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        val actionMenuItemView = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.add_bookmark), ViewMatchers.withContentDescription("Add to Bookmark"),
                        childAtPosition(
                                childAtPosition(
                                        ViewMatchers.withId(R.id.action_bar),
                                        1),
                                0),
                        ViewMatchers.isDisplayed()))
        actionMenuItemView.perform(ViewActions.click())

        Espresso.pressBack()

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        val imageView = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.bookmarked),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.instanceOf(LinearLayout::class.java),
                                        0),
                                1),
                        ViewMatchers.isDisplayed()))
        imageView.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun bookmarkIconNotShownWhenRepoRemoveFromBookmark(){
        val imageView = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.bookmarked),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.instanceOf(LinearLayout::class.java),
                                        0),
                                1),
                        ViewMatchers.isDisplayed()))

        imageView.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        val relativeLayout = Espresso.onView(
                Matchers.allOf(childAtPosition(
                        childAtPosition(
                                ViewMatchers.withId(R.id.project_recyler),
                                0),
                        0),
                        ViewMatchers.isDisplayed()))
        relativeLayout.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        val linearLayout = Espresso.onView(
                Matchers.allOf(childAtPosition(
                        Matchers.allOf(ViewMatchers.withId(R.id.project_recyler),
                                childAtPosition(
                                        ViewMatchers.withId(R.id.rootView),
                                        0)),
                        0),
                        ViewMatchers.isDisplayed()))
        linearLayout.perform(ViewActions.click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        val actionMenuItemView = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.remove_bookmark), ViewMatchers.withContentDescription("Remove from Bookmark"),
                        childAtPosition(
                                childAtPosition(
                                        ViewMatchers.withId(R.id.action_bar),
                                        1),
                                0),
                        ViewMatchers.isDisplayed()))
        actionMenuItemView.perform(ViewActions.click())

        Espresso.pressBack()

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        imageView.check(doesNotExist())
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

}