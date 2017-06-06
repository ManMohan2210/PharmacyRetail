package googlemapsexample.kotlinexample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    fun sum2Num(a:Int,b:Int):Int{
return a+b
    }

    fun sub2Num(a:Int,b:Int):Int{
        return a-b
    }
    fun mul2Num(a:Int,b:Int):Int{
        return a*b
    }
    fun div2Num(a:Float,b:Float):Float{
        if(b!=0f)
            return a/b
        else return 0f
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_sum.setOnClickListener {
            Toast.makeText(this, "sum of 2 no is " + sum2Num(edt_1.text.toString().toInt(), edt_2.text.toString().toInt()), Toast.LENGTH_LONG).show()
        }


        btn_sub.setOnClickListener {
            Toast.makeText(this, "sub of 2 no is " + sub2Num(edt_1.text.toString().toInt(), edt_2.text.toString().toInt()), Toast.LENGTH_LONG).show()
        }


        btn_mul.setOnClickListener {
            Toast.makeText(this, "mul of 2 no is " + mul2Num(edt_1.text.toString().toInt(), edt_2.text.toString().toInt()), Toast.LENGTH_LONG).show()
        }

        btn_div.setOnClickListener {
            Toast.makeText(this, "div of 2 no is " + div2Num(edt_1.text.toString().toFloat(), edt_2.text.toString().toFloat()), Toast.LENGTH_LONG).show()
        }
    }

    }