package fastcampus.aop.part2.aop_part2_chapter02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible

class MainActivity_ori : AppCompatActivity() {

    private val createButton: Button by lazy {
        findViewById<Button>(R.id.clearButton)
    }

    private val addButton: Button by lazy {
        findViewById<Button>(R.id.addButton)
    }

    private val runButton: Button by lazy {
        findViewById<Button>(R.id.runButton)
    }

    private val numberPicker: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker)
    }

    private val numberTextViewList: List<TextView> by lazy {
        listOf<TextView>(
            findViewById<TextView>(R.id.textView01),
            findViewById<TextView>(R.id.textView02),
            findViewById<TextView>(R.id.textView03),
            findViewById<TextView>(R.id.textView04),
            findViewById<TextView>(R.id.textView05),
            findViewById<TextView>(R.id.textView06)
        )
    }

    private val clearButton:Button by lazy{
        findViewById<Button>(R.id.clearButton)
    }

    private var didRun = false
    private val pickNumberSet = hashSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_ori)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initRunButton()
        initAddButton()
        initClearButton()
    }

    private fun initRunButton() {
        runButton.setOnClickListener {
            val list = getRandomNumber()
            didRun = true;

            list.forEachIndexed{ index, number ->
                val textView = numberTextViewList[index]

                textView.text = number.toString()
                textView.isVisible = true

            }

            Log.d("MainActivity", list.toString())
        }
    }

    private fun initAddButton() {
        addButton.setOnClickListener {
            if (didRun) {
                Toast.makeText(this, "??????????????? ??????????????????", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pickNumberSet.size >= 5) {
                Toast.makeText(this, "????????? 5???????????? ????????? ??? ????????????.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pickNumberSet.contains(numberPicker.value)) {
                Toast.makeText(this, "?????? ????????? ???????????????.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val textView = numberTextViewList[pickNumberSet.size]
            ///val textView = numberTextViewList.get[pickNumberSet.size]
            textView.isVisible = true
            textView.text = numberPicker.value.toString()

            pickNumberSet.add(numberPicker.value)
        }
    }

    private fun initClearButton() {
        clearButton.setOnClickListener{
            pickNumberSet.clear()
            numberTextViewList.forEach{
                it.isVisible = false
            }
            didRun = false;
        }
    }


    //6????????? ???????????? ????????????.
    private fun getRandomNumber(): List<Int> {
        val numberList = mutableListOf<Int>()
            .apply {
                for (i in 1..45) {
                    if(pickNumberSet.contains(i)){
                        continue
                    }
                    this.add(i)
                }
            }
        numberList.shuffle()

        val newList = pickNumberSet.toList() + numberList.subList(0, 6 - pickNumberSet.size)
        //newList.sort()
        return newList.sorted()
    }


}