package com.example.swapi.ui.common

import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.swapi.R

/**
 * CommonFragment
 *
 * So far only use to define fillText functions, that are a fun and simple way to fill in a
 * TextView, with a value from data object, in a single line. We could think of using a map even,
 * but at this stage it seems like over-engineering to me.
 */
open class CommonFragment: Fragment() {
    /**
     * Fill a TextField with a specific resource Id, given a string to write in it.
     *
     * @param root The root view to find the TextView in.
     * @param resourceId Id of the TextFiend
     * @param stringValue string to fill with
     */
    protected fun fillText(root: View, resourceId: Int, stringValue: String) {
        fillText(root, resourceId, stringValue, null)
    }

    /**
     * Fill a TextField with a specific resource Id, given a string to write in it.
     *
     * @param root The root view to find the TextView in.
     * @param resourceId Id of the TextFiend
     * @param stringValue string to fill with
     * @param suffix String to append after the value (unit to display)
     */
    protected fun fillText(root: View, resourceId: Int, stringValue: String, suffix: String?) {
        //TODO use strings with parameters instead of string concatenation
        val nameText = root.findViewById<View>(resourceId) as TextView
        var fullString = stringValue
        if (stringValue != getString(R.string.string_details_unknown) && suffix != null)
        {
            fullString += suffix
        }
        nameText.text = fullString
    }
}