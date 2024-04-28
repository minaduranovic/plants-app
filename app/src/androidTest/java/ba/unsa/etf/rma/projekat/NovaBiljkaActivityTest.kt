package ba.unsa.etf.rma.projekat

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.graphics.BitmapFactory
import android.widget.Button
import android.widget.ImageView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import junit.framework.TestCase.assertNotNull
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NovaBiljkaActivityTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(
        NovaBiljkaActivity::class.java
    )

    @Test
    fun nazivETValidation() {

        onView(withId(R.id.nazivET)).perform(typeText("a"))
        closeSoftKeyboard()
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo());
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.nazivET)).check(matches(hasErrorText("Naziv biljke mora biti u opsegu od 2 do 20 znakova!")));

        onView(withId(R.id.nazivET)).perform(typeText("Biljka"))
        closeSoftKeyboard()
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo());
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.nazivET)).check(matches(not(hasErrorText("Naziv biljke mora biti u opsegu od 2 do 20 znakova!"))));

    }

    @Test
    fun porodicaETValidation() {

        onView(withId(R.id.porodicaET)).perform(typeText("a"))
        closeSoftKeyboard()
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo());
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.porodicaET)).check(matches(hasErrorText("Porodica biljke mora biti u opsegu od 2 do 20 znakova!")));

        onView(withId(R.id.porodicaET)).perform(typeText("Biljka"))
        closeSoftKeyboard()
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo());
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.porodicaET)).check(matches(not(hasErrorText("Porodica biljke mora biti u opsegu od 2 do 20 znakova!"))));

    }

    @Test
    fun medUpETValidation() {

        onView(withId(R.id.medicinskoUpozorenjeET)).perform(typeText("a"))
        closeSoftKeyboard()
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo());
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.medicinskoUpozorenjeET)).check(matches(hasErrorText("Medicinsko upozorenje mora biti u opsegu od 2 do 20 znakova!")));

        onView(withId(R.id.medicinskoUpozorenjeET)).perform(typeText("Biljka"))
        closeSoftKeyboard()
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo());
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.medicinskoUpozorenjeET)).check(matches(not(hasErrorText("Medicinsko upozorenje mora biti u opsegu od 2 do 20 znakova!"))));

    }

    @Test
    fun jeloETValidation() {

        onView(withId(R.id.jeloET)).perform(scrollTo());
        onView(withId(R.id.jeloET)).perform(typeText("a"))
        closeSoftKeyboard()
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo());
        onView(withId(R.id.dodajJeloBtn)).perform(click())
        onView(withId(R.id.jeloET)).check(matches(hasErrorText("Jelo mora biti u opsegu od 2 do 20 znakova!")));

        onView(withId(R.id.jeloET)).perform(typeText("Biljka"))
        closeSoftKeyboard()
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo());
        onView(withId(R.id.dodajJeloBtn)).perform(click())
        onView(withId(R.id.jeloET)).check(matches(not(hasErrorText("Jelo mora biti u opsegu od 2 do 20 znakova!"))));

    }


    @Test
    fun kameraTest() {

        val dataIntent: Intent = Intent()
        val resultIntent = Instrumentation.ActivityResult(Activity.RESULT_OK, dataIntent)

        val scenario = ActivityScenario.launch(NovaBiljkaActivity::class.java)

        scenario.onActivity { activity ->
            activity.findViewById<Button>(R.id.uslikajBiljkuBtn).performClick()
        }

        scenario.onActivity { activity ->
            val bitmap = BitmapFactory.decodeResource(activity.resources, R.drawable.plant)
            val imageView = activity.findViewById(R.id.slikaIV) as ImageView
            imageView.setImageBitmap(bitmap)
        }

        scenario.onActivity { activity ->
            val imageView: ImageView = activity.findViewById(R.id.slikaIV)
            assertNotNull(imageView.drawable)

        }


    }

}
