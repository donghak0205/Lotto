package fastcampus.aop.part2.aop_part2_chapter02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private val addButton: Button by lazy {
        findViewById<Button>(R.id.addButton)
    }

    private val numberPicker: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker)
    }

    private val clearButton: Button by lazy {
        findViewById<Button>(R.id.clearButton)
    }

    private val runButton: Button by lazy {
        findViewById<Button>(R.id.runButton)
    }

    private val pickNumberSet = hashSetOf<Int>()
    private var didRun: Boolean = false;

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_ori)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initAddButton()
        initClearButton()
        initRunButton()
    }

    private fun initAddButton() {
        addButton.setOnClickListener {

            if (didRun) {
                Toast.makeText(this, "초기화 후 사용해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pickNumberSet.contains(numberPicker.value)) {
                Toast.makeText(this, "이미 추가 되었습니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pickNumberSet.size >= 5) {
                Toast.makeText(this, "번호는 5개까지만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val textView = numberTextViewList[pickNumberSet.size]
            textView.isVisible = true
            textView.text = numberPicker.value.toString()

            setNumberBackground(numberPicker.value, textView)
            pickNumberSet.add(numberPicker.value)
        }
    }

    private fun setNumberBackground(number: Int, textView: TextView) {
        when (number) {
            in 1..10 -> textView.background =
                ContextCompat.getDrawable(this, R.drawable.circle_blue)
            in 11..20 -> textView.background =
                ContextCompat.getDrawable(this, R.drawable.circle_red)
            in 21..30 -> textView.background =
                ContextCompat.getDrawable(this, R.drawable.circle_yello)
            in 31..40 -> textView.background =
                ContextCompat.getDrawable(this, R.drawable.circle_green)
            else -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_gray)
        }
    }

    private fun initClearButton() {
        clearButton.setOnClickListener {
            pickNumberSet.clear()
            numberTextViewList.forEach {
                it.isVisible = false
            }
            didRun = false
        }
    }

    private fun getRandomNumber(): List<Int> {
        var numberList = mutableListOf<Int>()
            .apply {
                for (i in 1..45) {
                    if (pickNumberSet.contains(i)) {
                        continue
                    }
                    this.add(i)
                }
            }
        numberList.shuffle()

        val newList = pickNumberSet.toList() + numberList.subList(0, 6 - pickNumberSet.size)
        return newList.sorted()
    }


    //자동생성 버튼을 눌렀을때 랜덤으로 만들어온 번호들을 뿌린다.
    private fun initRunButton() {
        runButton.setOnClickListener {
            var numList = getRandomNumber()
            didRun = true;
            //만약에 기존에 추가된 개수가 있다면 그 개수만큼 빼고 추가한다.
            numList.forEachIndexed { index, number ->
                var textView = numberTextViewList[index]
                textView.isVisible = true
                textView.text = number.toString()
                setNumberBackground(number, textView)
            }
        }

    }

}