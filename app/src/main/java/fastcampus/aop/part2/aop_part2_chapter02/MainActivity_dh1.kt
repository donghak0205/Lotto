package fastcampus.aop.part2.aop_part2_chapter02

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.contains
import androidx.core.view.isVisible
import androidx.core.view.size
import org.w3c.dom.Text
import java.util.*

class MainActivity_dh1 : AppCompatActivity() {



    private val numberPicker : NumberPicker by lazy{
        findViewById(R.id.numberPicker)
    }

    private val addButton : Button by lazy{
        findViewById(R.id.addButton)
    }

    private val clearButton : Button by lazy {
        findViewById(R.id.clearButton)
    }

    private val textViewList : List<TextView> by lazy {
        listOf(
            findViewById(R.id.textView1),
            findViewById(R.id.textView2),
            findViewById(R.id.textView3),
            findViewById(R.id.textView4),
            findViewById(R.id.textView5),
            findViewById(R.id.textView6)
        )
    }

    private val runButton : Button by lazy {
        findViewById(R.id.runButton)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initAddButton()
        initRunButton()
        initClearButton()
    }


    private var pickNumberSet = hashSetOf<Int>()
    private var didRun = false


    private fun initAddButton() {
        addButton.setOnClickListener{

            if(didRun){
                Toast.makeText(this,"초기화를 해주세요!",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(pickNumberSet.size >= 5){
                Toast.makeText(this,"5개 숫자를 모두 선택하였습니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(pickNumberSet.contains(numberPicker.value)){
                Toast.makeText(this,"이미 선택한 번호 입니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val textView: TextView = textViewList[pickNumberSet.size]
            textView.text = numberPicker.value.toString()
            textView.isVisible = true
            setNumberBackground(numberPicker.value, textView)
            pickNumberSet.add(numberPicker.value)
        }
    }

    private fun initRunButton() {
        runButton.setOnClickListener{
            val randomList = getRandomNumber();


            didRun = true
          /*  textViewList.forEachIndexed{
                index, textView ->
                textView.text = randomList[index].toString()
                setNumberBackground(randomList[index], textView)
                textView.isVisible =true
            }*/

            randomList.forEachIndexed{
                index,number ->

                var textView = textViewList[index]
                textView.text = number.toString()
                textView.isVisible = true
                setNumberBackground(number,textView)
            }


        }
    }

    private fun getRandomNumber() : List<Int> {
   /*     var randomNum:Random = Random(6)

        val randomNumList = mutableListOf<Int>()
        randomNumList.apply {
            for(i in 1..45){
                randomNumList.add(randomNum.nextInt())
            }
        }*/

        var randomNumList = mutableListOf<Int>()
        randomNumList.apply {
            for(i in 1..45){
                if(pickNumberSet.contains(i)){
                    continue
                }
                this.add(i)
            }
        }

        randomNumList.shuffle()

        val newList = pickNumberSet.toList() + randomNumList.subList(0 , (6-pickNumberSet.size))
        return newList.sorted()
    }

    private fun initClearButton() {
        clearButton.setOnClickListener {
            pickNumberSet.clear()
            textViewList.forEach {
                it.isVisible = false
            }
            didRun = false
        }
    }

    private fun setNumberBackground(number:Int, textview:TextView){
        when(number){
            in 1..10 -> textview.background =
                ContextCompat.getDrawable(this, R.drawable.circle_blue)
            in 2..20 -> textview.background =
            ContextCompat.getDrawable(this, R.drawable.circle_red)
            in 3..30-> textview.background =
                ContextCompat.getDrawable(this, R.drawable.circle_green)
            in 4..40-> textview.background =
                ContextCompat.getDrawable(this,R.drawable.circle_gray)
            else -> textview.background =
                ContextCompat.getDrawable(this,R.drawable.circle_yello)
        }
    }
}