package com.example.calculator


import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


private const val STATE_PENDING_OPERATION = "PendingOperation"
private const val STATE_OPERAND1 = "Operand1"
private const val STATE_OPERAND1_STORED = "Operand1_Stored"

class MainActivity : AppCompatActivity() {


//private lateinit var result: EditText
//private lateinit var newnumber: EditText
//private val displayOperation by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.operation) }

//variables to hold operands and type of calculation
private var operand1: Double? = null
private var pendingOperation = "="


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        result = findViewById(R.id.result)
//        newnumber = findViewById(R.id.newnumber)


//        button information
//        val button0: Button = findViewById(R.id.button0)
//        val button1: Button = findViewById(R.id.button1)
//        val button2: Button = findViewById(R.id.button2)
//        val button3: Button = findViewById(R.id.button3)
//        val button4: Button = findViewById(R.id.button4)
//        val button5: Button = findViewById(R.id.button5)
//        val button6: Button = findViewById(R.id.button6)
//        val button7: Button = findViewById(R.id.button7)
//        val button8: Button = findViewById(R.id.button8)
//        val button9: Button = findViewById(R.id.button9)
//        val buttondot: Button = findViewById(R.id.buttondot)
//        val buttondivide: Button = findViewById(R.id.buttondivide)
//        val buttonmultiply: Button = findViewById(R.id.buttonmultiply)
//        val buttonminus: Button = findViewById(R.id.buttonminus)
//        val buttonplus: Button = findViewById(R.id.buttonplus)
//        val buttonclear: Button = findViewById(R.id.buttonclear)
//        val buttonEqual = findViewById<Button>(R.id.buttonequal)

//        on click listener

        val listener = View.OnClickListener { v ->
            val b = v as Button
            newnumber.append(b.text)
        }

        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        buttondot.setOnClickListener(listener)

        buttonclear.setOnClickListener { view ->
            val value = 0
            operand1 = null
            if (value == 0){
                result.setText("")
                operation.text = ""
            }
        }

        val opListener = View.OnClickListener { v ->
            val op = (v as Button).text.toString()
            try {
                val value = newnumber.text.toString().toDouble()
                performOperation(value, op)
            } catch (e: NumberFormatException) {
                newnumber.setText("")
            }


            // performOperation is a function

            pendingOperation = op
            operation.text = pendingOperation
        }

        buttondivide.setOnClickListener(opListener)
        buttonplus.setOnClickListener(opListener)
        buttonminus.setOnClickListener(opListener)
        buttonmultiply.setOnClickListener(opListener)
        buttonequal.setOnClickListener(opListener)

        buttonNeg.setOnClickListener { view ->
            val value = newnumber.text.toString()
            if(value.isEmpty()){
                newnumber.setText("-")
            }else{
                try {
                    var doubleValue= value.toDouble()
                    doubleValue *= -1
                    newnumber.setText(doubleValue.toString())
                }catch (e:NumberFormatException){
//                    new number was (.) or (-), so clear it
                    newnumber.setText("")
                }
            }


        }

    }

    private fun performOperation(value: Double, operation: String) {
        if (operand1 == null) {
            operand1 = value
        } else {
            if (pendingOperation == "=") {
                pendingOperation = operation
            }

            when (pendingOperation) {
                "=" -> operand1 = value
                "/" -> operand1 = if (value == 0.0) {
                    Double.NaN
                } else {
                    operand1!! / value
                }

                "*" -> operand1 = operand1!! * value
                "-" -> operand1 = operand1!! - value
                "+" -> operand1 = operand1!! + value

            }
        }
        result.setText(operand1.toString())
        newnumber.setText("")

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (operand1 != null) {
            outState.putDouble(STATE_OPERAND1, operand1!!)
            outState.putBoolean(STATE_OPERAND1_STORED, true)
        }
        outState.putString(STATE_PENDING_OPERATION, pendingOperation)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        operand1 = if (savedInstanceState.getBoolean(STATE_OPERAND1_STORED, false)) {
            savedInstanceState.getDouble(STATE_OPERAND1)
        } else {
            null
        }

        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION, "=")
        operation.text = pendingOperation
    }
}
