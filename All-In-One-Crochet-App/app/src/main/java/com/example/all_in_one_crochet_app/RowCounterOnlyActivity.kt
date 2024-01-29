package com.example.all_in_one_crochet_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.example.all_in_one_crochet_app.firestore.FirestoreData
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RowCounterOnlyActivity : SettingsActivity() {

    val db = Firebase.firestore

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_row_counter_only)

        val viewTitle = findViewById<TextView>(R.id.view_name_text)
        viewTitle.text = "Row Counter"
        supportActionBar?.hide()

        // MAIN COUNTER
        val rowNumberText = findViewById<TextView>(R.id.row_number_text)
        val plusBtn = findViewById<Button>(R.id.plus_button)
        val minusBtn = findViewById<Button>(R.id.minus_button)
        val resetBtn = findViewById<ImageButton>(R.id.reset_button)
        var arrayOfVisibility = IntArray(10)
//        var listOfNames = Array<String>(10) { "it = $it" }
//        var listOfRows = Array<String>(10) { "it = $it" }


        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val firebaseData = FirestoreData(
            "",
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
            listOf("", "", "", "", "", "", "", "", "", ""),
            listOf("", "", "", "", "", "", "", "", "", "")
        )

        // reading data works
//        GlobalScope.launch(Dispatchers.IO) {
//            userId?.let {
//                val documentSnapshot =
        db.collection(FirebaseAuth.getInstance().currentUser?.email.toString())
            .document("rowCounterData")
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val dbData = documentSnapshot.toObject(FirestoreData::class.java)
                    rowNumberText.text = dbData?.mainCounterRows
                    // Debug log to check secCountersVisibility before conversion
                    Log.d(
                        "FirestoreData",
                        "secCountersVisibility: ${dbData?.secCountersVisibility}"
                    )

                    // Convert secCountersVisibility to IntArray
                    dbData?.secCountersVisibility?.let { visibility ->
                        arrayOfVisibility = visibility.toIntArray()
                    }

                    // Debug log to check arrayOfVisibility after conversion
                    Log.d(
                        "ConvertedVisibility",
                        "arrayOfVisibility: ${arrayOfVisibility.joinToString()}"
                    )

                    // Check if conversion was successful
                    Log.d("ConversionSuccess", "Array size: ${arrayOfVisibility.size}")


//                    listOfNames = dbData?.secCountersNames?.toArray()
//                    listOfRows = dbData?.secCountersNames
                } else {
//                    db.collection(FirebaseAuth.getInstance().currentUser?.email.toString())
//                        .document("rowCounterData")
//                        .set(firebaseData).await()

                }
            }
