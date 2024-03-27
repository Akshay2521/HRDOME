import android.animation.ValueAnimator
import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.app.hrdrec.R
import com.app.hrdrec.utils.CommonMethods.selectAnyDate
import com.app.hrdrec.utils.getDate

class FilterDialogFragment : DialogFragment() {

    private lateinit var btnSubmit: ImageView
    private lateinit var txtFrom: TextView
    private lateinit var txtTo: TextView
    private lateinit var imgFrom: ImageView
    private lateinit var imgTo: ImageView
    private lateinit var filterContent: LinearLayout

    var strFrom = "";
    var strTo = "";
    var strStatus = "All";

    val datePicker = selectAnyDate(null)
    val datePickerEnd = selectAnyDate(null)


    // Listener to communicate button click to the hosting activity or fragment
    interface FilterDialogListener {
        fun onFilterApply(startDate: String,endDate : String,status : String)
    }


    // Function to set active state
    fun setActiveState(textView: TextView) {
        strStatus = textView.text.toString();
        textView.setBackgroundResource(R.drawable.rounded_corners_text)
        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
    }

    // Function to set inactive state
    fun setInactiveState(textView: TextView) {
        textView.setBackgroundResource(R.drawable.round_corner_transparent)
        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_dark_orange))
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_filter_dialog, container, false)
        btnSubmit = view.findViewById(R.id.btnSubmit)
        filterContent = view.findViewById(R.id.filterContent)
        txtFrom = view.findViewById(R.id.txtFrom)
        imgFrom = view.findViewById(R.id.imgFrom)
        imgTo = view.findViewById(R.id.imgTo)
        txtTo = view.findViewById(R.id.txtTo)

        txtTo.setText(strTo);
        txtFrom.setText(strFrom);

        val txtSaved = view.findViewById<TextView>(R.id.txtSaved)
        val txtSubmitted = view.findViewById<TextView>(R.id.txtSubmitted)
        val txtRejected = view.findViewById<TextView>(R.id.txtRejected)
        val txtApproved = view.findViewById<TextView>(R.id.txtApproved)
        val txtAll = view.findViewById<TextView>(R.id.txtAll)

// Set initial states
        setInactiveState(txtSubmitted)
        setInactiveState(txtRejected)
        setInactiveState(txtApproved)
        setInactiveState(txtAll)

        txtSaved.setOnClickListener {
            setActiveState(txtSaved)
            setInactiveState(txtSubmitted)
            setInactiveState(txtRejected)
            setInactiveState(txtApproved)
            setInactiveState(txtAll)
        }

        txtSubmitted.setOnClickListener {
            setActiveState(txtSubmitted)
            setInactiveState(txtSaved)
            setInactiveState(txtRejected)
            setInactiveState(txtApproved)
            setInactiveState(txtAll)
        }

        txtRejected.setOnClickListener {
            setActiveState(txtRejected)
            setInactiveState(txtSaved)
            setInactiveState(txtSubmitted)
            setInactiveState(txtApproved)
            setInactiveState(txtAll)
        }

        txtApproved.setOnClickListener {
            setActiveState(txtApproved)
            setInactiveState(txtSaved)
            setInactiveState(txtSubmitted)
            setInactiveState(txtRejected)
            setInactiveState(txtAll)
        }

        txtAll.setOnClickListener {
            setActiveState(txtAll)
            setInactiveState(txtSaved)
            setInactiveState(txtSubmitted)
            setInactiveState(txtRejected)
            setInactiveState(txtApproved)
        }

        if(strStatus != "" || strStatus != "All"){
            if(strStatus == txtSaved.text.toString()){
                txtSaved.performClick();
            }else if(strStatus == txtApproved.text.toString()){
                txtApproved.performClick();
            }else if(strStatus == txtSubmitted.text.toString()){
                txtSubmitted.performClick();
            }else if(strStatus == txtRejected.text.toString()){
                txtRejected.performClick();
            }
        }

        btnSubmit.setOnClickListener {
            // Call listener when submit button is clicked
            val listener = activity as FilterDialogListener?
            listener?.onFilterApply(txtFrom.text.toString(),txtTo.text.toString(),strStatus)
            dismiss()
        }

        imgTo.setOnClickListener {
            datePicker.show(childFragmentManager, "DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                txtTo.text = getDate(it)
            }
        }


        imgFrom.setOnClickListener {
            datePickerEnd.show(childFragmentManager, "DatePicker")
            datePickerEnd.addOnPositiveButtonClickListener {
                    txtFrom.text = getDate(it)
                }
        }


        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        animateDialog(true)
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setGravity(Gravity.TOP)
    }

    private fun animateDialog(shouldExpand: Boolean) {
        val animator = if (shouldExpand) {
            ValueAnimator.ofInt(0, resources.getDimensionPixelSize(R.dimen.dialog_height))
        } else {
            ValueAnimator.ofInt(resources.getDimensionPixelSize(R.dimen.dialog_height), 0)
        }

        animator.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            val params = filterContent.layoutParams
            params.height = value
            filterContent.layoutParams = params
        }

        animator.duration = 300
        animator.start()
    }
}
