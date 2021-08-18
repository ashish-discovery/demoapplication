package ashish.be.gupta.firstapplication

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.widget.AppCompatSpinner

class CustomSpinner : AppCompatSpinner, AdapterView.OnItemSelectedListener {

    var mListener: OnItemSelectedListener? = null

    /**
     * used to ascertain whether the user selected an item on spinner (and not programmatically)
     */
    private var mUserActionOnSpinner = true

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

        if (mListener != null) {

            mListener!!.onItemSelected(parent, view, position, id, mUserActionOnSpinner)
        }
        // reset variable, so that it will always be true unless tampered with
        mUserActionOnSpinner = true
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        if (mListener != null)
            mListener!!.onNothingSelected(parent)
    }

    interface OnItemSelectedListener {

        fun onItemSelected(
            parent: AdapterView<*>, view: View, position: Int, id: Long,
            userSelected: Boolean
        )

        fun onNothingSelected(parent: AdapterView<*>)
    }

    fun setUserActionOnSpinner(value: Boolean) {
        mUserActionOnSpinner = value
    }

    fun programmaticallySetPosition(pos: Int, animate: Boolean) {
        mUserActionOnSpinner = false
        setSelection(pos, animate)
    }

    fun setOnItemSelectedListener(listener: OnItemSelectedListener) {
        mListener = listener
    }

    constructor(context: Context) : super(context) {
        super.setOnItemSelectedListener(this)
    }

    constructor(context: Context, mode: Int) : super(context, mode) {
        super.setOnItemSelectedListener(this)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        super.setOnItemSelectedListener(this)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        super.setOnItemSelectedListener(this)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int, mode: Int) : super(
        context,
        attrs,
        defStyle,
        mode
    ) {
        super.setOnItemSelectedListener(this)
    }
}