//        }

        minusBtn.isEnabled = rowNumberText.text != "0"

        plusBtn.setOnClickListener {
            var numbTemp = (rowNumberText.text.toString()).toInt()
            numbTemp += 1
            rowNumberText.text = numbTemp.toString()
            minusBtn.isEnabled = true

            // modifying data works... kinda
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            val email = FirebaseAuth.getInstance().currentUser?.email.toString()

            GlobalScope.launch(Dispatchers.IO) {
                userId?.let {
                    db.collection(email).document("rowCounterData")
                        .update("mainCounterRows", rowNumberText.text.toString()).await()
                }
            }
        }

        minusBtn.setOnClickListener {
            var numbTemp = (rowNumberText.text.toString()).toInt()
            if (numbTemp == 1) {
                numbTemp = 0
                rowNumberText.text = numbTemp.toString()
                minusBtn.isEnabled = false

            } else {
                minusBtn.isEnabled = true
                numbTemp -= 1
                rowNumberText.text = numbTemp.toString()
            }

            val userId = FirebaseAuth.getInstance().currentUser?.uid
            val email = FirebaseAuth.getInstance().currentUser?.email.toString()


            GlobalScope.launch(Dispatchers.IO) {
                userId?.let {
                    db.collection(email).document("rowCounterData")
                        .update("mainCounterRows", rowNumberText.text.toString()).await()
                }
            }
        }

        resetBtn.setOnClickListener {
            rowNumberText.text = "0"
            minusBtn.isEnabled = false

            val userId = FirebaseAuth.getInstance().currentUser?.uid
            val email = FirebaseAuth.getInstance().currentUser?.email.toString()


            GlobalScope.launch(Dispatchers.IO) {
                userId?.let {
                    db.collection(email).document("rowCounterData")
                        .update("mainCounterRows", rowNumberText.text.toString()).await()
                }
            }
        }

        val settingsButton = findViewById<ImageButton>(R.id.settings_button)
        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        // SECONDARY COUNTERS

        val addCounterButton = findViewById<MaterialButton>(R.id.add_counter_button)

        // counters
        val counter1 = findViewById<ConstraintLayout>(R.id.counter1)
        val counter2 = findViewById<ConstraintLayout>(R.id.counter2)
        val counter3 = findViewById<ConstraintLayout>(R.id.counter3)
        val counter4 = findViewById<ConstraintLayout>(R.id.counter4)
        val counter5 = findViewById<ConstraintLayout>(R.id.counter5)
        val counter6 = findViewById<ConstraintLayout>(R.id.counter6)
        val counter7 = findViewById<ConstraintLayout>(R.id.counter7)
        val counter8 = findViewById<ConstraintLayout>(R.id.counter8)
        val counter9 = findViewById<ConstraintLayout>(R.id.counter9)
        val counter10 = findViewById<ConstraintLayout>(R.id.counter10)

        Log.d("visibilityArrayInCode", "${arrayOfVisibility.joinToString()}")
        counter1.isVisible = arrayOfVisibility[0] == 1
        counter2.isVisible = arrayOfVisibility[1] == 1
        counter3.isVisible = arrayOfVisibility[2] == 1
        counter4.isVisible = arrayOfVisibility[3] == 1
        counter5.isVisible = arrayOfVisibility[4] == 1
        counter6.isVisible = arrayOfVisibility[5] == 1
        counter7.isVisible = arrayOfVisibility[6] == 1
        counter8.isVisible = arrayOfVisibility[7] == 1
        counter9.isVisible = arrayOfVisibility[8] == 1
        counter10.isVisible = arrayOfVisibility[9] == 1


        // counter name edits
        val counter1Edit = findViewById<EditText>(R.id.counter1_edit)
        val counter2Edit = findViewById<EditText>(R.id.counter2_edit)
        val counter3Edit = findViewById<EditText>(R.id.counter3_edit)
        val counter4Edit = findViewById<EditText>(R.id.counter4_edit)
        val counter5Edit = findViewById<EditText>(R.id.counter5_edit)
        val counter6Edit = findViewById<EditText>(R.id.counter6_edit)
        val counter7Edit = findViewById<EditText>(R.id.counter7_edit)
        val counter8Edit = findViewById<EditText>(R.id.counter8_edit)
        val counter9Edit = findViewById<EditText>(R.id.counter9_edit)
        val counter10Edit = findViewById<EditText>(R.id.counter10_edit)

        // counter row number texts
        val rowNumber1Text = findViewById<TextView>(R.id.row_number1_text)
        val rowNumber2Text = findViewById<TextView>(R.id.row_number2_text)
        val rowNumber3Text = findViewById<TextView>(R.id.row_number3_text)
        val rowNumber4Text = findViewById<TextView>(R.id.row_number4_text)
        val rowNumber5Text = findViewById<TextView>(R.id.row_number5_text)
        val rowNumber6Text = findViewById<TextView>(R.id.row_number6_text)
        val rowNumber7Text = findViewById<TextView>(R.id.row_number7_text)
        val rowNumber8Text = findViewById<TextView>(R.id.row_number8_text)
        val rowNumber9Text = findViewById<TextView>(R.id.row_number9_text)
        val rowNumber10Text = findViewById<TextView>(R.id.row_number10_text)

        // counter plus buttons
        val plusButton1 = findViewById<Button>(R.id.plus1_button)
        val plusButton2 = findViewById<Button>(R.id.plus2_button)
        val plusButton3 = findViewById<Button>(R.id.plus3_button)
        val plusButton4 = findViewById<Button>(R.id.plus4_button)
        val plusButton5 = findViewById<Button>(R.id.plus5_button)
        val plusButton6 = findViewById<Button>(R.id.plus6_button)
        val plusButton7 = findViewById<Button>(R.id.plus7_button)
        val plusButton8 = findViewById<Button>(R.id.plus8_button)
        val plusButton9 = findViewById<Button>(R.id.plus9_button)
        val plusButton10 = findViewById<Button>(R.id.plus10_button)

        // counter minus buttons
        val minusButton1 = findViewById<Button>(R.id.minus1_button)
        val minusButton2 = findViewById<Button>(R.id.minus2_button)
        val minusButton3 = findViewById<Button>(R.id.minus3_button)
        val minusButton4 = findViewById<Button>(R.id.minus4_button)
        val minusButton5 = findViewById<Button>(R.id.minus5_button)
        val minusButton6 = findViewById<Button>(R.id.minus6_button)
        val minusButton7 = findViewById<Button>(R.id.minus7_button)
        val minusButton8 = findViewById<Button>(R.id.minus8_button)
        val minusButton9 = findViewById<Button>(R.id.minus9_button)
        val minusButton10 = findViewById<Button>(R.id.minus10_button)

        minusButton1.isEnabled = rowNumber1Text.text != "0"
        minusButton2.isEnabled = rowNumber2Text.text != "0"
        minusButton3.isEnabled = rowNumber3Text.text != "0"
        minusButton4.isEnabled = rowNumber4Text.text != "0"
        minusButton5.isEnabled = rowNumber5Text.text != "0"
        minusButton6.isEnabled = rowNumber6Text.text != "0"
        minusButton7.isEnabled = rowNumber7Text.text != "0"
        minusButton8.isEnabled = rowNumber8Text.text != "0"
        minusButton9.isEnabled = rowNumber9Text.text != "0"
        minusButton10.isEnabled = rowNumber10Text.text != "0"

        // counter reset buttons
        val resetButton1 = findViewById<ImageButton>(R.id.reset1_button)
        val resetButton2 = findViewById<ImageButton>(R.id.reset2_button)
        val resetButton3 = findViewById<ImageButton>(R.id.reset3_button)
        val resetButton4 = findViewById<ImageButton>(R.id.reset4_button)
        val resetButton5 = findViewById<ImageButton>(R.id.reset5_button)
        val resetButton6 = findViewById<ImageButton>(R.id.reset6_button)
        val resetButton7 = findViewById<ImageButton>(R.id.reset7_button)
        val resetButton8 = findViewById<ImageButton>(R.id.reset8_button)
        val resetButton9 = findViewById<ImageButton>(R.id.reset9_button)
        val resetButton10 = findViewById<ImageButton>(R.id.reset10_button)

        // counter edit buttons
        val editButton1 = findViewById<ImageButton>(R.id.edit1_button)
        val editButton2 = findViewById<ImageButton>(R.id.edit2_button)
        val editButton3 = findViewById<ImageButton>(R.id.edit3_button)
        val editButton4 = findViewById<ImageButton>(R.id.edit4_button)
        val editButton5 = findViewById<ImageButton>(R.id.edit5_button)
        val editButton6 = findViewById<ImageButton>(R.id.edit6_button)
        val editButton7 = findViewById<ImageButton>(R.id.edit7_button)
        val editButton8 = findViewById<ImageButton>(R.id.edit8_button)
        val editButton9 = findViewById<ImageButton>(R.id.edit9_button)
        val editButton10 = findViewById<ImageButton>(R.id.edit10_button)

        // counter delete buttons
        val deleteButton1 = findViewById<ImageButton>(R.id.delete1_button)
        val deleteButton2 = findViewById<ImageButton>(R.id.delete2_button)
        val deleteButton3 = findViewById<ImageButton>(R.id.delete3_button)
        val deleteButton4 = findViewById<ImageButton>(R.id.delete4_button)
        val deleteButton5 = findViewById<ImageButton>(R.id.delete5_button)
        val deleteButton6 = findViewById<ImageButton>(R.id.delete6_button)
        val deleteButton7 = findViewById<ImageButton>(R.id.delete7_button)
        val deleteButton8 = findViewById<ImageButton>(R.id.delete8_button)
        val deleteButton9 = findViewById<ImageButton>(R.id.delete9_button)
        val deleteButton10 = findViewById<ImageButton>(R.id.delete10_button)

        // ~~~FUNCTIONS~~~
        // ~add~
        addRow(plusButton1, minusButton1, rowNumber1Text)
        addRow(plusButton2, minusButton2, rowNumber2Text)
        addRow(plusButton3, minusButton3, rowNumber3Text)
        addRow(plusButton4, minusButton4, rowNumber4Text)
        addRow(plusButton5, minusButton5, rowNumber5Text)
        addRow(plusButton6, minusButton6, rowNumber6Text)
        addRow(plusButton7, minusButton7, rowNumber7Text)
        addRow(plusButton8, minusButton8, rowNumber8Text)
        addRow(plusButton9, minusButton9, rowNumber9Text)
        addRow(plusButton10, minusButton10, rowNumber10Text)

        // ~subtract~
        subtractRow(minusButton1, rowNumber1Text)
        subtractRow(minusButton2, rowNumber2Text)
        subtractRow(minusButton3, rowNumber3Text)
        subtractRow(minusButton4, rowNumber4Text)
        subtractRow(minusButton5, rowNumber5Text)
        subtractRow(minusButton6, rowNumber6Text)
        subtractRow(minusButton7, rowNumber7Text)
        subtractRow(minusButton8, rowNumber8Text)
        subtractRow(minusButton9, rowNumber9Text)
        subtractRow(minusButton10, rowNumber10Text)

        // ~reset~
        resetRow(resetButton1, minusButton1, rowNumber1Text)
        resetRow(resetButton2, minusButton2, rowNumber2Text)
        resetRow(resetButton3, minusButton3, rowNumber3Text)
        resetRow(resetButton4, minusButton4, rowNumber4Text)
        resetRow(resetButton5, minusButton5, rowNumber5Text)
        resetRow(resetButton6, minusButton6, rowNumber6Text)
        resetRow(resetButton7, minusButton7, rowNumber7Text)
        resetRow(resetButton8, minusButton8, rowNumber8Text)
        resetRow(resetButton9, minusButton9, rowNumber9Text)
        resetRow(resetButton10, minusButton10, rowNumber10Text)

        // ~edit~
        editCounter(editButton1, counter1Edit)
        editCounter(editButton2, counter2Edit)
        editCounter(editButton3, counter3Edit)
        editCounter(editButton4, counter4Edit)
        editCounter(editButton5, counter5Edit)
        editCounter(editButton6, counter6Edit)
        editCounter(editButton7, counter7Edit)
        editCounter(editButton8, counter8Edit)
        editCounter(editButton9, counter9Edit)
        editCounter(editButton10, counter10Edit)

        // ~delete~
        deleteCounter(
            deleteButton1,
            counter1,
            counter1Edit,
            rowNumber1Text,
            minusButton1,
            counter1,
            counter2,
            counter3,
            counter4,
            counter5,
            counter6,
            counter7,
            counter8,
            counter9,
            counter10
        )
        deleteCounter(
            deleteButton2,
            counter2,
            counter2Edit,
            rowNumber2Text,
            minusButton2,
            counter1,
            counter2,
            counter3,
            counter4,
            counter5,
            counter6,
            counter7,
            counter8,
            counter9,
            counter10
        )
        deleteCounter(
            deleteButton3,
            counter3,
            counter3Edit,
            rowNumber3Text,
            minusButton3,
            counter1,
            counter2,
            counter3,
            counter4,
            counter5,
            counter6,
            counter7,
            counter8,
            counter9,
            counter10
        )
        deleteCounter(
            deleteButton4,
            counter4,
            counter4Edit,
            rowNumber4Text,
            minusButton4,
            counter1,
            counter2,
            counter3,
            counter4,
            counter5,
            counter6,
            counter7,
            counter8,
            counter9,
            counter10
        )
        deleteCounter(
            deleteButton5,
            counter5,
            counter5Edit,
            rowNumber5Text,
            minusButton5,
            counter1,
            counter2,
            counter3,
            counter4,
            counter5,
            counter6,
            counter7,
            counter8,
            counter9,
            counter10
        )
        deleteCounter(
            deleteButton6,
            counter6,
            counter6Edit,
            rowNumber6Text,
            minusButton6,
            counter1,
            counter2,
            counter3,
            counter4,
            counter5,
            counter6,
            counter7,
            counter8,
            counter9,
            counter10
        )
        deleteCounter(
            deleteButton7,
            counter7,
            counter7Edit,
            rowNumber7Text,
            minusButton7,
            counter1,
            counter2,
            counter3,
            counter4,
            counter5,
            counter6,
            counter7,
            counter8,
            counter9,
            counter10
        )
        deleteCounter(
            deleteButton8,
            counter8,
            counter8Edit,
            rowNumber8Text,
            minusButton8,
            counter1,
            counter2,
            counter3,
            counter4,
            counter5,
            counter6,
            counter7,
            counter8,
            counter9,
            counter10
        )
        deleteCounter(
            deleteButton9,
            counter9,
            counter9Edit,
            rowNumber9Text,
            minusButton9,
            counter1,
            counter2,
            counter3,
            counter4,
            counter5,
            counter6,
            counter7,
            counter8,
            counter9,
            counter10
        )
        deleteCounter(
            deleteButton10,
            counter10,
            counter10Edit,
            rowNumber10Text,
            minusButton10,
            counter1,
            counter2,
            counter3,
            counter4,
            counter5,
            counter6,
            counter7,
            counter8,
            counter9,
            counter10
        )


        addCounterButton.setOnClickListener {
            if (!counter1.isVisible) {
                counter1.isVisible = true
            } else if (!counter2.isVisible) {
                counter2.isVisible = true
            } else if (!counter3.isVisible) {
                counter3.isVisible = true
            } else if (!counter4.isVisible) {
                counter4.isVisible = true
            } else if (!counter5.isVisible) {
                counter5.isVisible = true
            } else if (!counter6.isVisible) {
                counter6.isVisible = true
            } else if (!counter7.isVisible) {
                counter7.isVisible = true
            } else if (!counter8.isVisible) {
                counter8.isVisible = true
            } else if (!counter9.isVisible) {
                counter9.isVisible = true
            } else if (!counter10.isVisible) {
                counter10.isVisible = true
            } else {
                showSnackBar("Reached limit of: 10 counters", false)
            }

            val userId = FirebaseAuth.getInstance().currentUser?.uid
            val email = FirebaseAuth.getInstance().currentUser?.email.toString()
            val visibilityList = createVisibilityList(
                counter1,
                counter2,
                counter3,
                counter4,
                counter5,
                counter6,
                counter7,
                counter8,
                counter9,
                counter10
            )

            GlobalScope.launch(Dispatchers.IO) {
                userId?.let {
                    db.collection(email).document("rowCounterData")
                        .update("secCountersVisibility", visibilityList).await()
                }
            }
        }
    }


    //~~~FUNCTIONS~~~
    private fun addRow(plusButton: Button, minusButton: Button, rowText: TextView) {
        plusButton.setOnClickListener {
            var numbTemp = (rowText.text.toString()).toInt()
            numbTemp += 1
            rowText.text = numbTemp.toString()
            minusButton.isEnabled = true
        }
    }

    private fun subtractRow(minusButton: Button, rowText: TextView) {
        minusButton.setOnClickListener {
            var numbTemp = (rowText.text.toString()).toInt()
            if (numbTemp == 1) {
                numbTemp -= 1
                rowText.text = numbTemp.toString()
                minusButton.isEnabled = false

            } else {
                minusButton.isEnabled = true
                numbTemp -= 1
                rowText.text = numbTemp.toString()
            }
        }
    }

    private fun resetRow(resetButton: ImageButton, minusButton: Button, rowText: TextView) {
        resetButton.setOnClickListener {
            rowText.text = "0"
            minusButton.isEnabled = false
        }
    }

    private fun editCounter(editButton: ImageButton, counterNameEdit: EditText) {
        var counter = 0
        editButton.setOnClickListener {
            if (counter == 0) {
                counterNameEdit.isEnabled = true
                counterNameEdit.backgroundTintList = ContextCompat.getColorStateList(
                    editButton.context,
                    R.color.light_gray_transparency
                )
                editButton.backgroundTintList =
                    ContextCompat.getColorStateList(editButton.context, R.color.light_gray)
                counter += 1
            } else {
                counterNameEdit.isEnabled = false
                counterNameEdit.backgroundTintList = ContextCompat.getColorStateList(
                    editButton.context, getAppColorRes(
                        androidx.appcompat.R.attr.colorAccent
                    )
                )
                editButton.backgroundTintList = ContextCompat.getColorStateList(
                    editButton.context, getAppColorRes(
                        androidx.appcompat.R.attr.colorPrimary
                    )
                )
                counter = 0
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun deleteCounter(
        deleteButton: ImageButton, counter: ConstraintLayout,
        counterEdit: EditText, rowNumber: TextView, minusButton: Button,
        counter1: ConstraintLayout, counter2: ConstraintLayout,
        counter3: ConstraintLayout, counter4: ConstraintLayout,
        counter5: ConstraintLayout, counter6: ConstraintLayout,
        counter7: ConstraintLayout, counter8: ConstraintLayout,
        counter9: ConstraintLayout, counter10: ConstraintLayout
    ) {
        deleteButton.setOnClickListener {
            counterEdit.text = null
            rowNumber.text = "0"
            minusButton.isEnabled = false
            counter.isVisible = false

            val userId = FirebaseAuth.getInstance().currentUser?.uid
            val email = FirebaseAuth.getInstance().currentUser?.email.toString()
            val listOfVisibility = createVisibilityList(
                counter1,
                counter2,
                counter3,
                counter4,
                counter5,
                counter6,
                counter7,
                counter8,
                counter9,
                counter10
            )

            GlobalScope.launch(Dispatchers.IO) {
                userId?.let {
                    db.collection(email).document("rowCounterData")
                        .update("secCountersVisibility", listOfVisibility).await()
                }
            }
        }
    }

    private fun createVisibilityList(
        counter1: ConstraintLayout, counter2: ConstraintLayout,
        counter3: ConstraintLayout, counter4: ConstraintLayout,
        counter5: ConstraintLayout, counter6: ConstraintLayout,
        counter7: ConstraintLayout, counter8: ConstraintLayout,
        counter9: ConstraintLayout, counter10: ConstraintLayout
    ): List<Int> {
        var visibilityArray = IntArray(10)

        if (counter1.isVisible) {
            visibilityArray[0] = 1
        } else if (!counter1.isVisible) {
            visibilityArray[0] = 0
        }
        if (counter2.isVisible) {
            visibilityArray[1] = 1
        } else if (!counter2.isVisible) {
            visibilityArray[1] = 0
        }
        if (counter3.isVisible) {
            visibilityArray[2] = 1
        } else if (!counter3.isVisible) {
            visibilityArray[2] = 0
        }
        if (counter4.isVisible) {
            visibilityArray[3] = 1
        } else if (!counter4.isVisible) {
            visibilityArray[3] = 0
        }
        if (counter5.isVisible) {
            visibilityArray[4] = 1
        } else if (!counter5.isVisible) {
            visibilityArray[4] = 0
        }
        if (counter6.isVisible) {
            visibilityArray[5] = 1
        } else if (!counter6.isVisible) {
            visibilityArray[5] = 0
        }
        if (counter7.isVisible) {
            visibilityArray[6] = 1
        } else if (!counter7.isVisible) {
            visibilityArray[6] = 0
        }
        if (counter8.isVisible) {
            visibilityArray[7] = 1
        } else if (!counter8.isVisible) {
            visibilityArray[7] = 0
        }
        if (counter9.isVisible) {
            visibilityArray[8] = 1
        } else if (!counter9.isVisible) {
            visibilityArray[8] = 0
        }
        if (counter10.isVisible) {
            visibilityArray[9] = 1
        } else if (!counter10.isVisible) {
            visibilityArray[9] = 0
        }

        return visibilityArray.toList()
    }


    // GETTING THEME COLOR ATTRIBUTES ?attr/*color name*
    @ColorInt // returns RGB (ColorInt) int
    fun Context.getAppColor(@AttrRes colorAttr: Int): Int {
        val resolvedAttr = resolveThemeAttr(this, colorAttr)
        return if (resolvedAttr.resourceId != 0) {
            return ContextCompat.getColor(this, resolvedAttr.resourceId)
        } else {
            resolvedAttr.data
        }
    }

    @ColorRes // returns ColorRes int
    fun Context.getAppColorRes(@AttrRes colorAttr: Int): Int {
        val resolvedAttr = resolveThemeAttr(this, colorAttr)
        // resourceId is used if it's a ColorStateList, and data if it's a color reference or a hex color
        return if (resolvedAttr.resourceId != 0) resolvedAttr.resourceId else 0
    }

    private fun resolveThemeAttr(context: Context, @AttrRes attrRes: Int): TypedValue {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(attrRes, typedValue, true)
        return typedValue
    }

}