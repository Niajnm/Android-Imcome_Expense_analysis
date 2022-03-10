package Fragment

import RoomDatabase.User
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.incomeexpensemanager.Helper
import com.example.incomeexpensemanager.R
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.dialog_details_layout.view.*

class RecentAdapter (
    var context: Context,
    var rdata:  MutableList<User>,
    val fragment: CalenderFragment
) :
    RecyclerView.Adapter<RecentAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //  var viewName: TextView = itemView.findViewById(R.id.textView_title)
        var viewName: TextView = itemView.findViewById(R.id.detailsTitle_id)
        var viewIncome: TextView = itemView.findViewById(R.id.detailsBdt_id)
        var viewTime: TextView = itemView.findViewById(R.id.recent_time)
        var viewDate: TextView = itemView.findViewById(R.id.recent_date)
        var viewTag: Chip = itemView.findViewById(R.id.chip_id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.item_in_out_layoout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userPosition = rdata[position]
        // val getdate=userPosition.day.toString()
        val getMoney = userPosition.money.toString()
        val getFlag = userPosition.tag.toString()
        val getCategory = userPosition.category.toString()
        val getTitle = userPosition.bank.toString()
        val getTitme = userPosition.time.toString()
        val getDate = userPosition.day.toString()
        val getID = userPosition.uid.toString()
        //  holder.viewName.text=getdate
        //   holder.viewIncome.text = "bdt $getMoney"
        holder.viewName.text="$getCategory ($getTitle)"
        holder.viewTime.text= getTitme
        holder.viewDate.text= getDate

        if (getFlag == "1") {
            holder.viewIncome.text = "bdt $getMoney"
            holder.viewTag.setChipBackgroundColorResource(R.color.grn)
        } else if (getFlag == "0") {
            holder.viewTag.setChipBackgroundColorResource(R.color.mycustom)
            holder.viewTag.setChipIconResource(R.drawable.ic_baseline_do_not_disturb_on_24)
            holder.viewTag.text="OUT"
            holder.viewIncome.text ="bdt -$getMoney"
        }
        holder.itemView.setOnClickListener {
            Detailsdialogue(getDate,getTitle,getTitme,getMoney,getFlag,getID,position)
        }
//        val id = userPosition.bpId!!
    }
    private fun Detailsdialogue(
        getDate: String,
        getTitle: String,
        getTitme: String,
        getMoney: String,
        getFlag: String,
        getID: String,
        userPosition: Int
    ) {

        val builder1 = AlertDialog.Builder(context)
        val layoutInflater = LayoutInflater.from(context)
        val dialogView = layoutInflater.inflate(R.layout.dialog_details_layout, null)
        //  val date= ctx.rcvDate
        builder1.setView(dialogView)
        builder1.setCancelable(true)
        val alert11: AlertDialog = builder1.create()
         dialogView.dlg_date_id.text=getDate
        dialogView.dlg_option.text=getTitle


        if (getFlag == "1") {
            dialogView.dlg_money.text = "BDT $getMoney"
            dialogView.dlg_date_id.setBackgroundResource(R.color.grn)
            dialogView.dlg_money.setBackgroundResource(R.color.grnLow)
        }
        else if (getFlag == "0") {
//            holder.viewTag.setChipBackgroundColorResource(R.color.mycustom)
//            holder.viewTag.setChipIconResource(R.drawable.ic_baseline_do_not_disturb_on_24)
//            holder.viewTag.text = "OUT"
            dialogView.dlg_money.text = "BDT -$getMoney"
        }

        dialogView.img_edit.setOnClickListener {
            //fragment.itemEdit()
        }

        dialogView.img_delete.setOnClickListener {
           fragment.itemDelete(getID.toInt(),getMoney.toInt(),getFlag,getDate)
            rdata.removeAt(userPosition)
            notifyItemRemoved(userPosition)
            notifyItemRangeChanged(userPosition,itemCount)
            alert11.dismiss()
        }
        dialogView.img_share.setOnClickListener {
            // implemented below
            val help = Helper()
            val bitmap = help.getScreenShotFromView(dialogView.bottomSheet_layout)

            // if bitmap is not null then
            // save it to gallery
            if (bitmap != null) {
                help.saveMediaToStorage(context, bitmap)
            }
        }
        alert11.show()
        dialogView.dialouge_ok.setOnClickListener {
            alert11.dismiss()
        }
    }

//    private fun showBottomSheetDialog(getTitle: String, getMoney: String, getFlag: String) {
//        val bottomSheetDialog = BottomSheetDialog(context)
//        val layoutInflater = LayoutInflater.from(context)
//        val dialogView = layoutInflater.inflate(R.layout.dialog_details_layout, null)
//        bottomSheetDialog.setContentView(dialogView)
//        val date = ctx.rcvDate
//        dialogView.dlg_date_id.text = date
//        dialogView.dlg_option.text = getTitle
//
//        if (getFlag == "income") {
//            dialogView.dlg_money.text = "BDT $getMoney"
//            dialogView.dlg_date_id.setBackgroundResource(R.color.grn)
//        }
//        else if (getFlag == "expense") {
////            holder.viewTag.setChipBackgroundColorResource(R.color.mycustom)
////            holder.viewTag.setChipIconResource(R.drawable.ic_baseline_do_not_disturb_on_24)
////            holder.viewTag.text = "OUT"
//            dialogView.dlg_money.text = "BDT -$getMoney"
//        }
//
//        dialogView.img_edit.setOnClickListener {
//            ctx.itemEdit()
//        }
//        dialogView.img_share.setOnClickListener {
//            // implemented below
//            val help = Helper()
//            val bitmap = help.getScreenShotFromView(dialogView.bottomSheet_layout)
//
//            // if bitmap is not null then
//            // save it to gallery
//            if (bitmap != null) {
//                help.saveMediaToStorage(context, bitmap)
//            }
//        }
//
//        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
//        bottomSheetDialog.show()
//        dialogView.dialouge_ok.setOnClickListener {
//            bottomSheetDialog.dismiss()
//        }
//    }

    override fun getItemCount(): Int {
        return rdata.size
    }
}