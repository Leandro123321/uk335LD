package ch.ny.screens.homeactivity;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.ny.screens.searchactivity.SearchActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

@RunWith(AndroidJUnit4.class)
public class SearchQueryTest {

    public String searchQueryText;

    @Rule
    public ActivityTestRule<SearchActivity> activityRule
            = new ActivityTestRule<>(SearchActivity.class);

    @Before
    public void init() {
        Intents.init();
        searchQueryText = "Lisb";
    }

    @Test
    public void searchForCounty() {
        onView(withId(R.id.searchcity))
                .perform(typeText(searchQueryText));

        onData(anything())
                .inAdapterView(withId(R.id.listCities))
                .atPosition(0)
                .onChildView(withId(R.id.textview_itemname_listview))
                .check(matches(withText("Lisbon")));
    }
}
