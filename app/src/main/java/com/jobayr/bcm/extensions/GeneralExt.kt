package com.jobayr.bcm.extensions

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.shashank.sony.fancytoastlib.FancyToast

fun Activity.showToast(text: String) {
    FancyToast.makeText(this, text, Toast.LENGTH_LONG, FancyToast.DEFAULT, false).show()
}

fun Activity.showSuccessToast(text: String) {
    FancyToast.makeText(this, text, Toast.LENGTH_LONG, FancyToast.SUCCESS, false).show()
}

fun Activity.showErrorToast(text: String) {
    FancyToast.makeText(this, text, Toast.LENGTH_LONG, FancyToast.ERROR, false).show()
}

fun Fragment.showToast(text: String) {
    FancyToast.makeText(requireContext(), text, Toast.LENGTH_LONG, FancyToast.DEFAULT, false).show()
}

fun Fragment.showSuccessToast(text: String) {
    FancyToast.makeText(requireContext(), text, Toast.LENGTH_LONG, FancyToast.SUCCESS, false).show()
}

fun Fragment.showErrorToast(text: String) {
    FancyToast.makeText(requireContext(), text, Toast.LENGTH_LONG, FancyToast.ERROR, false).show()
}

fun View.openOnClick(className: Class<*>) {
    setOnClickListener {
        context.startActivity(Intent(context, className))
    }
}

fun getDeptList(): MutableList<String> {
    val list = mutableListOf<String>()
    list.add("ARC")
    list.add("CSE")
    list.add("EEE")
    list.add("ENG")
    list.add("ESS")
    list.add("MNS")
    list.add("PHR")
    return list
}

fun getDeptName(index: Int): String? {
    var name: String? = null
    when(index) {
        0 -> name = "ARC"
        1 -> name = "CSE"
        2 -> name = "EEE"
        3 -> name = "ENG"
        4 -> name = "ESS"
        5 -> name = "MNS"
        6 -> name = "PHR"
    }
    return name
}
