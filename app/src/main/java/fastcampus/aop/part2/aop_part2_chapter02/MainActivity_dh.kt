package fastcampus.aop.part2.aop_part2_chapter02

import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlin.random.Random

class MainActivity_dh : AppCompatActivity() {


    private val numberPicker: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker)
    }

    private val addButton: Button by lazy {
        findViewById(R.id.addButton)
    }

    private val clearButton: Button by lazy {
        findViewById(R.id.clearButton)
    }

    private val runButton:Button by lazy {
        findViewById(R.id.runButton)
    }

    //pickNumber Check용
    private var pickNumberList = hashSetOf<Int>()
    private var didRun: Boolean = false;

    private val textViewList: List<TextView> by lazy {
        listOf(
            findViewById<TextView>(R.id.textView01),
            findViewById<TextView>(R.id.textView02),
            findViewById<TextView>(R.id.textView03),
            findViewById<TextView>(R.id.textView04),
            findViewById<TextView>(R.id.textView05),
            findViewById<TextView>(R.id.textView06),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main_ori)
        setContentView(R.layout.activity_main_1)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initAddButton();
        initClearButton();
    }

    private fun initClearButton() {
        clearButton.setOnClickListener {
            pickNumberList.clear()
            textViewList.forEach()
            {
                it.isVisible = false
            }
            didRun = false;
        }
    }

    //번호 추가하기 버튼
    private fun initAddButton() {
        addButton.setOnClickListener {

            if (pickNumberList.contains(numberPicker.value)) {
                Toast.makeText(this, "이미 뽑았습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pickNumberList.size > 5) {
                Toast.makeText(this, "6개 모두 뽑았습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var numberPicker: Int = numberPicker.value
            var textView = textViewList[pickNumberList.size]
            textView.text = numberPicker.toString()
            textView.isVisible = true

            pickNumberList.add(numberPicker)

        }
    }

    private fun getRandomNumber(): List<Int> {

        var randNum: Random
        var list = mutableSetOf<Int>()
        while (list.size <= 6-pickNumberList.size) {
            randNum = Random(45)
            list.add(randNum.nextInt())
        }


        return pickNumberList.toList() + list;
    }

    private fun initRunButton(){
        runButton.setOnClickListener{
            val randomNumber = getRandomNumber()

            randomNumber.forEachIndexed{
                index, number ->
                val textView = textViewList[index]
                textView.text = number.toString();
            }


           // textViewList[]
           // textViewList[]
        }
    }
}